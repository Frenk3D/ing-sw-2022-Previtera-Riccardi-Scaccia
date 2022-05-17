package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.Lobby;

import java.util.List;

public class LobbyMessage extends Message{
    List<Lobby> lobbiesList;

    public LobbyMessage(int senderId, List<Lobby> lobbiesList){
        super(MessageType.AVAILABLE_LOBBIES, senderId, true);
        this.lobbiesList = lobbiesList;
    }

    public List<Lobby> getLobbiesList() {
        return lobbiesList;
    }
}
