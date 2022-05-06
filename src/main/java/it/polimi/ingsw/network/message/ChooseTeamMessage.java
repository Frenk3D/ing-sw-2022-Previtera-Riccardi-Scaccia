package it.polimi.ingsw.network.message;

public class ChooseTeamMessage extends Message{
    int playerId;
    int requestedPlayerId;

    public ChooseTeamMessage(int senderId, int playerId, int requestedPlayerId){
        super(MessageType.CHOOSE_TEAM,senderId,false);
        this.playerId = playerId;
        this.requestedPlayerId = requestedPlayerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRequestedPlayerId() {
        return requestedPlayerId;
    }
}