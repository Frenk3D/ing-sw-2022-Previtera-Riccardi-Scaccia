package it.polimi.ingsw.network.message;

/**
 * This class implements the ThrownCharacterMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the thrown character
 */
public class ThrownCharacterMessage extends Message {
    private final int playerId;
    private final int characterId;

    /**
     * Default constructor
     *
     * @param senderId    the id of the message sender
     * @param characterId the id of the thrown character
     * @param playerId    the id of the player who threw the character
     */
    public ThrownCharacterMessage(int senderId, int characterId, int playerId) {
        super(MessageType.THROWN_CHARACTER, senderId, false);
        this.characterId = characterId;
        this.playerId = playerId;
    }

    /**
     * @return the id of the player
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return the id of the thrown character
     */
    public int getCharacterId() {
        return characterId;
    }
}
