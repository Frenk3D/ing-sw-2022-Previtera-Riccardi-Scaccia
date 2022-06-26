package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.*;

import java.util.List;

/**
 * This class implements the AllGameMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message is used in the class {@link it.polimi.ingsw.client.ClientGameModel} to obtain the needed game model informations
 */
public class AllGameMessage extends Message{
    private List<ReducedPlayer> playersList;
    private List<ReducedIsland> islandsList;
    private List<ReducedCloud> cloudsList;
    private List<ReducedAssistant> assistantsList;
    private int motherNaturePos;
    private int tableMoney;
    private List<ReducedCharacter> charactersList;
    private boolean expertMode;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param playersList the list of players of the game model
     * @param expertMode the game mode
     * @param islandsList the list of islands of the game model
     * @param cloudsList the list of clouds of the game model
     * @param assistantsList the list of assistants of the game model
     * @param motherNaturePos the position of mother nature
     * @param tableMoney the money on the table
     * @param charactersList the list of characters of the game model
     */
    public AllGameMessage(int senderId, List<ReducedPlayer> playersList, boolean expertMode, List<ReducedIsland> islandsList, List<ReducedCloud> cloudsList, List<ReducedAssistant> assistantsList, int motherNaturePos, int tableMoney, List<ReducedCharacter> charactersList){
        super(MessageType.INIT_SEND,senderId,false);
        this.playersList = playersList;
        this.islandsList = islandsList;
        this.cloudsList = cloudsList;
        this.assistantsList = assistantsList;
        this.motherNaturePos = motherNaturePos;
        this.tableMoney = tableMoney;
        this.charactersList = charactersList;
        this.expertMode = expertMode;
    }

    /**
     *
     * @return the list of reduced players
     */
    public List<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    /**
     *
     * @return the list of reduced islands
     */
    public List<ReducedIsland> getIslandsList() {
        return islandsList;
    }

    /**
     *
     * @return the list of reduced clouds
     */
    public List<ReducedCloud> getCloudsList() {
        return cloudsList;
    }

    /**
     *
     * @return the list of reduced assistants
     */
    public List<ReducedAssistant> getAssistantsList() {
        return assistantsList;
    }

    /**
     *
     * @return the position of mother nature
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
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
     * @return {@code true} if the game is in expert mode {@code false} if it is in normal mode
      */
    public boolean isExpertMode() {
        return expertMode;
    }
}
