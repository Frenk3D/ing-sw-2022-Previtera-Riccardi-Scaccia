package it.polimi.ingsw.network.message;

public class SelectAssistantMessage extends Message{

    private int selectedAssistant;

    public SelectAssistantMessage(int senderId, int selectedAssistant) {
        super(MessageType.SELECT_ASSISTANT, senderId,false);
        this.selectedAssistant = selectedAssistant;
    }

    public int getSelectedAssistant() {
        return selectedAssistant;
    }
}
