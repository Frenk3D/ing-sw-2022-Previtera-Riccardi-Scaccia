package it.polimi.ingsw.network.message;

/**
 * This class implements the MoveStudentIslandMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the index of the entrance list student to be moved,and the index of the destination island
 */
public class MoveStudentIslandMessage extends Message{

    private int entranceListIndex;
    private int islandIndex;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param entranceListIndex the index of the entrance list to be moved
     * @param islandIndex the index of the destination island
     */
    public MoveStudentIslandMessage(int senderId, int entranceListIndex,int islandIndex) {
        super(MessageType.MOVE_STUDENT_ISLAND, senderId,false);
        this.entranceListIndex = entranceListIndex;
        this.islandIndex = islandIndex;

    }

    /**
     *
     * @return the index of the entrance list student to be moved
     */
    public int getEntranceListIndex() {
        return entranceListIndex;
    }

    /**
     *
     * @return the index of the destination island
     */
    public int getIslandIndex() {
        return islandIndex;
    }
}
