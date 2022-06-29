package it.polimi.ingsw.network.message;

/**
 * This class implements the MoveStudentDashboardMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the index of the entrance list student to be moved in the hall
 */
public class MoveStudentDashboardMessage extends Message {
    private final int entranceListIndex;

    /**
     * Default constructor
     *
     * @param senderId          the id of the message sender
     * @param entranceListIndex the index of the entrance list student to be moved in the hall
     */
    public MoveStudentDashboardMessage(int senderId, int entranceListIndex) {
        super(MessageType.MOVE_STUDENT_DASHBOARD, senderId, false);
        this.entranceListIndex = entranceListIndex;
    }

    /**
     * @return the index of the entrance list student to be moved in the hall
     */
    public int getEntranceListIndex() {
        return entranceListIndex;
    }
}
