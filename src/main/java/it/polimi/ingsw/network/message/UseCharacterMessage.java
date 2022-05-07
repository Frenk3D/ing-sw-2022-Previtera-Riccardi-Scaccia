package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;

public class UseCharacterMessage extends Message{
    private MessageCharacterParameters parameters;

    public UseCharacterMessage(int senderId, CharacterParameters parameters){
        super(MessageType.USE_CHARACTER,senderId,false);
        this.parameters = new MessageCharacterParameters();
        this.parameters.setSelectedColor(parameters.getSelectedColor());
        this.parameters.setStudentIndex(parameters.getStudentIndex());
        this.parameters.setSelectedColor2(parameters.getSelectedColor2());
        this.parameters.setStudentsIndexList(parameters.getStudentsIndexList());
        this.parameters.setStudentsIndexEntranceList(parameters.getStudentsIndexEntranceList());
        this.parameters.setIslandIndex(parameters.getIslandsList(), parameters.getIsland());
        this.parameters.setCharacterIndex(parameters.getCharactersList(), parameters.getUsedCharacter());
    }


    public MessageCharacterParameters getCharacterParameters(){
        return parameters;
    }
}
