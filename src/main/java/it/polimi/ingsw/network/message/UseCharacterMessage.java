package it.polimi.ingsw.network.message;

public class UseCharacterMessage extends Message{

    public UseCharacterMessage(int senderId){
        super(MessageType.USE_CHARACTER,senderId);
    }
}