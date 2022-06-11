package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;

public class UseCharacterMessage extends Message{
    private MessageCharacterParameters parameters;

    public UseCharacterMessage(int senderId, MessageCharacterParameters parameters){
        super(MessageType.USE_CHARACTER,senderId,false);
        this.parameters=parameters;
    }


    public MessageCharacterParameters getCharacterParameters(){
        return parameters;
    }
}
