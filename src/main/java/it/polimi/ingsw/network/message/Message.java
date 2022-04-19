package it.polimi.ingsw.network.message;
//custom messages to observe
public abstract class Message {
    private MessageType messageType;
    private int senderId;

    private Message(MessageType messageType, int senderId){
        this.messageType=messageType;
        this.senderId=senderId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getSenderId(){
        return senderId;
    }
}
