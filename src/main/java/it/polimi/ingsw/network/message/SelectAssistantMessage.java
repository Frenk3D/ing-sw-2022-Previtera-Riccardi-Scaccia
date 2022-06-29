package it.polimi.ingsw.network.message;

/**
 * This class implements the SelectAssistantMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the assistant selected by the sender
 */
public class SelectAssistantMessage extends Message {

    private final int selectedAssistant;

    /**
     * Default constructor
     *
     * @param senderId          the id of the message sender
     * @param selectedAssistant the assistant selected by the sender
     */
    public SelectAssistantMessage(int senderId, int selectedAssistant) {
        super(MessageType.SELECT_ASSISTANT, senderId, false);
        this.selectedAssistant = selectedAssistant;
    }

    /**
     * @return the selected assistant
     */
    public int getSelectedAssistant() {
        return selectedAssistant;
    }
}
