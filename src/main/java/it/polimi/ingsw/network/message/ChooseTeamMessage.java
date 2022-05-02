package it.polimi.ingsw.network.message;

public class ChooseTeamMessage extends Message{
    int playerId;
    int requestedPlayerId;

    public ChooseTeamMessage(int senderId, int playerId, int requestedPlayerId){
        super(MessageType.CHOOSE_TEAM,senderId);
        this.playerId = playerId;
        this.requestedPlayerId = requestedPlayerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRequestedPlayerId() {
        return requestedPlayerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setRequestedPlayerId(int requestedPlayerId) {
        this.requestedPlayerId = requestedPlayerId;
    }
}