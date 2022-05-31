package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.ViewObserver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is part of the client side.
 * It is an interpreter between the network and a generic view (which in this case can be CLI or GUI).
 * It receives the messages, wraps/unwraps and pass them to the network/view.
 */
public class ClientController implements ViewObserver {

    private ClientGameModel clientGameModel;
    private ClientSocket client;
    private String nickname;
    private ClientState clientState;
    private int teamId;  //if there are no teams it is the clientId, or it's the leaderId
    private boolean teamLeader; //he is who requested the teamPlayer and who will choose the tower color
    //my player id is in clientGameModel and in client

    private final ExecutorService taskQueue;

    //public static final int UNDO_TIME = 5000;

    /**
     * Constructs Client Controller.
     *
     *
     */
    public ClientController() {
        taskQueue = Executors.newSingleThreadExecutor();
        teamLeader=true;
        clientState = ClientState.APPLICATION_START;
        clientGameModel = new ClientGameModel();
        nickname = null;



    }
    /**
     * Takes action based on the message type received from the server.
     *
     * @param message the message received from the server.
     */
    //this is the command (like an update) from the clientSocket
    public void onMessageReceived(Message message) {

        switch (message.getMessageType()) {
            case LOGIN_REPLY: //we can use notifyObserver of the clientGameModel because we have it, empty
                clientState = ClientState.LOGIN_ACCEPTED;
                StringMessage loginReply = (StringMessage) message;
                client.setClientId(Integer.parseInt(loginReply.getString())); //we need this in the ClientSocket class
                clientGameModel.setMyPlayerId(client.getClientId());
                teamId=client.getClientId(); //for now, I'm my team player
                taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
                break;
            case AVAILABLE_LOBBIES:
                clientState = ClientState.CHOOSING_LOBBY;
                LobbyMessage lobbyMessage = (LobbyMessage) message;
                taskQueue.execute(() -> clientGameModel.sendChooseLobby(lobbyMessage.getLobbiesList()));
                break;
            case PLAYER_JOIN:
                clientState = ClientState.WAITING_IN_LOBBY;
                PlayerJoinMessage playerJoinMessage = (PlayerJoinMessage) message;
                taskQueue.execute(() -> clientGameModel.showPlayerJoin(playerJoinMessage.getPlayersList()));
                break;

            case AVAILABLE_TEAM_SEND:
                SyncInitMessage teamMessage = (SyncInitMessage) message;
                Map<String,Integer> availableTeamPlayers =  teamMessage.getAvailableTeamPlayers();
                clientGameModel.setAvailableTeamPlayers(availableTeamPlayers);

                for(Map.Entry<String,Integer> entry : availableTeamPlayers.entrySet()){  //saving my nickname, the first available team send will have every nickname
                    if(entry.getValue() == client.getClientId())
                    taskQueue.execute(() -> setNickname(entry.getKey()));

                }

                if (availableTeamPlayers.containsKey(nickname)){  //it means that I still have to choose
                    clientState = ClientState.CHOOSING_TEAM;
                    availableTeamPlayers.remove(nickname);
                    taskQueue.execute(() -> clientGameModel.sendChooseTeam(availableTeamPlayers));
                }

                break;
            case AVAILABLE_TOWER_SEND:
                SyncInitMessage towerMessage = (SyncInitMessage) message;
                List<TowerColor> availableTowerColors = towerMessage.getAvailableTowerColors();
                clientGameModel.setAvailableTowerColors(availableTowerColors);
                if (clientState == ClientState.CHOOSING_TOWER_COLOR){
                    taskQueue.execute(() -> clientGameModel.sendChooseTowerColor(availableTowerColors));
                }
                break;
            case AVAILABLE_WIZARD_SEND:
                SyncInitMessage wizardMessage = (SyncInitMessage) message;
                List<Wizard> availableWizards = wizardMessage.getAvailableWizards();
                clientGameModel.setAvailableWizards(availableWizards);

                if (clientState == ClientState.CHOOSING_WIZARD){
                    taskQueue.execute(() -> clientGameModel.sendChooseWizard(availableWizards));
                }
                break;



            case INIT_SEND:
                clientState = ClientState.GAME_START;
                AllGameMessage allGameMessage = (AllGameMessage) message;
                clientGameModel.initClientGameModel(allGameMessage);
                taskQueue.execute(() -> setNickname(clientGameModel.findPlayerById(client.getClientId()).getName()));
                break;


            case SYNC_STATE:
                SyncStateMessage syncStateMessage = (SyncStateMessage) message;
                manageSyncStateMessage(syncStateMessage);
                break;

//            case TABLE:
//                BoardMessage boardMessage = (BoardMessage) message;
//                taskQueue.execute(() -> view.showBoard(boardMessage.getBoard()));
//                break;
//            case THROWN_ASSISTANT:
//                taskQueue.execute(() -> view.askInitWorkersPositions(((PositionMessage) message).getPositionList()));
//                break;
//            case DASHBOARD:
//                taskQueue.execute(() -> view.askEnableEffect(((PrepareEffectMessage) message).isEnableEffect()));
//                break;
//            case CHARACTER_TABLE:
//                taskQueue.execute(() -> view.askMoveFx(((PositionMessage) message).getPositionList()));
//                break;
//            case ASSISTANTS_SEND:
//                taskQueue.execute(() -> view.askBuildFx(((PositionMessage) message).getPositionList()));
//                break;
//            case WIN:
//                WinMessage winMessage = (WinMessage) message;
//                client.disconnect();
//                taskQueue.execute(() -> view.showWinMessage(winMessage.getWinnerNickname()));
//                break;


            case OK_REPLY: //used also like a state sync message
                //GenericMessage okReply = (GenericMessage) message;
                manageOkReplyMessage();//we use a private method to have more order
                break;


            case ERROR_REPLY: //It will show the error and reset the previous state, it will interrupt the current thread?
                StringMessage errorReply = (StringMessage) message;
                clientGameModel.show(errorReply.getString());
                manageErrorReplyMessage();
                break;


            case DISCONNECTION:  //when someone else disconnected
                // StringMessage dm = (StringMessage) message;
                // client.sendMessage(dm);
                clientState = ClientState.APPLICATION_START;
                //taskQueue.execute(() -> clientGameModel.show("A client disconnected"));
                clientGameModel.show("A client disconnected");
                //System.out.println("A client disconnected");
                //taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
                clientGameModel.askCreateOrJoin();
                break;

            default: // Should never reach this condition
                break;
        }
    }


