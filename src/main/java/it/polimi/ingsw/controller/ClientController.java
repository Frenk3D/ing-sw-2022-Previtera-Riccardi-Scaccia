package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.client.ReducedDashboard;
import it.polimi.ingsw.model.client.ReducedPlayer;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
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
    private int teamPlayerId;  //if there are no teams it is the clientId, or it's the leaderId
    private boolean teamLeader; //he is who requested the teamPlayer and who will choose the tower color

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
                taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
                break;
            case AVAILABLE_LOBBIES:
                clientState = ClientState.PRE_LOBBY;
                LobbyMessage lobbyMessage = (LobbyMessage) message;
                taskQueue.execute(() -> clientGameModel.sendChooseLobby(lobbyMessage.getLobbiesList()));
                break;
            case PLAYER_JOIN:
                clientState = ClientState.WAITING_IN_LOBBY;
                PlayerJoinMessage playerJoinMessage = (PlayerJoinMessage) message;
                taskQueue.execute(() -> clientGameModel.showPlayerJoin(playerJoinMessage.getPlayersList()));
                break;
            case OK_REPLY: //used also like a state sync message
                GenericMessage okReply = (GenericMessage) message;
                if(clientState == ClientState.WAITING_IN_LOBBY) { //chosen in a team,team player id will be set with init send
                    teamLeader = false;
                    clientState = ClientState.CHOOSING_WIZARD;
                }
                break;



            case DISCONNECTION:  //when someone else disconnected
               // StringMessage dm = (StringMessage) message;
               // client.sendMessage(dm);
                clientState = ClientState.APPLICATION_START;
                taskQueue.execute(() -> clientGameModel.show("A client disconnected"));
                //System.out.println("A client disconnected");
                taskQueue.execute(() -> clientGameModel.askCreateOrJoin());
                break;
//            case ERROR_REPLY:
//                ErrorMessage em = (ErrorMessage) message;
//                view.showErrorAndExit(em.getError());
//                break;
//
//
                case INIT_SEND:
                    clientState = ClientState.GAME_START;
                    AllGameMessage allGameMessage = (AllGameMessage) message;
                    taskQueue.execute(() -> clientGameModel.initClientGameModel(allGameMessage));

                    Map<String, Integer> availablePlayers = new HashMap<>();
                    for(ReducedPlayer p : clientGameModel.getPlayersList()){
                        if(p.getId() != client.getClientId())
                            availablePlayers.put(p.getName(),p.getId());
                        else {
                            nickname = p.getName();
                        }
                    }

                    List<Wizard> availableWizards = new ArrayList<>();
                    for (Wizard w: Wizard.values()){
                        availableWizards.add(w);
                    }

                    List<TowerColor> availableTowerColors = new ArrayList<>();
                    for (TowerColor tw: TowerColor.values()){
                        availableTowerColors.add(tw);
                    }

                    if(clientGameModel.getPlayersList().size() == 4 && teamLeader){
                        clientState = ClientState.CHOOSING_TEAM;
                        taskQueue.execute(() -> clientGameModel.sendChooseTeam(availablePlayers));
                    }
                    else if(clientGameModel.getPlayersList().size() == 4 && !teamLeader){
                        clientState = ClientState.CHOOSING_WIZARD;
                        taskQueue.execute(() -> clientGameModel.sendChooseWizard(availableWizards));
                    }
                    else{
                        clientState = ClientState.CHOOSING_TOWER_COLOR;
                        taskQueue.execute(() -> clientGameModel.sendChooseTowerColor(availableTowerColors));
                    }


                    break;
//
//            case AVAILABLE_TEAM_SEND:
            //message.getMap
            //map.remove(nickname)
//                taskQueue.execute(() -> view.askMove(((PositionMessage) message).getPositionList()));
//                //we have to give leaderId to the teamPlayer and set the leader as true if we receive avaible_team_send as reply
//                break;
//            case AVAILABLE_TOWER_SEND:
//                taskQueue.execute(() -> view.askMovingWorker(((PositionMessage) message).getPositionList()));
//                break;
//            case AVAILABLE_WIZARD_SEND:
//                MatchInfoMessage playersMessage = (MatchInfoMessage) message;
//                taskQueue.execute(() -> view.askFirstPlayer(playersMessage.getActivePlayers(), playersMessage.getActiveGods()));
//                break;
//            case SYNC_STATE:
//
//                break;
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
            //client.addObserver(this);
            client.readMessage(); // Starts an asynchronous reading from the server.
            taskQueue.execute(clientGameModel::sendLoginRequest);

        } catch (IOException e) {
            taskQueue.execute(() -> clientGameModel.sendServerInfoRequest());
        }
    }
    @Override
    public void onSendLoginRequest(String input){
        StringMessage loginRequest = new StringMessage(MessageType.LOGIN_REQUEST, client.getClientId(), true,input);
        taskQueue.execute(() -> client.sendMessage(loginRequest));
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
    }

    @Override
    public void onSendLobbiesRequest(){
        GenericMessage message = new GenericMessage(MessageType.LOBBIES_REQUEST,client.getClientId(),true);
        client.sendMessage(message);
    }

    @Override
    public void onSendChooseLobby(String chosenLobby){
        StringMessage message =  new StringMessage(MessageType.CHOOSE_LOBBY,client.getClientId(),true, chosenLobby);
        client.sendMessage(message);
    }
    //TO DELETE
