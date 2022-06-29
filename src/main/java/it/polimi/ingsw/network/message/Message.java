package it.polimi.ingsw.network.message;

import java.io.Serializable;

/**
 * This is the generic class that implements {@link Serializable} and we can send through the network.
 */
//custom messages to observe
public abstract class Message implements Serializable {
    private static final long serialVersionUID = 4951303731052728724L;
    private final MessageType messageType;
    private final int senderId; //id of the client that sends the message
    private final boolean initMessage;

    /**
     * Default constructor
     *
     * @param messageType the type of the message
     * @param senderId    the id of the message sender
     * @param initMessage the initialization message
     */
    Message(MessageType messageType, int senderId, boolean initMessage) {
        this.messageType = messageType;
        this.senderId = senderId;
        this.initMessage = initMessage;
    }

    /**
     * @return the type of the message
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return the id of the message sender
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * @return {@code true} if it is an init message {@code false} if it is not
     */
    public boolean isInitMessage() {
        return initMessage;
    }
}
