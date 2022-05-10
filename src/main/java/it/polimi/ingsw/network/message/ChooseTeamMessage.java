package it.polimi.ingsw.network.message;

public class ChooseTeamMessage extends Message{
    int requestedPlayerId;

    public ChooseTeamMessage(int senderId, int requestedPlayerId){
        super(MessageType.CHOOSE_TEAM,senderId,false);
        this.requestedPlayerId = requestedPlayerId;
    }


    public int getRequestedPlayerId() {
        return requestedPlayerId;
    }
}