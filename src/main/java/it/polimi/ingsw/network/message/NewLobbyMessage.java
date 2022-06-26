package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.Lobby;

/**
 * This class implements the NewLobbyMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains a new lobby
 */
public class NewLobbyMessage extends Message{

    Lobby lobby;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param lobby the new lobby
     */
    public NewLobbyMessage(int senderId, Lobby lobby){
        super(MessageType.NEW_LOBBY_REQUEST,senderId,true);
        this.lobby = lobby;
    }

    /**
     *
     * @return the lobby
     */
    public Lobby getLobby() {
        return lobby;
    }
}
