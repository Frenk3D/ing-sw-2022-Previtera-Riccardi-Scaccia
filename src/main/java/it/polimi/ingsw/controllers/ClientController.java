package it.polimi.ingsw.controllers;


import it.polimi.ingsw.model.characters.MessageCharacterParameters;
import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.client.ReducedPlayer;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.ViewObserver;

import java.io.IOException;
import java.net.URI;
import java.util.*;


/**
 * This class is part of the client side.
 * It is an interpreter between the network and a generic view (which in this case can be CLI or GUI).
 * It receives the messages, wraps/unwraps and pass them to the network/view.
 * Implements {@link ViewObserver}
 */
public class ClientController implements ViewObserver {

    private ClientGameModel clientGameModel;
    private ClientSocket client;
    private String nickname;
    private ClientState clientState;
    private int teamId;  //if there are no teams it is the clientId, or it's the leaderId
    private boolean teamLeader; //he is who requested the teamPlayer and who will choose the tower color
    //my player id is in clientGameModel and in client
    private ClientState prevClientState;

    private int usedCharacter; //this is for the input verifier
    /**
     * Constructs Client Controller.
     *
     *
     */
    public ClientController() {
        teamLeader=true;
        clientState = ClientState.APPLICATION_START;
        clientGameModel = new ClientGameModel();
        nickname = null;
        usedCharacter = -1;


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
                clientState = ClientState.CHOOSING_JOIN_CREATE;
                StringMessage loginReply = (StringMessage) message;
                client.setClientId(Integer.parseInt(loginReply.getString())); //we need this in the ClientSocket class
                clientGameModel.setMyPlayerId(client.getClientId());
                teamId = client.getClientId(); //for now, I'm my team player
                clientGameModel.askCreateOrJoin();
                break;
            case AVAILABLE_LOBBIES:
                clientState = ClientState.CHOOSING_LOBBY;
                LobbyMessage lobbyMessage = (LobbyMessage) message;
                clientGameModel.sendChooseLobby(lobbyMessage.getLobbiesList());
                break;
            case PLAYER_JOIN:
                clientState = ClientState.WAITING_IN_LOBBY;
                clientGameModel.reset();
                PlayerJoinMessage playerJoinMessage = (PlayerJoinMessage) message;
                clientGameModel.showPlayerJoin(playerJoinMessage.getPlayersList());
                break;

            case AVAILABLE_TEAM_SEND:
                SyncInitMessage teamMessage = (SyncInitMessage) message;
                Map<String, Integer> availableTeamPlayers = teamMessage.getAvailableTeamPlayers();
                clientGameModel.setAvailableTeamPlayers(availableTeamPlayers);

                for (Map.Entry<String, Integer> entry : availableTeamPlayers.entrySet()) {  //saving my nickname, the first available team send will have every nickname
                    if (entry.getValue() == client.getClientId())
                        setNickname(entry.getKey());

                }

                if (availableTeamPlayers.containsKey(nickname)) {  //it means that I still have to choose
                    clientState = ClientState.CHOOSING_TEAM;
                    availableTeamPlayers.remove(nickname);
                    clientGameModel.sendChooseTeam(availableTeamPlayers);
                }

                break;
            case AVAILABLE_TOWER_SEND:
                SyncInitMessage towerMessage = (SyncInitMessage) message;
                List<TowerColor> availableTowerColors = towerMessage.getAvailableTowerColors();
                clientGameModel.setAvailableTowerColors(availableTowerColors);
                if (teamLeader && (clientState == ClientState.CHOOSING_TOWER_COLOR)) {
                    clientGameModel.sendChooseTowerColor(availableTowerColors);
                }
                break;
            case AVAILABLE_WIZARD_SEND:
                SyncInitMessage wizardMessage = (SyncInitMessage) message;
                List<Wizard> availableWizards = wizardMessage.getAvailableWizards();
                clientGameModel.setAvailableWizards(availableWizards);

                if (clientState == ClientState.CHOOSING_WIZARD) {
                    clientGameModel.sendChooseWizard(availableWizards);
                }
                break;

            case INIT_SEND:
                clientState = ClientState.GAME_START;
                AllGameMessage allGameMessage = (AllGameMessage) message;
                clientGameModel.initClientGameModel(allGameMessage);
                setNickname(clientGameModel.findPlayerById(client.getClientId()).getName());
                teamId = clientGameModel.findPlayerById(client.getClientId()).getTeam();
                break;


            case SYNC_STATE:
                SyncStateMessage syncStateMessage = (SyncStateMessage) message;
                manageSyncStateMessage(syncStateMessage);
                break;

            case TABLE:
                TableMessage tableMessage = (TableMessage) message;
                clientGameModel.setIslandList(tableMessage.getIslandList());
                clientGameModel.setCloudList(tableMessage.getCloudList());
                clientGameModel.setMotherNaturePos(tableMessage.getMotherNaturePos());
                break;
            case THROWN_ASSISTANT:
                ThrownAssistantMessage thrownAssistantMessage = (ThrownAssistantMessage) message;
                ReducedPlayer thrownAssistantPlayer = clientGameModel.findPlayerById(thrownAssistantMessage.getPlayerId());
                thrownAssistantPlayer.setSelectedAssistant(thrownAssistantMessage.getAssistant());
                break;
            case DASHBOARD:
                DashboardMessage dashboardMessage = (DashboardMessage) message;
                ReducedPlayer dashboardPlayer = clientGameModel.findPlayerById(dashboardMessage.getPlayerId());
                dashboardPlayer.setDashboard(dashboardMessage.getDashboard());
                break;
            case CHARACTER_TABLE:
                CharacterTableMessage characterTableMessage = (CharacterTableMessage) message;
                clientGameModel.setCharactersList(characterTableMessage.getCharactersList());
                clientGameModel.setTableMoney(characterTableMessage.getTableMoney());
                for (Map.Entry<Integer, Integer> entry : characterTableMessage.getNumOfMoneyMap().entrySet()) {
                    ReducedPlayer moneyPlayer = clientGameModel.findPlayerById(entry.getKey());
                    moneyPlayer.setNumOfMoney(entry.getValue());
                }
                break;
            case ASSISTANTS_SEND:
                AssistantsSendMessage assistantsSendMessage = (AssistantsSendMessage) message;
                clientGameModel.setAssistantList(assistantsSendMessage.getAssistantsList());
                break;

            case THROWN_CHARACTER:
                ThrownCharacterMessage thrownCharacterMessage = (ThrownCharacterMessage) message;
                if(thrownCharacterMessage.getPlayerId()!=clientGameModel.getMyPlayerId()) {
                    String nameP = clientGameModel.findPlayerById(thrownCharacterMessage.getPlayerId()).getName();
                    clientGameModel.show(nameP + " threw character: " + thrownCharacterMessage.getCharacterId());
                }
                break;

            case WIN: //we decided that we can restart a new game after finished one
                StringMessage stringMessage = (StringMessage) message;
                clientState = ClientState.GAME_FINISHED;
                if(clientGameModel.getNumOfPlayers() ==4){
                    clientGameModel.show("The winners are: " + stringMessage.getString() + " \nYou can play again if you want!");
                }
                else{clientGameModel.show("The winner is: " + stringMessage.getString() + " \n|You can play again if you want!|");}

                //clientGameModel.show("'Restarting'...");
                clientState = ClientState.CHOOSING_JOIN_CREATE;
                teamLeader = true;
                teamId = client.getClientId();
                clientGameModel.askCreateOrJoin();
                break;


            case OK_REPLY: //used also like a state sync message
                manageOkReplyMessage();//we use a private method to have more order
                break;


            case ERROR_REPLY: //It will show the error and reset the previous state, it will interrupt the current thread?
                StringMessage errorReply = (StringMessage) message;
                clientGameModel.show("ErrorMessage: \n" + errorReply.getString());
                manageErrorReplyMessage();
                break;


            case DISCONNECTION:  //when someone else disconnected
                StringMessage disconnectionMessage = (StringMessage) message;
                clientState = ClientState.CHOOSING_JOIN_CREATE;
                clientGameModel.show(disconnectionMessage.getString() +" disconnected... returning to home\n");
                teamLeader = true;
                teamId = client.getClientId();
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

        if (ip.matches(regex)) {
            return true;
        } else {
            try {
                new URI(ip);
                return true;
            }
            // If there was an Exception
            // while creating URL object
            catch (Exception e) {
                return false;
            }

        }

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
            clientGameModel.sendLoginRequest();
        } catch (IOException e) {
            clientState = ClientState.APPLICATION_START;
            clientGameModel.sendServerInfoRequest();
        }
    }

    /**
     * Request of the client to login containing the desired name
     * @param input the player's desired name
     */
    @Override
    public void onSendLoginRequest(String input){
        StringMessage loginRequest = new StringMessage(MessageType.LOGIN_REQUEST, client.getClientId(), true,input);
        clientState = ClientState.LOGIN_ACCEPTED;
        client.sendMessage(loginRequest);
    }

    /**
     * Request containing the name,number of players and game mode of a lobby to be created by the client
     * @param input the name of the lobby
     * @param numOfPlayers the number of players of the lobby
     * @param expertMode the game mode of the lobby
     */
    @Override
    public void onSendNewLobbyRequest(String input,int numOfPlayers,boolean expertMode){
        Lobby lobby = new Lobby(numOfPlayers,0,expertMode,input);
        NewLobbyMessage message = new NewLobbyMessage(client.getClientId(),lobby);
        clientState = ClientState.CHOSEN_LOBBY;
        client.sendMessage(message);
    }

    /**
     * Request to showcase the list of available lobbies,at this point the client is choosing a lobby
     */
    @Override
    public void onSendLobbiesRequest(){
        GenericMessage message = new GenericMessage(MessageType.LOBBIES_REQUEST,client.getClientId(),true);
        clientState = ClientState.CHOOSING_LOBBY;
        client.sendMessage(message);
    }

    /**
     * The client sends a message containing the chosen lobby from the available lobbies
     * @param chosenLobby the lobby selected by the player
     */
    @Override
    public void onSendChooseLobby(String chosenLobby){
        StringMessage message =  new StringMessage(MessageType.CHOOSE_LOBBY,client.getClientId(),true, chosenLobby);
        clientState = ClientState.CHOSEN_LOBBY;
        client.sendMessage(message);
    }

    /**
     * The client sends a message containing the chosen team player from the available players
     * @param chosenTeamPlayerId the id of the selected team player
     */
    @Override
    public void onSendChooseTeam(int chosenTeamPlayerId) {
        ChooseTeamMessage message = new ChooseTeamMessage(client.getClientId(),chosenTeamPlayerId);
        clientState = ClientState.CHOSEN_TEAM;
        client.sendMessage(message);
    }

    /**
     * The client sends a message containing the chosen tower color from the available tower colors
     * @param color the tower color selected by the player
     */
    @Override
    public void onSendChooseTowerColor(TowerColor color) {
        ChooseTowerColorMessage message = new ChooseTowerColorMessage(client.getClientId(),color);
        clientState = ClientState.CHOSEN_TOWER_COLOR;
        client.sendMessage(message);
    }

    /**
     * The client sends a message containing the chosen wizard from the available wizards
     * @param wizard the wizard selected by the player
     */
    @Override
    public void onSendChooseWizard(Wizard wizard) {
        ChooseWizardMessage message = new ChooseWizardMessage(client.getClientId(),wizard);
        clientState = ClientState.CHOSEN_WIZARD;
        client.sendMessage(message);
    }

    /**
     * The client sends a message containing the chosen assistant's id from the available assistants
     * @param selectedAssistantId the assistant's id selected by the player
     */
    @Override
    public void onSendSelectAssistant(int selectedAssistantId) {
        if(ClientInputVerifier.verifyAssistant(clientGameModel,selectedAssistantId)) {
            SelectAssistantMessage selectAssistantMessage = new SelectAssistantMessage(client.getClientId(), selectedAssistantId);
            clientState = ClientState.SELECTED_ASSISTANT;
            client.sendMessage(selectAssistantMessage);
        }
        else {
            clientGameModel.show("The selected assistant isn't available,try again!");
        }
    }

    /**
     * The player sends a message containing the selected student's index and the island's index where the student has to be moved
     * @param selectedStudentIndex the index of the chosen student
     * @param selectedIslandIndex the index of the island where the student has to be moved
     */
    @Override
    public void onSendMoveAStudentIsland(int selectedStudentIndex, int selectedIslandIndex) {
        if(ClientInputVerifier.verifyStudentIsland(clientGameModel,selectedStudentIndex,selectedIslandIndex)) {
            MoveStudentIslandMessage message = new MoveStudentIslandMessage(client.getClientId(), selectedStudentIndex, selectedIslandIndex);
            clientState = ClientState.MOVED_STUDENT;
            client.sendMessage(message);
        }
        else {
            clientGameModel.show("The selected student or island aren't available,try again!");
        }
    }

    /**
     * The player chooses a student to be moved to the dashboard,where it's added to the hall based on its color
     * the client then sends a message containing the information
     * @param selectedStudentIndex
     */
    @Override
    public void onSendMoveAStudentDashboard(int selectedStudentIndex) {
        if(ClientInputVerifier.verifyStudentDashboard(clientGameModel,selectedStudentIndex)) {
            MoveStudentDashboardMessage message = new MoveStudentDashboardMessage(client.getClientId(), selectedStudentIndex);
            clientState = ClientState.MOVED_STUDENT;
            client.sendMessage(message);
        }
        else {
            clientGameModel.show("The selected student isn't available,try again!");
        }
    }

    /**
     * The player choose the island where mother nature has to be moved
     * the client sends a message containing the info
     * @param selectedIslandIndex
     */
    @Override
    public void onSendMoveMotherNature(int selectedIslandIndex) {
        if(ClientInputVerifier.verifyMotherNaturePos(clientGameModel,selectedIslandIndex,usedCharacter)) {
            MoveMotherNatureMessage message = new MoveMotherNatureMessage(client.getClientId(), selectedIslandIndex);
            clientState = ClientState.MOVED_MOTHER_NATURE;
            client.sendMessage(message);
        }
        else {
            clientGameModel.show("You can't do this move,try again!");
        }
    }


    /**
     *the player chooses a cloud
     *the client sends a message containing the chosen cloud index
     * @param selectedCloudIndex
     */
    @Override
    public void onSendChooseCloud(int selectedCloudIndex) {
        if(ClientInputVerifier.verifyCloud(clientGameModel,selectedCloudIndex)) {
            TakeFromCloudMessage message = new TakeFromCloudMessage(client.getClientId(), selectedCloudIndex);
            clientState = ClientState.CHOSEN_CLOUD;
            client.sendMessage(message);
        }
        else {
            clientGameModel.show("The selected cloud isn't available,try again!");
        }
    }

    /**
     * The player chooses a character to be used
     * The client sends a message containing the character id
     * @param characterId
     */
    @Override
    public void onAskCharacter(int characterId) {
        if(ClientInputVerifier.verifyCharacter(clientGameModel, characterId)){
            prevClientState = clientState;
            usedCharacter = characterId;
            if(characterId == 1 || characterId== 3 || characterId == 5 || characterId == 7 || characterId == 9 || characterId == 10 || characterId== 11 || characterId ==12){
                clientState = ClientState.USING_CHARACTER;
                clientGameModel.askCharacterParameters(characterId);}
            else{
                MessageCharacterParameters params = new MessageCharacterParameters();
                params.setCharacterId(characterId);
                UseCharacterMessage message = new UseCharacterMessage(client.getClientId(), params);
                clientState = ClientState.USED_CHARACTER;
                client.sendMessage(message);
            }
        }

        else{
            clientGameModel.show("The chosen character is not available");
        }
    }

    /**
     * The player uses a character
     * the client sends a message containing the character parameters
     * @param params
     */
    @Override
    public void onSendUseCharacter(MessageCharacterParameters params) {
        if(ClientInputVerifier.verifyCharacterParams(params, clientGameModel)){
        UseCharacterMessage message = new UseCharacterMessage(client.getClientId(), params);
        clientState = ClientState.USED_CHARACTER;
        client.sendMessage(message);}
        else{
            clientGameModel.show("The params are not correct");
        }
    }

    /**
     * A text message notifies that a client lost connection,the game is automatically ended
     */
    public void onSocketDisconnect(){   //this happens only when there is a mine critical problem
        clientGameModel.show("\nConnection lost, disconnecting...");
        clientState = ClientState.GAME_FINISHED;
        System.exit(0);
    }

    //UTILS METHODS

    /**
     * this method manages ok reply messages for each client state
     */
    private void manageOkReplyMessage(){ //if we don't receive error we suppose that everything is ok
        switch(clientState){
            case CHOOSING_TEAM:
                teamLeader = false; //and with init send I will receive my teamPlayer
                clientGameModel.showChosenTeam("Chosen, now you are not the leader, \nyou can't choose tower color");
                clientState = ClientState.CHOSEN_TEAM;
                break;

            case CHOSEN_TEAM:
                teamLeader = true; //for security, I set it true
                clientGameModel.showChosenTeam("Ok, you are the leader of the team, \nyou can choose tower color"); //they can choose if communicate outside the game
                teamId = client.getClientId(); //for security, I set it, the team Id is mine
                //clientState = ClientState.CHOOSING_TOWER_COLOR; //and now I will wait for available ... send
                break;

            case CHOSEN_TOWER_COLOR:
                clientGameModel.show("Color chosen, waiting for other players to choose color");
                //clientState = ClientState.CHOOSING_WIZARD; //and now I will wait for available ... send
                break;
            case CHOSEN_WIZARD:
                clientGameModel.show("Wizard chosen, waiting for other players to choose wizard");
                //clientState = ClientState.GAME_START;
                break;
            default:
                break;
        }
    }

    /**
     * This method manages error replies for each client state
     */
    private void manageErrorReplyMessage(){
        switch(clientState) {
            case LOGIN_ACCEPTED:
                clientState = ClientState.REQUESTING_LOGIN;
                clientGameModel.sendLoginRequest();
                break;

            case CHOSEN_LOBBY:
                clientState = ClientState.CHOOSING_JOIN_CREATE;
                clientGameModel.askCreateOrJoin();
                break;

            case CHOSEN_TEAM:
                clientState = ClientState.CHOOSING_TEAM;
                clientGameModel.sendChooseTeam(clientGameModel.getAvailableTeamPlayers());
                break;

            case CHOSEN_TOWER_COLOR:
                clientState = ClientState.CHOOSING_TOWER_COLOR;
                clientGameModel.sendChooseTowerColor(clientGameModel.getAvailableTowerColors());
                break;

            case CHOSEN_WIZARD:
                clientState = ClientState.CHOOSING_WIZARD;
                clientGameModel.sendChooseWizard(clientGameModel.getAvailableWizards());
                break;

            case SELECTED_ASSISTANT:
                clientState = ClientState.THROWING_ASSISTANT;
                clientGameModel.sendSelectAssistant();
                break;

            case MOVED_STUDENT: //moved in dashboard or island you have to retry!
                clientState = ClientState.CHOOSING_WHERE_TO_MOVE_STUDENT;
                clientGameModel.askWhereToMoveStudent();
                break;

            case MOVED_MOTHER_NATURE:
                clientState = ClientState.MOVING_MOTHER_NATURE;
                clientGameModel.sendMoveMotherNature();
                break;

            case CHOSEN_CLOUD:
                clientState = ClientState.CHOOSING_CLOUD;
                clientGameModel.sendChooseCloud();
                break;
            case USED_CHARACTER:
                clientState= prevClientState;
                usedCharacter = -1;
                //clientGameModel.show("Retry to use character or continue playing"); //for clarity
                //only the print of the string is ok and reset to the prev clientState
                break;

            default:
                break;
        }
    }

    /**
     * This method manages sync state message for each game state
     * @param syncStateMessage the message used to sync states
     */
    private  void manageSyncStateMessage(SyncStateMessage syncStateMessage){
        GameState gameState = syncStateMessage.getGameState();
        SettingState settingState = syncStateMessage.getSettingState();
        RoundState roundState = syncStateMessage.getRoundState();
        TurnState turnState = syncStateMessage.getTurnState();
        int currPlayerId = syncStateMessage.getCurrPlayerId();


        if(gameState== GameState.SETTING_STATE) {
            switch (settingState) {
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
            if(clientGameModel.getRoundState()!=RoundState.PLANNING_STATE){
                for(ReducedPlayer p : clientGameModel.getPlayersList()){
                    p.setSelectedAssistant(null);
                }
                usedCharacter = -1;
                clientGameModel.setRoundState(roundState);
            }

            if(currPlayerId == client.getClientId()){
                clientState = ClientState.THROWING_ASSISTANT;
                clientGameModel.showGame();
                if (teamLeader == false) {
                    String leaderName = clientGameModel.findPlayerById(teamId).getName();
                    clientGameModel.show("Your team leader is: " + leaderName);
                }
                clientGameModel.sendSelectAssistant();
            }
            else {
                clientState = ClientState.WAITING_FOR_YOUR_TURN;
                String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                clientGameModel.showGame();
                if (teamLeader == false) {
                    String leaderName = clientGameModel.findPlayerById(teamId).getName();
                    clientGameModel.show("Your team leader is: " + leaderName);
                }
                clientGameModel.show(playerName + " is throwing an assistant..." );

            }
        }
        else if (gameState == GameState.INGAME_STATE && roundState == RoundState.ACTION_STATE){
            clientGameModel.setRoundState(roundState);
            clientGameModel.showGame();
            if(currPlayerId == client.getClientId()){
                switch(turnState){
                    case MOVE_STUDENT_STATE:
                        clientState = ClientState.CHOOSING_WHERE_TO_MOVE_STUDENT; //in case of used character we have to choose again where to move
                        clientGameModel.askWhereToMoveStudent();  //server will resend me syncStateMessage until I finished moving all the students
                        break;
                    case MOVE_MOTHER_NATURE_STATE:
                        clientState = ClientState.MOVING_MOTHER_NATURE;
                        clientGameModel.sendMoveMotherNature();
                        break;
                    case CHOOSE_CLOUD_STATE:
                        clientState = ClientState.CHOOSING_CLOUD;
                        clientGameModel.sendChooseCloud();
                        break;
                }
            }
            else{
                clientState = ClientState.WAITING_FOR_YOUR_TURN;
                if (turnState == TurnState.MOVE_STUDENT_STATE){
                    String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                    clientGameModel.show(playerName + " is moving a student..." );

                }
                else if (turnState == TurnState.MOVE_MOTHER_NATURE_STATE){
                    String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                    clientGameModel.show(playerName + " is moving mother nature..." );

                }
                else if (turnState == TurnState.CHOOSE_CLOUD_STATE){
                    String playerName = clientGameModel.findPlayerById(currPlayerId).getName();
                    clientGameModel.show(playerName + " is choosing a cloud..." );
                }

            }
        }

    }

    /**
     *
     * @return the client game model
     */
    public ClientGameModel getClientGameModel() {
        return clientGameModel;
    }

    /**
     * Sets the nickname
     * @param nickname the name of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return the client state
     */
    public ClientState getClientState() {
        return clientState;
    }

    /**
     * Sets the client state
     * @param clientState the current state of the client
     */
    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }
}