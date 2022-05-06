package it.polimi.ingsw.network.message;

public class MoveStudentIslandMessage extends Message{

    private int entranceListIndex;
    private int islandIndex;

    public MoveStudentIslandMessage(int senderId, int entranceListIndex,int islandIndex) {
        super(MessageType.MOVE_STUDENT_ISLAND, senderId,false);
        this.entranceListIndex = entranceListIndex;
        this.islandIndex = islandIndex;

    }

    public int getEntranceListIndex() {
        return entranceListIndex;
    }

    public int getIslandIndex() {
        return islandIndex;
    }
}
