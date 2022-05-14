package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.client.ReducedAssistant;

import java.util.List;

public class AssistantsSendMessage extends Message{
    private int destId;
    private List<ReducedAssistant> assistantsList;

    public AssistantsSendMessage(int senderId, int destId, List<ReducedAssistant> assistantsList){
        super(MessageType.ASSISTANTS_SEND,senderId,false);
        this.destId = destId;
        this.assistantsList = assistantsList;
    }

    public int getDestId() {
        return destId;
    }

    public List<ReducedAssistant> getAssistantsList() {
        return assistantsList;
    }
}
