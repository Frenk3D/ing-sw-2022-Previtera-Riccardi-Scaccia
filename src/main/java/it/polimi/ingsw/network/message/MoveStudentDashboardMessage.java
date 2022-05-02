package it.polimi.ingsw.network.message;

public class MoveStudentDashboardMessage extends Message{
    private int entranceListIndex;

    public MoveStudentDashboardMessage(int senderId, int entranceListIndex){
        super(MessageType.MOVE_STUDENT_DASHBOARD,senderId);
        this.entranceListIndex=entranceListIndex;
    }

    public int getEntranceListIndex() {
        return entranceListIndex;
    }
}
