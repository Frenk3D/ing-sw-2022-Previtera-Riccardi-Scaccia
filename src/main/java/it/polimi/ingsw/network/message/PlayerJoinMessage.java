package it.polimi.ingsw.network.message;

import java.util.List;

public class PlayerJoinMessage extends Message{
    List<String> playersList;

    public PlayerJoinMessage(int senderId, List<String> playersList){
        super(MessageType.PLAYER_JOIN,senderId,true);
        this.playersList = playersList;
    }

    public List<String> getPlayersList() {
        return playersList;
    }
}
