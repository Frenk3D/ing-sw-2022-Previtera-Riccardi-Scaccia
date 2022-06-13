package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server{
    private Map<Integer, RemoteView> remoteViewMap; //player id - remote view
    private Map<Integer, SocketClientManager> idSocketMap; //player id - socket
    private Map<SocketClientManager, Integer> socketIdMap; //socket - player id
    private Map<Integer, Controller> playerControllerMap; //player id - associated controller
    private Map<String,Controller> controllersMap; //controller name - controller

    private List<Player> allPlayersList;
    private List<Player> watchingLobbiesPlayersList;

    private int playersIdCounter = 0;
    public static final int SERVERID = 9999;

    public Server(int port){
        remoteViewMap = new HashMap<>();
        idSocketMap = new HashMap<>();
        socketIdMap = new HashMap<>();
        playerControllerMap = new HashMap<>();
        controllersMap = new HashMap<>();

        allPlayersList = new ArrayList<>();
        watchingLobbiesPlayersList = new ArrayList<>();

        SocketServerManager serverManager = new SocketServerManager(this,port);
        Thread thread = new Thread(serverManager, "server");
        thread.start();
    }

    public synchronized void onInitMessageReceived(Message message, SocketClientManager socketManager){
        System.out.println(message.getMessageType()+ " received from client "+ message.getSenderId());

        switch (message.getMessageType()){
            case LOGIN_REQUEST:
                StringMessage stringMessage = (StringMessage) message;
                createPlayer(stringMessage.getString(),socketManager);
                break;
            case NEW_LOBBY_REQUEST:
                NewLobbyMessage newLobbyMessage = (NewLobbyMessage) message;
                newLobby(newLobbyMessage.getSenderId(), newLobbyMessage.getLobby());
                break;
            case LOBBIES_REQUEST:
                sendAvailableLobbies(message.getSenderId());
                break;
            case CHOOSE_LOBBY:
                StringMessage stringMessage1 = (StringMessage) message;
                addToLobby(stringMessage1.getSenderId(), stringMessage1.getString());
                break;
        }

    }

    private void createPlayer(String name, SocketClientManager socketManager){
        if(checkName(name) && socketIdMap.get(socketManager)==null) {
            playersIdCounter++;
            Player player = new Player(name, playersIdCounter);
            allPlayersList.add(player);
            idSocketMap.put(playersIdCounter,socketManager);
            socketIdMap.put(socketManager,playersIdCounter);
            RemoteView remoteView = new RemoteView(socketManager);
            socketManager.setRemoteView(remoteView);
            remoteViewMap.put(playersIdCounter,remoteView);

            socketManager.sendMessage(new StringMessage(MessageType.LOGIN_REPLY, SERVERID, true, playersIdCounter + ""));
            System.out.println("Server: Added new player " + name + " id: " + playersIdCounter);
        }
        else{
            socketManager.sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true,"Nickname already used"));
        }
    }


    private void sendAvailableLobbies(int senderId){ //the player requested join game, so he remains waiting for a lobby to play
        SocketClientManager destSocket = idSocketMap.get(senderId);
        Player player = getPlayerById(senderId);
        if(watchingLobbiesPlayersList.contains(player)){
            sendError(senderId,"Command not available in this moment");
        }
        else if(playerControllerMap.get(senderId)!=null){
            sendError(senderId,"Command not available in this moment");
        }
        else {
            watchingLobbiesPlayersList.add(player);
            LobbyMessage lobbyMessage = new LobbyMessage(SERVERID, getAvailableLobbiesList());
            destSocket.sendMessage(lobbyMessage);
        }
    }

    private void newLobby(int senderId, Lobby lobby){
        if(playerControllerMap.get(senderId) != null){ //player already associated to a controller
            sendError(senderId,"Command not available in this moment");
        }
        else if(!checkControllerName(lobby.getName())){ //name already used
            sendError(senderId,"The requested name is already used");
        }
        else if(watchingLobbiesPlayersList.contains(getPlayerById(senderId))){ //the user is in choose lobby mode
            sendError(senderId,"Command not available in this moment");
        }
        else if(lobby.getNumOfPlayers()<2 || lobby.getNumOfPlayers()>4){ //wrong parameters
            sendError(senderId,"The parameters are not correct");
        }
        else{
            Controller controller = new Controller();
            controller.setServer(this);
            controller.configure(lobby.getNumOfPlayers(),lobby.isExpertMode());
            controllersMap.put(lobby.getName(),controller);

            addToLobby(senderId,lobby.getName());
        }
    }

    private void addToLobby(int senderId, String lobbyName){
        Player player = getPlayerById(senderId);
        Controller controller = getLobbyByName(lobbyName);

        if(playerControllerMap.get(senderId) != null){
            sendError(senderId,"Command not available in this moment");
        }
        else if(controller == null){
            sendError(senderId,"The requested controller does not exist");
        }
        else if(!controller.isOpen()) {
            sendError(senderId,"The selected controller is closed");
        }
        else {
            playerControllerMap.put(senderId,controller);
            RemoteView remoteView = getRemoteViewByPlayerId(senderId);
            controller.getGame().addObserver(remoteView);
            remoteView.addObserver(controller);
            controller.addPlayer(player);
            watchingLobbiesPlayersList.remove(player); //if the player choose new game the method applies no changes on the list

            System.out.println("Server: added player "+senderId+" to lobby "+ lobbyName);

            //if a user entered a lobby we update the list for everybody waiting
            for (Player waitingPlayer : watchingLobbiesPlayersList){
                SocketClientManager destSocket = idSocketMap.get(waitingPlayer.getId());
                LobbyMessage lobbyMessage = new LobbyMessage(SERVERID, getAvailableLobbiesList());
                destSocket.sendMessage(lobbyMessage);
            }
            pumpControllerCommands(controller);

        }
    }

    public synchronized void onDisconnect(SocketClientManager client){
        Integer playerId = socketIdMap.get(client);

        if(playerId != null) {
            Player disconnectedPlayer = getPlayerById(playerId);
            Controller controller = playerControllerMap.get(disconnectedPlayer.getId());

            if(controller != null) {
                broadcastDisconnectionToLobby(controller, disconnectedPlayer);
                deleteLobby(controller);
            }

            socketIdMap.remove(client);
            idSocketMap.remove(disconnectedPlayer.getId());
            remoteViewMap.remove(disconnectedPlayer.getId());
            allPlayersList.remove(disconnectedPlayer);
            watchingLobbiesPlayersList.remove(disconnectedPlayer);
            System.out.println("Removed player "+disconnectedPlayer.getName());
        }

    }

    private void broadcastDisconnectionToLobby(Controller controller, Player disconnectedPlayer){
        for(Integer playerId : playerControllerMap.keySet()){
            if(playerControllerMap.get(playerId)==controller && playerId != disconnectedPlayer.getId()){
                idSocketMap.get(playerId).sendMessage(new StringMessage(MessageType.DISCONNECTION, SERVERID, true, disconnectedPlayer.getName()));
            }
        }
    }

    public void deleteLobby(Controller controller){
        List<Integer> playersToRemove = new ArrayList<>();
        String controllerToRemove = null;


        for(Integer playerId : playerControllerMap.keySet()){ //finds the player in the controller
            if(playerControllerMap.get(playerId)==controller){
                playersToRemove.add(playerId);
            }
        }

        for(String controllerName : controllersMap.keySet()) { //finds the name of the controller
            if (controllersMap.get(controllerName) == controller) {
                controllerToRemove = controllerName;
                break;
            }
        }

        //remove the players from player-controller association map
        for(Integer id : playersToRemove){
            RemoteView remoteView = getRemoteViewByPlayerId(id);
            remoteView.removeObserver(controller);
            controller.getGame().removeObserver(remoteView);
            playerControllerMap.remove(id);
            getPlayerById(id).reset();
            System.out.println("Removed player "+ id);
        }

        //remove the controller from the controllers map
        controllersMap.remove(controllerToRemove);
        System.out.println("Removed controller "+ controllerToRemove);
    }

    private Player getPlayerById(int id){
        for (Player p : allPlayersList){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    private boolean checkName(String name){
        if(name == null || name.isEmpty()) {
            return false;
        }
        for (Player p : allPlayersList){
            if(p.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    private boolean checkControllerName(String name){
        if(name == null || name.isEmpty()) {
            return false;
        }
        for(String controllerName : controllersMap.keySet()){
            if(controllerName.equals(name)){
                return false;
            }
        }
        return true;
    }

    public RemoteView getRemoteViewByPlayerId(int id){
        return remoteViewMap.get(id);
    }

    private Controller getLobbyByName(String lobby){
        return controllersMap.get(lobby);
    }

    private List<Lobby> getAvailableLobbiesList(){
        List<Lobby> availableLobbiesList = new ArrayList<>();
        for (String s : controllersMap.keySet()) {
            Controller c = controllersMap.get(s);
            if (c.isOpen()) {
                availableLobbiesList.add(new Lobby(c.getNumOfPlayer(), c.getActualNumOfPlayers(), c.getExpertMode(), s));
            }
        }
        return availableLobbiesList;
    }

    public boolean checkIdSocket(Message message, SocketClientManager socketManager){
        if(message.getMessageType() != MessageType.LOGIN_REQUEST && idSocketMap.get(message.getSenderId()) != socketManager){
            System.out.println("Received message with invalid id");
            return false;
        }
        return true;
    }

    private void sendError(int clientId, String error){
        idSocketMap.get(clientId).sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true, error));
        System.out.println("Sent error to "+clientId +" ,"+ error);
    }

    public void createTestController(){
        Controller controller = new Controller();
        controller.setServer(this);
        controller.configure(2,false);
        controllersMap.put("test",controller);

        Controller controller1 = new Controller();
        controller1.setServer(this);
        controller1.configure(3,false);
        controllersMap.put("mytest",controller1);
    }

    private void pumpControllerCommands(Controller controller){
        if(controllersMap.get("test")!= null && controllersMap.get("test").equals(controller)&&!controller.isOpen() && playersIdCounter == 2){
            System.out.println("pumping controller");
            controller.chooseTowerColor(1, TowerColor.BLACK);
            controller.chooseTowerColor(2,TowerColor.WHITE);
            controller.chooseWizard(1, Wizard.ASIATIC);
            controller.chooseWizard(2,Wizard.KING);
        }
        else if(controllersMap.get("mytest")!= null && controllersMap.get("mytest").equals(controller)&&!controller.isOpen() && playersIdCounter == 3){
            System.out.println("pumping controller");
            controller.chooseTowerColor(1, TowerColor.BLACK);
            controller.chooseTowerColor(2,TowerColor.WHITE);
            controller.chooseTowerColor(3,TowerColor.GRAY);
            controller.chooseWizard(1, Wizard.ASIATIC);
            controller.chooseWizard(2,Wizard.KING);
            controller.chooseWizard(3,Wizard.WITCH);
        }
    }
}
