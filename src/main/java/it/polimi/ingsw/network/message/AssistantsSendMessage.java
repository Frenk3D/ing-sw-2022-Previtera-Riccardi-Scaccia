package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedAssistant;

import java.util.List;

/**
 * This class implements the AssistantsSendMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * it sends the list of assistants
 */
public class AssistantsSendMessage extends Message {
    private final List<ReducedAssistant> assistantsList;

    public AssistantsSendMessage(int senderId, List<ReducedAssistant> assistantsList) {
        super(MessageType.ASSISTANTS_SEND, senderId, false);
        this.assistantsList = assistantsList;
    }

    /**
     * @return the list of reduced assistants
     */
    public List<ReducedAssistant> getAssistantsList() {
        return assistantsList;
    }
}
