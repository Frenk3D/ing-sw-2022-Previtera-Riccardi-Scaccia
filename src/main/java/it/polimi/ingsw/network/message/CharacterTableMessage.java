package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedCharacter;

import java.util.List;
import java.util.Map;

public class CharacterTableMessage extends Message{
    private int tableMoney;
    private List<ReducedCharacter> charactersList;
    private Map<Integer,Integer> numOfMoneyMap; //playerid - numOfMoney

    public CharacterTableMessage(int senderId,int tableMoney, List<ReducedCharacter> charactersList, Map<Integer,Integer> numOfMoneyMap){
        super(MessageType.CHARACTER_TABLE,senderId,false);
        this.tableMoney = tableMoney;
        this.charactersList = charactersList;
        this.numOfMoneyMap = numOfMoneyMap;
    }

    public int getTableMoney() {
        return tableMoney;
    }

    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    public Map<Integer, Integer> getNumOfMoneyMap() {
        return numOfMoneyMap;
    }
}
