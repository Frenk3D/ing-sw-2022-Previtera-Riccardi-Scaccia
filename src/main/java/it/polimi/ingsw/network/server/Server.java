package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
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
    private List<Player> waitingRoom;
    private int playersIdCounter = 0;
    public static final int SERVERID = 9999;

    public Server(int port){
        remoteViewMap = new HashMap<>();
        idSocketMap = new HashMap<>();
        socketIdMap = new HashMap<>();
        playerControllerMap = new HashMap<>();
        controllersMap = new HashMap<>();

        allPlayersList = new ArrayList<>();
        waitingRoom = new ArrayList<>();

        SocketServerManager serverManager = new SocketServerManager(this,port);
        Thread thread = new Thread(serverManager, "server");
        thread.start();
    }

    public void onInitMessageReceived(Message message, SocketClientManager socketClientManager){
        System.out.println(message.getMessageType()+ " received from client "+ message.getSenderId());
        switch (message.getMessageType()){
            case LOGIN_REQUEST:
                StringMessage stringMessage = (StringMessage) message;
                createPlayer(stringMessage.getString(),socketClientManager);
                break;
            case NEW_LOBBY_REQUEST:
                NewLobbyMessage newLobbyMessage = (NewLobbyMessage) message;
                newLobby(newLobbyMessage.getSenderId(), newLobbyMessage.getLobby());
                break;
            case LOBBY_REQUEST:
                sendAvailableLobbies(message.getSenderId());
                break;
            case CHOOSE_LOBBY:
                StringMessage stringMessage1 = (StringMessage) message;
                addToLobby(stringMessage1.getSenderId(), stringMessage1.getString());
                break;
        }

    }

    public void createPlayer(String name, SocketClientManager socketManager){
        if(checkName(name)) {
            playersIdCounter++;
            Player player = new Player(name, playersIdCounter);
            allPlayersList.add(player);
            waitingRoom.add(player);
            idSocketMap.put(playersIdCounter,socketManager);
            socketIdMap.put(socketManager,playersIdCounter);
            RemoteView remoteView = new RemoteView(socketManager);
            remoteViewMap.put(playersIdCounter,remoteView);

            socketManager.sendMessage(new StringMessage(MessageType.LOGIN_REPLY, SERVERID, playersIdCounter + ""));
            System.out.println("Server: Added new player " + name + " id: " + playersIdCounter);
        }
    }

    public void createTestController(){
        Controller controller = new Controller();
        controller.configure(2,false);
        controllersMap.put("test",controller);
    }

    public void newLobby(int senderId, Lobby lobby){

    }

    public void deleteLobby(String name){

    }

    public void addToLobby(int senderId, String lobbyName){
        Player player = getPlayerById(senderId);
        Controller controller = getLobbyByName(lobbyName);
        if(player == null || controller == null){
            System.out.println("Server: parameter error");
            return;
        }

        if(controller.isOpen()){
            playerControllerMap.put(senderId,controller);
            waitingRoom.remove(player);
            RemoteView remoteView = getRemoteViewByPlayerId(senderId);
            controller.getGame().addObserver(remoteView);
            remoteView.addObserver(controller);
            controller.addPlayer(player);
            System.out.println("Server: added player "+ senderId+ " to lobby "+ lobbyName);
        }

    }

    public void onDisconnect(SocketClientManager client){
        int playerId = socketIdMap.get(client);
        socketIdMap.remove(client);
        idSocketMap.remove(playerId);
        remoteViewMap.remove(playerId);
        removePlayer(playerId);
        System.out.println("Removed player "+playerId);
    }

    public Player getPlayerById(int id){
        for (Player p : allPlayersList){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    public void removePlayer(int id){
        Player p = getPlayerById(id);
        allPlayersList.remove(p);
        waitingRoom.remove(p);
    }

    public boolean checkName(String name){
        for (Player p : allPlayersList){
            if(p.getName().equals(name)){
                return false;
            }
        }
        return true;
    }


    public void sendAvailableLobbies(int senderId){
        SocketClientManager destSocket = idSocketMap.get(senderId);
        if(destSocket == null) {
            System.out.println("Server: send lobby error wrong id "+ senderId);
            return;
        }

        List<Lobby> availableLobbiesList = new ArrayList<>();
        for (String s : controllersMap.keySet()){
            Controller c = controllersMap.get(s);
            if(c.isOpen()){
            availableLobbiesList.add(new Lobby(c.getNumOfPlayer(),c.getActualNumOfPlayers(),c.getExpertMode(),s));
            }
        }
        LobbyMessage lobbyMessage = new LobbyMessage(SERVERID,availableLobbiesList);
        destSocket.sendMessage(lobbyMessage);
    }

    public RemoteView getRemoteViewByPlayerId(int id){
        return remoteViewMap.get(id);
    }

    public Controller getLobbyByName(String lobby){
        return controllersMap.get(lobby);
    }

    public void broadcastToLobby(String lobbyName){
        Controller controller = controllersMap.get(lobbyName);
    }

}
