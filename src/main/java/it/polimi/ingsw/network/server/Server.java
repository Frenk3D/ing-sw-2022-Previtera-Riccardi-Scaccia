package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.LobbyMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NewLobbyMessage;
import it.polimi.ingsw.network.message.StringMessage;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server{
    private Map<Integer, RemoteView> remoteViewMap;
    private Map<Integer,SocketClientManager> socketClientManagerMap;
    private Map<SocketClientManager, Player> allPlayersMap;
    private Map<Player, Controller> playerControllerMap;
    private Map<String,Controller> controllersMap;
    private int playersIdCounter = 0;

    public Server(int port){
        new SocketServerManager(this,port);

    }

    public void onInitMessageReceived(Message message, SocketClientManager socketClientManager){
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

    public void createPlayer(String name, SocketClientManager socket){

    }

    public void newLobby(int senderId, Lobby lobby){

    }

    public void deleteLobby(String name){

    }

    public void addToLobby(int senderId, String lobbyName){

    }

    public void onDisconnect(SocketServerManager client){

    }

    public Player getPlayerById(){

        return null;
    }

    public void onDisconnect(SocketClientManager client){

    }

    public void sendAvailableLobbies(int senderId){
        List<Lobby> availableLobbiesList = new ArrayList<>();
        for (String s : controllersMap.keySet()){
            Controller c = controllersMap.get(s);
            if(c.isOpen()){
            availableLobbiesList.add(new Lobby(c.getNumOfPlayer(),c.getActualNumOfPlayers(),c.getExpertMode(),s));
            }
        }
        LobbyMessage lobbyMessage = new LobbyMessage(GameModel.SERVERID,availableLobbiesList);
        socketClientManagerMap.get(senderId).sendMessage(lobbyMessage);
    }

    public RemoteView getRemoteViewByPlayerId(int id){
        return remoteViewMap.get(id);
    }

    public Controller getLobbyByName(String lobby){
        return controllersMap.get(lobby);
    }

}
