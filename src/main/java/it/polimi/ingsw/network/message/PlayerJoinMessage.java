package it.polimi.ingsw.network.message;

import java.util.List;

/**
 * This class implements the PlayerJoinMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the list of players of a lobby
 */
public class PlayerJoinMessage extends Message{
    List<String> playersList;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param playersList the list of players
     */
    public PlayerJoinMessage(int senderId, List<String> playersList){
        super(MessageType.PLAYER_JOIN,senderId,true);
        this.playersList = playersList;
    }

    /**
     *
     * @return the list of players
     */
    public List<String> getPlayersList() {
        return playersList;
    }
}
