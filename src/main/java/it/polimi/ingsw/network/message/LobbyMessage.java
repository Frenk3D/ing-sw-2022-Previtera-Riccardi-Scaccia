package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.Lobby;

import java.util.List;

/**
 * This class implements the LobbyMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the available lobbies
 */
public class LobbyMessage extends Message {
    List<Lobby> lobbiesList;

    /**
     * Default constructor
     *
     * @param senderId    the id of the message sender
     * @param lobbiesList the list of available lobbies
     */
    public LobbyMessage(int senderId, List<Lobby> lobbiesList) {
        super(MessageType.AVAILABLE_LOBBIES, senderId, true);
        this.lobbiesList = lobbiesList;
    }

    /**
     * @return the list of available lobbies
     */
    public List<Lobby> getLobbiesList() {
        return lobbiesList;
    }
}
