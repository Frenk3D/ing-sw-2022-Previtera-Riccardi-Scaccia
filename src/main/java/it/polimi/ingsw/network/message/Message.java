package it.polimi.ingsw.network.message;
//custom messages to observe
public abstract class Message {
    private MessageType messageType;
    private int playerId;

    public Message(MessageType messageType, int playerId){
        this.messageType=messageType;
        this.playerId=playerId;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
