package it.polimi.ingsw.network.message;

//custom messages to observe
public abstract class Message {
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