//    /**
//     * Create a new Socket Connection to the server with the updated info.
//     * An error view is shown if connection cannot be established.
//     *
//     * @param serverInfo a map of server address and server port.
//     */
//    @Override
//    public void onUpdateServerInfo(Map<String, String> serverInfo) {
//        try {
//            client = new ClientSocket(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
//            client.addObserver(this);
//            client.readMessage(); // Starts an asynchronous reading from the server.
//            taskQueue.execute(view::askPlayerInfo);
//        } catch (IOException e) {
//            taskQueue.execute(() -> view.askServerConfig());
//        }
//    }
//
//    /**
//     * Sends a message to the server with the updated nickname.
//     * The nickname is also stored locally for later usages.
//     *
//     * @param nickname the nickname to be sent.
//     */
//    @Override
//    public void onUpdateNickname(String nickname) {
//        this.nickname = nickname;
//        client.sendMessage(new StringMessage(MessageType.LOGIN_REQUEST,client.getClientId(),this.nickname));
//    }
//
//    /**
//     * Sends a message to the server with the player number chosen by the user.
//     *
//     * @param playersNumber the number of players.
//     */
//    @Override
//    public void onUpdatePlayersNumber(int playersNumber) {
//        client.sendMessage(new PlayerNumberReply(this.nickname, playersNumber));
//    }
//
//    /**
//     * Sends a message to the server with the workers color chosen by the user.
//     *
//     * @param color the color of the workers.
//     */
//    @Override
//    public void onUpdateWorkersColor(Color color) {
//        client.sendMessage(new ColorsMessage(this.nickname, List.of(color)));
//    }
//
//    /**
//     * Sends a message to the server with the gods chosen by the user.
//     *
//     * @param gods the list of gods chosen by the user.
//     */
//    @Override
//    public void onUpdateGod(List<ReducedGod> gods) {
//        client.sendMessage(new GodListMessage(this.nickname, gods, 0));
//    }
//
//    /**
//     * Sends a message to the server with the position of the worker to be moved chosen by the user.
//     *
//     * @param position the position of the worker to be moved.
//     */
//    @Override
//    public void onUpdatePickMovingWorker(Position position) {
//        client.sendMessage(new PositionMessage(this.nickname, MessageType.PICK_MOVING_WORKER, List.of(position)));
//    }
//
//    /**
//     * Sends a message to the server with the initial position of the workers chosen by the user.
//     *
//     * @param positions the list of the initial position of the workers.
//     */
//    @Override
//    public void onUpdateInitWorkerPosition(List<Position> positions) {
//        client.sendMessage(new PositionMessage(this.nickname, MessageType.INIT_WORKERSPOSITIONS, positions));
//    }
//
//    /**
//     * Sends a message to the server with the new position of the moving worker chosen by the user.
//     *
//     * @param destination the new position of the moving worker.
//     */
//    @Override
//    public void onUpdateMove(Position destination) {
//        client.sendMessage(new PositionMessage(this.nickname, MessageType.MOVE, List.of(destination)));
//    }
//
//    /**
//     * Sends a message to the server with the position of the block to be built chosen by the user.
//     *
//     * @param position the position of the block to be built.
//     */
//    @Override
//    public void onUpdateBuild(Position position) {
//        client.sendMessage(new PositionMessage(this.nickname, MessageType.BUILD, List.of(position)));
//    }
//
//    /**
//     * Sends a message to the server with the choice of the user about his god effect.
//     *
//     * @param response the choice of the user about his god effect.
//     */
//    @Override
//    public void onUpdateEnableEffect(boolean response) {
//        client.sendMessage(new PrepareEffectMessage(this.nickname, response));
//    }
//
//    /**
//     * Sends a message to the server with the choice of the user about his god effect.
//     *
//     * @param dest the choice of the user about his god effect.
//     */
//    @Override
//    public void onUpdateApplyEffect(Position dest) {
//        client.sendMessage(new PositionMessage(this.nickname, MessageType.APPLY_EFFECT, List.of(dest)));
//    }
//
//    /**
//     * Sends a message to the server with the nickname of the first player chosen by the user.
//     *
//     * @param nickname the nickname of the first player.
//     */
//    @Override
//    public void onUpdateFirstPlayer(String nickname) {
//        client.sendMessage(new MatchInfoMessage(this.nickname, MessageType.PICK_FIRST_PLAYER, null, null, null, nickname));
//    }
//


//   /**
//     * Disconnects the client from the network.
//     */
//    @Override
//    public void onAskDisconnection() {
//        client.disconnect();
//     }


    public void onSocketDisconnect(){   //this happens only when there is a mine critical problem
        /*taskQueue.execute(() -> )*/ clientGameModel.show((Object) "Disconnecting...");
    }

    public ClientGameModel getClientGameModel() {
        return clientGameModel;
    }
}
