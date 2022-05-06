package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class ClientGameModel {
    private List<Island> clientIslandList;
    private List<Assistant> clientAssistantList;
    private List<Cloud> clientCloudList;
    private List<Player> playersList;
    private int clientTableMoney;
    private int myPlayerId;

    public List<Island> getClientIslandList() {
        return clientIslandList;
    }

    public List<Assistant> getClientAssistantList() {
        return clientAssistantList;
    }

    public List<Cloud> getClientCloudList() {
        return clientCloudList;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public int getClientTableMoney() {
        return clientTableMoney;
    }

    public int getMyPlayerId() {
        return myPlayerId;
    }
}
