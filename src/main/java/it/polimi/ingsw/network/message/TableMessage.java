package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedCloud;
import it.polimi.ingsw.client.ReducedIsland;

import java.util.List;

public class TableMessage extends Message{
    private List<ReducedIsland> islandList;
    private List<ReducedCloud> cloudList;
    private int motherNaturePos;

    public TableMessage(int senderId, List<ReducedIsland> islandList, List<ReducedCloud> cloudList, int motherNaturePos){
        super(MessageType.TABLE,senderId,false);
        this.islandList = islandList;
        this.cloudList = cloudList;
        this.motherNaturePos = motherNaturePos;
    }

    public List<ReducedIsland> getIslandList() {
        return islandList;
    }

    public List<ReducedCloud> getCloudList() {
        return cloudList;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }
}