package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.client.*;

import java.util.List;

public class AllGameMessage extends Message{
    private List<ReducedPlayer> playersList;
    private List<ReducedIsland> islandsList;
    private List<ReducedCloud> cloudsList;
    private List<ReducedAssistant> assistantsList;
    private int motherNaturePos;
    private int tableMoney;
    private List<ReducedCharacter> charactersList;
    private boolean expertMode;


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

    public List<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    public List<ReducedIsland> getIslandsList() {
        return islandsList;
    }

    public List<ReducedCloud> getCloudsList() {
        return cloudsList;
    }

    public List<ReducedAssistant> getAssistantsList() {
        return assistantsList;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    public int getTableMoney() {
        return tableMoney;
    }

    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    public boolean isExpertMode() {
        return expertMode;
    }
}
