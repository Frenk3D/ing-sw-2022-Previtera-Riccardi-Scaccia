package it.polimi.ingsw.network.message;

public class StringMessage extends Message{
    private String string;

    public StringMessage(MessageType type, int senderId, boolean initMessage, String string){
        super(type,senderId,initMessage);
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
