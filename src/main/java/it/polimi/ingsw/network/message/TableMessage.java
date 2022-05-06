package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;

import java.util.List;

public class TableMessage extends Message{
    List<Island> islandList;
    List<Cloud> cloudList;
    int motherNaturePos;

    public TableMessage(int senderId, List<Island> islandList, List<Cloud> cloudList, int motherNaturePos){
        super(MessageType.TABLE,senderId,false);
        this.islandList = islandList;
        this.cloudList = cloudList;
        this.motherNaturePos = motherNaturePos;
    }

    public List<Island> getIslandList() {
        return islandList;
    }

    public List<Cloud> getCloudList() {
        return cloudList;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }
}
