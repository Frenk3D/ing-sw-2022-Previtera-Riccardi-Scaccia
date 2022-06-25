package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedAssistant;

import java.util.List;

public class AssistantsSendMessage extends Message{
    private List<ReducedAssistant> assistantsList;

    public AssistantsSendMessage(int senderId, List<ReducedAssistant> assistantsList){
        super(MessageType.ASSISTANTS_SEND,senderId,false);
        this.assistantsList = assistantsList;
    }

    public List<ReducedAssistant> getAssistantsList() {
        return assistantsList;
    }
}
