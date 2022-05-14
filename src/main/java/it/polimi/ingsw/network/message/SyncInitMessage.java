package it.polimi.ingsw.network.message;

public class SyncInitMessage extends Message {

    public SyncInitMessage(MessageType type, int senderId){
        super(type,senderId,false);
    }

}
