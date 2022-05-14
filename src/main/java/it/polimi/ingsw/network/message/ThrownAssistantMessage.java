package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.client.ReducedAssistant;

public class ThrownAssistantMessage extends Message{
    private ReducedAssistant assistant;
    private int playerId; //the player that throws the assistant

    public ThrownAssistantMessage(int senderId, ReducedAssistant assistant, int playerId){
        super(MessageType.THROWN_ASSISTANT, senderId,false);
        this.assistant = assistant;
        this.playerId = playerId;
    }

    public ReducedAssistant getAssistant() {
        return assistant;
    }

    public int getPlayerId() {
        return playerId;
    }
}
