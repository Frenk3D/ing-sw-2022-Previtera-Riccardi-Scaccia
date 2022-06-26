package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;

/**
 *  This class implements the UseCharacterMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 *  this message contains the character parameters
 */
public class UseCharacterMessage extends Message{
    private MessageCharacterParameters parameters;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param parameters the character's parameters
     */
    public UseCharacterMessage(int senderId, MessageCharacterParameters parameters){
        super(MessageType.USE_CHARACTER,senderId,false);
        this.parameters=parameters;
    }

    /**
     *
     * @return the character's parameters
     */
    public MessageCharacterParameters getCharacterParameters(){
        return parameters;
    }
}
