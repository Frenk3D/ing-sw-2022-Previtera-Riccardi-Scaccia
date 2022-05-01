package it.polimi.ingsw.network.message;

public class SelectAssistantMessage extends Message{

    int selectedAssistant;

    public SelectAssistantMessage(int senderId, int selectedAssistant) {
        super(MessageType.SELECT_ASSISTANT, senderId);
        this.selectedAssistant = selectedAssistant;
    }

    public int getSelectedAssistant() {
        return selectedAssistant;
    }
}
