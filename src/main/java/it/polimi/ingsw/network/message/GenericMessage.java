package it.polimi.ingsw.network.message;

public class GenericMessage extends Message{
    public GenericMessage(MessageType type, int senderId, boolean initMessage){
        super(type,senderId,initMessage);
    }
}
