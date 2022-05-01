package it.polimi.ingsw.network.message;

public class MoveMotherNatureMessage extends Message{
    int islandIndex;

    public MoveMotherNatureMessage(int senderId, int islandIndex){
        super(MessageType.MOVE_MOTHER_NATURE,senderId);
        this.islandIndex = islandIndex;
    }

    public int getIslandIndex() {
        return islandIndex;
    }
}
