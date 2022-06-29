package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedCloud;
import it.polimi.ingsw.client.ReducedIsland;

import java.util.List;

/**
 * This class implements the TableMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains all the items that are on the table
 */
public class TableMessage extends Message {
    private final List<ReducedIsland> islandList;
    private final List<ReducedCloud> cloudList;
    private final int motherNaturePos;

    /**
     * Default constructor
     *
     * @param senderId        the id of the message sender
     * @param islandList      the list of reduced islands
     * @param cloudList       the list of reduced clouds
     * @param motherNaturePos the position of mother nature
     */
    public TableMessage(int senderId, List<ReducedIsland> islandList, List<ReducedCloud> cloudList, int motherNaturePos) {
        super(MessageType.TABLE, senderId, false);
        this.islandList = islandList;
        this.cloudList = cloudList;
        this.motherNaturePos = motherNaturePos;
    }

    /**
     * @return the list of reduced islands
     */
    public List<ReducedIsland> getIslandList() {
        return islandList;
    }

    /**
     * @return the list of reduced clouds
     */
    public List<ReducedCloud> getCloudList() {
        return cloudList;
    }

    /**
     * @return the position of mother nature
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
    }
}