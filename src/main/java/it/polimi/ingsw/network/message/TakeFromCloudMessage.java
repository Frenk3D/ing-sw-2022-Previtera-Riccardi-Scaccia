package it.polimi.ingsw.network.message;

/**
 *  This class implements the SyncInitMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 *  this message contains the index of the chosen cloud
 */
public class TakeFromCloudMessage extends Message{
    int cloudIndex;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param cloudIndex the index of the chosen cloud
     */
    public TakeFromCloudMessage(int senderId, int cloudIndex){
        super(MessageType.TAKE_FROM_CLOUD,senderId,false);
        this.cloudIndex = cloudIndex;
    }

    /**
     *
     * @return the index of the chosen cloud
     */
    public int getCloudIndex() {
        return cloudIndex;
    }
}
