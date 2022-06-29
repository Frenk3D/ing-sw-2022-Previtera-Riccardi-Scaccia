package it.polimi.ingsw.network.message;

/**
 * This class implements the MoveMotherNatureMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the index of the island where mother nature has to move
 */
public class MoveMotherNatureMessage extends Message {
    private final int islandIndex;

    /**
     * default constructor
     *
     * @param senderId    the id of the message sender
     * @param islandIndex the index of the island where mother nature has to move
     */
    public MoveMotherNatureMessage(int senderId, int islandIndex) {
        super(MessageType.MOVE_MOTHER_NATURE, senderId, false);
        this.islandIndex = islandIndex;
    }

    /**
     * @return the index of the island where mother nature has to move
     */
    public int getIslandIndex() {
        return islandIndex;
    }
}
