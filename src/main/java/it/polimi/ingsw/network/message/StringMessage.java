package it.polimi.ingsw.network.message;

public class StringMessage extends Message{
    private String string;

    public StringMessage(MessageType type, int senderId, String string){
        super(type,senderId,true);
    }

    public String getString() {
        return string;
    }
}
