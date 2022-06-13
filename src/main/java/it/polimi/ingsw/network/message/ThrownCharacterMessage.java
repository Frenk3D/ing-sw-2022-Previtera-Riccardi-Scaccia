package it.polimi.ingsw.network.message;

public class ThrownCharacterMessage extends Message{
    private int playerId;
    private int characterId;

    public ThrownCharacterMessage(int senderId, int characterId, int playerId){
        super(MessageType.THROWN_CHARACTER, senderId,false);
        this.characterId = characterId;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getCharacterId() {
        return characterId;
    }
}
