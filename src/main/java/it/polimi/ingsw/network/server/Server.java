package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.RemoteView;

import java.util.List;
import java.util.Map;

public class Server{
    Map<Integer, RemoteView> remoteViewMap;
    Map<SocketClientManager, Player> allPlayersMap;
    Map<Player, Controller> playerControllerMap;
    int playersIdCounter = 0;
    List<Controller> controllersList;

    public Server(int port){

    }

    public void onInitMessageReceived(Message message, SocketClientManager socketClientManager){


    }

    public void createPlayer(String name){

    }

    public void newLobby(String name){

    }

    public void deleteLobby(String name){

    }

    public void addToLobby(Player player, String lobbyName){

    }

    public void onDisconnect(SocketServerManager client){

    }

    public Player getPlayerById(){

        return null;
    }

    public Controller getLobbyByName(String lobby){

        return null;
    }

    public void onDisconnect(SocketClientManager client){

    }

    public RemoteView getRemoteViewById(int id){

        return null;
    }

    public List<Lobby> getAvailableLobbies(){

        return null;
    }


}
