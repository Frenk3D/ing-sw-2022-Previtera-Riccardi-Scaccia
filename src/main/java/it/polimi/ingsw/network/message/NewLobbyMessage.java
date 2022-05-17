package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.Lobby;

public class NewLobbyMessage extends Message{

    Lobby lobby;

    public NewLobbyMessage(int senderId, Lobby lobby){
        super(MessageType.NEW_LOBBY_REQUEST,senderId,true);
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
