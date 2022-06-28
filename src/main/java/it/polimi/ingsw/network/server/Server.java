package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controllers.Controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the server object
 * it handles the connections of the clients
 * it is also used to manage the creation and join of the lobbies
 */
public class Server{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Map<Integer, RemoteView> remoteViewMap; //player id - remote view
    private Map<Integer, SocketClientManager> idSocketMap; //player id - socket
    private Map<SocketClientManager, Integer> socketIdMap; //socket - player id
    private Map<Integer, Controller> playerControllerMap; //player id - associated controller
    private Map<String,Controller> controllersMap; //controller name - controller

    private List<Player> allPlayersList;
    private List<Player> watchingLobbiesPlayersList;

    private int playersIdCounter = 0;
    public static final int SERVERID = 9999;

    /**
     * Default constructor
     * @param port the port on witch the server will listen
     */
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

    /**
     * Called from a client thread when arrives a message of initialization
     * it gives the message to the correct function
     * @param message received init message
     * @param socketManager reference of the socketManager that received the message
     */
    public synchronized void onInitMessageReceived(Message message, SocketClientManager socketManager){
        logger.log(Level.INFO,message.getMessageType()+ " received from client "+ message.getSenderId());

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

    /**
     * creates and stores a player
     * it assigns a new id to the created player
     * check if the name is valid
     * @param name player name
     * @param socketManager reference of the socketManager that received the message
     */
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
            logger.log(Level.INFO,"added new player " + name + " id: " + playersIdCounter);
        }
        else{
            socketManager.sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true,"Nickname already used or empty"));
        }
    }

    /**
     * send the available lobbies to the player
     * called when player choose join
     * @param senderId id of the player
     */
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

    /**
     * creates a new lobby and its controller
     * add the player which created the lobby
     * @param senderId id of the player
     * @param lobby lobby object
     */
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

    /**
     * add a player that choose join to the selected lobby
     * @param senderId id of the player
     * @param lobbyName name of the lobby
     */
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

            logger.log(Level.INFO,"added player "+senderId+" to lobby "+ lobbyName);

            //if a user entered a lobby we update the list for everybody waiting
            broadcastAvailableLobbies();
        }
    }

    /**
     * handle disconnection of a client
     * closes the open game if exist
     * send the disconnection message to the other clients
     * @param client socket which disconnected
     */
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

            if(controller!=null && controller.isOpen()){
                broadcastAvailableLobbies();
            }

            logger.log(Level.INFO,"removed disconnected player "+disconnectedPlayer.getName());
        }

    }

    /**
     * send the disconnection message to all clients in a lobby
     * @param controller closed controller
     * @param disconnectedPlayer player that disconnected
     */
    private void broadcastDisconnectionToLobby(Controller controller, Player disconnectedPlayer){
        for(Integer playerId : playerControllerMap.keySet()){
            if(playerControllerMap.get(playerId)==controller && playerId != disconnectedPlayer.getId()){
                idSocketMap.get(playerId).sendMessage(new StringMessage(MessageType.DISCONNECTION, SERVERID, true, disconnectedPlayer.getName()));
            }
        }
    }


    /**
     * check for finished game and remove that
     */
    public synchronized void removeFinishedController(){
        List<String> controllersToRemove = new ArrayList<>();
        for(String s : controllersMap.keySet()){
            if(controllersMap.get(s).isFinished()){
                controllersToRemove.add(s);
            }
        }
        for (String name : controllersToRemove){
            deleteLobby(controllersMap.get(name));
        }
    }

    /**
     * delete a lobby
     * removes controller and all observers
     * @param controller controller to close
     */
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
        }

        //remove the controller from the controllers map
        controllersMap.remove(controllerToRemove);
        logger.log(Level.INFO,"removed controller "+ controllerToRemove);
    }

    /**
     * get the player object with requested id
     * @param id
     * @return a player chosen by id
     */
    private Player getPlayerById(int id){
        for (Player p : allPlayersList){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    /**
     * check if the selected name is valid
     * @param name
     * @return true if valid, false if not valid
     */
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

    /**
     * check if the choosen name for the controller is valid
     * @param name
     * @return true if valid, false if not valid
     */
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

    /**
     * get the RemoteView object with requested id
     * @param id
     * @return requested RemoteView or null if not exist
     */
    public RemoteView getRemoteViewByPlayerId(int id){
        return remoteViewMap.get(id);
    }

    /**
     * get the lobby by name
     * @param lobby
     * @return requested controller or null if not exist
     */
    private Controller getLobbyByName(String lobby){
        return controllersMap.get(lobby);
    }

    /**
     *
     * @return list of the open lobbies
     */
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

    /**
     * check for network security if the id is correctly associated with the socketManager
     * @param message
     * @param socketManager
     * @return true if found, false if not found
     */
    public boolean checkIdSocket(Message message, SocketClientManager socketManager){
        if(message.getMessageType() != MessageType.LOGIN_REQUEST && idSocketMap.get(message.getSenderId()) != socketManager){
            logger.log(Level.SEVERE,"Received message with invalid id");
            return false;
        }
        return true;
    }

    /**
     * send error message to a specific client
     * @param clientId client that generated the error
     * @param error text of the error
     */
    private void sendError(int clientId, String error){
        idSocketMap.get(clientId).sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true, error));
    }


    /**
     * sends to all players in pre-lobby an update of the available lobbies
     */
    private void broadcastAvailableLobbies(){
        for (Player waitingPlayer : watchingLobbiesPlayersList){
            SocketClientManager destSocket = idSocketMap.get(waitingPlayer.getId());
            LobbyMessage lobbyMessage = new LobbyMessage(SERVERID, getAvailableLobbiesList());
            destSocket.sendMessage(lobbyMessage);
        }
    }
}
