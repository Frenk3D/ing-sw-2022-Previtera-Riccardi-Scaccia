package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class ClientGameModel {
    private List<ReducedIsland> islandList;
    private List<ReducedAssistant> assistantList;
    private List<ReducedCloud> cloudList;
    private List<ReducedPlayer> playersList;
    private List<ReducedCharacter> charactersList;
    private int tableMoney;
    private int myPlayerId;
    private boolean expertMode;
    private int numOfPlayers;


    public void setIslandList(List<ReducedIsland> islandList) {
        this.islandList = islandList;
    }

    public void setAssistantList(List<ReducedAssistant> assistantList) {
        this.assistantList = assistantList;
    }

    public void setCloudList(List<ReducedCloud> cloudList) {
        this.cloudList = cloudList;
    }

    public void setPlayersList(List<ReducedPlayer> playersList) {
        this.playersList = playersList;
        numOfPlayers = playersList.size();
    }

    public void setTableMoney(int tableMoney) {
        this.tableMoney = tableMoney;
    }

    public void setMyPlayerId(int myPlayerId) {
        this.myPlayerId = myPlayerId;
    }

    public void setCharactersList(List<ReducedCharacter> charactersList) {
        this.charactersList = charactersList;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public List<ReducedIsland> getIslandList() {
        return islandList;
    }

    public List<ReducedAssistant> getAssistantList() {
        return assistantList;
    }

    public List<ReducedCloud> getCloudList() {
        return cloudList;
    }

    public List<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    public int getTableMoney() {
        return tableMoney;
    }

    public int getMyPlayerId() {
        return myPlayerId;
    }

    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    public boolean isExpertMode() {
        return expertMode;
    }
}


