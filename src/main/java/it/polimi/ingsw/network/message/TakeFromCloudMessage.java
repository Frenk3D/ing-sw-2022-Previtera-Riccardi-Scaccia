package it.polimi.ingsw.network.message;

public class TakeFromCloudMessage extends Message{
    int cloudIndex;

    public TakeFromCloudMessage(int senderId, int cloudIndex){
        super(MessageType.TAKE_FROM_CLOUD,senderId,false);
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex() {
        return cloudIndex;
    }
}
