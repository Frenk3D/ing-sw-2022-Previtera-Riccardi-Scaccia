package it.polimi.ingsw.network.message;

import java.io.Serializable;

//custom messages to observe
public abstract class Message implements Serializable {
    private static final long serialVersionUID = 4951303731052728724L;
    private MessageType messageType;
    private int senderId; //id of the client that sends the message
    private boolean initMessage;

    Message(MessageType messageType, int senderId, boolean initMessage){
        this.messageType=messageType;
        this.senderId=senderId;
        this.initMessage = initMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getSenderId(){
        return senderId;
    }

    public boolean isInitMessage() {
        return initMessage;
    }
}
