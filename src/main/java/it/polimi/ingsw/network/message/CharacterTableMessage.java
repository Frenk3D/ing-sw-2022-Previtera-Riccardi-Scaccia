package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedCharacter;

import java.util.List;
import java.util.Map;

/**
 * This class implements the CharacterTableMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * the character table message contains the table money,the reduced character's list and the map of player id and money
 */
public class CharacterTableMessage extends Message{
    private int tableMoney;
    private List<ReducedCharacter> charactersList;
    private Map<Integer,Integer> numOfMoneyMap; //playerid - numOfMoney

    /**
     * Default constructor
     * @param senderId the id of the sender
     * @param tableMoney the money on the table
     * @param charactersList the list of reduced characters
     * @param numOfMoneyMap the map of player id and money
     */
    public CharacterTableMessage(int senderId,int tableMoney, List<ReducedCharacter> charactersList, Map<Integer,Integer> numOfMoneyMap){
        super(MessageType.CHARACTER_TABLE,senderId,false);
        this.tableMoney = tableMoney;
        this.charactersList = charactersList;
        this.numOfMoneyMap = numOfMoneyMap;
    }

    /**
     *
     * @return the money on the table
     */
    public int getTableMoney() {
        return tableMoney;
    }

    /**
     *
     * @return the list of reduced characters
     */
    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    /**
     *
     * @return the map of player id and money
     */
    public Map<Integer, Integer> getNumOfMoneyMap() {
        return numOfMoneyMap;
    }
}
