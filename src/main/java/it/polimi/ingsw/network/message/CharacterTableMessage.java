package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.client.ReducedCharacter;

import java.util.List;

public class CharacterTableMessage extends Message{
    private int tableMoney;
    private List<ReducedCharacter> charactersList;

    public CharacterTableMessage(int senderId,int tableMoney, List<ReducedCharacter> charactersList){
        super(MessageType.CHARACTER_TABLE,senderId,false);
        this.tableMoney = tableMoney;
        this.charactersList = charactersList;
    }

    public int getTableMoney() {
        return tableMoney;
    }

    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }
}
