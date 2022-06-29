package it.polimi.ingsw.network.message;

/**
 * This class implements a GenericMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 */
public class GenericMessage extends Message {
    public GenericMessage(MessageType type, int senderId, boolean initMessage) {
        super(type, senderId, initMessage);
    }
}