    //NOW THERE ARE UTILS METHOD FOR THE SOCKET
    /**
     * Validates the given IPv4 address by using a regex.
     *
     * @param ip the string of the ip address to be validated
     * @return {@code true} if the ip is valid, {@code false} otherwise.
     */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
     * Checks if the given port string is in the range of allowed ports.
     *
     * @param portStr the ports to be checked.
     * @return {@code true} if the port is valid, {@code false} otherwise.
     */
    public static boolean isValidPort(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     //     * Create a new Socket Connection to the server with the updated info.
     //     * An error view is shown if connection cannot be established.
     //     *
     //     * @param serverInfo a map of server address and server port.
     //     */
    //from now there are the override of the ViewObserver interface
    @Override
    public void onAskServerInfo(Map<String, String> serverInfo)  {
        try {
            client = new ClientSocket(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")), this);
            clientState = ClientState.REQUESTING_LOGIN;
            client.readMessage(); // Starts an asynchronous reading from the server.
            taskQueue.execute(clientGameModel::sendLoginRequest);
        } catch (IOException e) {
            clientGameModel.sendServerInfoRequest();
            clientState = ClientState.APPLICATION_START;
        }
    }
    @Override
    public void onSendLoginRequest(String input){
        StringMessage loginRequest = new StringMessage(MessageType.LOGIN_REQUEST, client.getClientId(), true,input);
        taskQueue.execute(() -> client.sendMessage(loginRequest));
        clientState = ClientState.LOGIN_ACCEPTED;
    }



    /*@Override
    public void onAskCreateOrJoin(String input){ //also in view we could do this check...
        if(input.equals("c")){
            taskQueue.execute(() -> clientGameModel.sendNewLobbyRequest());
        }
        else if(input.equals("j")){
            taskQueue.execute(() -> clientGameModel.sendLobbiesRequest());
        }
        else{
            taskQueue.execute(() -> clientGameModel.show("Incorrect input,try again!"));
            taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
        }
    }*/

    @Override
    public void onSendNewLobbyRequest(String input,int numOfPlayers,boolean expertMode){
        Lobby lobby = new Lobby(numOfPlayers,0,expertMode,input);
        NewLobbyMessage message = new NewLobbyMessage(client.getClientId(),lobby);
        client.sendMessage(message);
        clientState = ClientState.CHOSEN_LOBBY;
    }

    @Override
    public void onSendLobbiesRequest(){
        GenericMessage message = new GenericMessage(MessageType.LOBBIES_REQUEST,client.getClientId(),true);
        client.sendMessage(message);
        clientState = ClientState.CHOOSING_LOBBY;

    }

    @Override
    public void onSendChooseLobby(String chosenLobby){
        StringMessage message =  new StringMessage(MessageType.CHOOSE_LOBBY,client.getClientId(),true, chosenLobby);
        client.sendMessage(message);
        clientState = ClientState.CHOSEN_LOBBY;
    }

    @Override
    public void onSendChooseTeam(int chosenTeamPlayerId) {
        ChooseTeamMessage message = new ChooseTeamMessage(client.getClientId(),chosenTeamPlayerId);
        client.sendMessage(message);
        clientState = ClientState.CHOSEN_TEAM;
    }

    @Override
    public void onSendChooseTowerColor(TowerColor color) {
        ChooseTowerColorMessage message = new ChooseTowerColorMessage(client.getClientId(),color);
        client.sendMessage(message);
        clientState = ClientState.CHOSEN_TOWER_COLOR;
    }

    @Override
    public void onSendChooseWizard(Wizard wizard) {
        ChooseWizardMessage message = new ChooseWizardMessage(client.getClientId(),wizard);
        client.sendMessage(message);
        clientState = ClientState.CHOSEN_WIZARD;
    }



    public void onSocketDisconnect(){   //this happens only when there is a mine critical problem
        /*taskQueue.execute(() -> )*/ clientGameModel.show((Object) "Disconnecting...");
        clientState = ClientState.GAME_FINISHED;
    }

    //UTILS METHODS
    private void manageOkReplyMessage(){
        switch(clientState){
            case CHOOSING_TEAM:
                teamLeader = false; //and with init send I will receive my teamPlayer
                clientState = ClientState.CHOOSING_WIZARD;
                break;

            case CHOSEN_TEAM:
                teamLeader = true; //for security, I set it true
                teamId = client.getClientId(); //for security I set it, the team Id is mine
                clientState = ClientState.CHOOSING_TOWER_COLOR; //and now i will wait for available ... send
                break;

            case CHOSEN_TOWER_COLOR:
                clientState = ClientState.CHOOSING_WIZARD; //and now i will wait for available ... send
                break;
            case CHOSEN_WIZARD:
                clientState = ClientState.GAME_START;
                break;
            default:
                break;

        }
    }

    private void manageErrorReplyMessage(){
        switch(clientState) {
            case LOGIN_ACCEPTED:
                clientState = ClientState.REQUESTING_LOGIN;
                //ExecutorService.shutdownNow();
                //taskQueue.execute(() -> clientGameModel.sendLoginRequest());
                clientGameModel.sendLoginRequest();;
                break;

            case CHOSEN_LOBBY:
                clientState = ClientState.CHOOSING_LOBBY;
                //ExecutorService.shutdownNow();
                //taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
                clientGameModel.askCreateOrJoin();;
                break;

            case CHOSEN_TEAM:
                clientState = ClientState.CHOOSING_TEAM;
                //ExecutorService.shutdownNow();
                //taskQueue.execute(() -> clientGameModel.sendChooseTeam(clientGameModel.getAvailableTeamPlayers()));
                clientGameModel.sendChooseTeam(clientGameModel.getAvailableTeamPlayers());

                break;

            case CHOSEN_TOWER_COLOR:
                clientState = ClientState.CHOOSING_TOWER_COLOR;
                //ExecutorService.shutdownNow();
                //taskQueue.execute(() -> clientGameModel.sendChooseTowerColor(clientGameModel.getAvailableTowerColors()));
                clientGameModel.sendChooseTowerColor(clientGameModel.getAvailableTowerColors());
                break;

            case CHOSEN_WIZARD:
                clientState = ClientState.CHOOSING_WIZARD;
                //ExecutorService.shutdownNow();
                //taskQueue.execute(() -> clientGameModel.sendChooseWizard(clientGameModel.getAvailableWizards()));
                clientGameModel.sendChooseWizard(clientGameModel.getAvailableWizards());
                break;

            default:
                break;
        }

    }

    private  void manageSyncStateMessage(SyncStateMessage syncStateMessage){
        GameState gameState = syncStateMessage.getGameState();
        SettingState settingState = syncStateMessage.getSettingState();
        RoundState roundState = syncStateMessage.getRoundState();
        TurnState turnState = syncStateMessage.getTurnState();
        int currPlayerId = syncStateMessage.getCurrPlayerId();


        if(gameState== GameState.SETTING_STATE){
            switch (settingState){
                case CHOOSE_TEAM_STATE:
                    clientState = ClientState.CHOOSING_TEAM;
                    break;
                case CHOOSE_TOWER_COLOR_STATE:
                        clientState = ClientState.CHOOSING_TOWER_COLOR;
                        break;
                case CHOOSE_WIZARD_STATE:
                    clientState = ClientState.CHOOSING_WIZARD;
                default:
                    break;
        }
        }

        else if(gameState == GameState.INGAME_STATE && roundState == RoundState.PLANNING_STATE){
            if(currPlayerId == client.getClientId()){
                //clientState = ClientState.THROWING_ASSISTANT;
                //taskQueue.execute(() -> clientGameModel.sendThrowAssistant());
            }
            else {
                String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                //taskQueue.execute(() -> clientGameModel.show(playerName + " is throwing an assistant..." ));
                clientGameModel.show(playerName + " is throwing an assistant..." );
            }
        }
        else if (gameState == GameState.INGAME_STATE && roundState == RoundState.ACTION_STATE){
            if(currPlayerId == client.getClientId()){
                switch(turnState){
                    case MOVE_STUDENT_STATE:
                        //clientState = ClientState.MOVING_STUDENT;
                        //taskQueue.execute(() -> clientGameModel.sendMoveStudent());  //server will resend me syncStateMessage until I finished to move student
                        break;
                    case MOVE_MOTHER_NATURE_STATE:
                        //clientState = ClientState.MOVING_MOTHER_NATURE;
                        //taskQueue.execute(() -> clientGameModel.sendMoveMotherNature());
                        break;
                    case CHOOSE_CLOUD_STATE:
                        //clientState = ClientState.CHOOSING_CLOUD;
                        //taskQueue.execute(() -> clientGameModel.sendChooseCloud());
                        break;
                }
            }
            else{
                if (turnState == TurnState.MOVE_STUDENT_STATE){
                    String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                    //ExecutorService.shutdownNow();
                    //taskQueue.execute(() -> clientGameModel.show(playerName + " is playing..." ));
                    clientGameModel.show(playerName + " is playing..." );

                }

            }
        }

    }

    public ClientGameModel getClientGameModel() {
        return clientGameModel;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ClientState getClientState() {
        return clientState;
    }
}
