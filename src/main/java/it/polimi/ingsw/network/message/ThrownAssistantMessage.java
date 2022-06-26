package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedAssistant;

/**
 *  This class implements the ThrownAssistantMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 *  this message contains the thrown assistant
 */
public class ThrownAssistantMessage extends Message{
    private ReducedAssistant assistant;
    private int playerId; //the player that throws the assistant

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param assistant the thrown assistant
     * @param playerId the id of the player who threw the assistant
     */
    public ThrownAssistantMessage(int senderId, ReducedAssistant assistant, int playerId){
        super(MessageType.THROWN_ASSISTANT, senderId,false);
        this.assistant = assistant;
        this.playerId = playerId;
    }

    /**
     *
     * @return the thown assistant
     */
    public ReducedAssistant getAssistant() {
        return assistant;
    }

    /**
     *
     * @return the id of the player who threw the assistant
      */
    public int getPlayerId() {
        return playerId;
    }
}
