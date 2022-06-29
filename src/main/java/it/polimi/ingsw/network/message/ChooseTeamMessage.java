package it.polimi.ingsw.network.message;

/**
 * This class implements the ChooseTeamMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message handles the team selection of a player
 */
public class ChooseTeamMessage extends Message {
    int requestedPlayerId;

    /**
     * Default constructor
     *
     * @param senderId          the id of the requesting team player
     * @param requestedPlayerId the id of the requested team player
     */
    public ChooseTeamMessage(int senderId, int requestedPlayerId) {
        super(MessageType.CHOOSE_TEAM, senderId, false);
        this.requestedPlayerId = requestedPlayerId;
    }

    /**
     * @return the requested player's id
     */
    public int getRequestedPlayerId() {
        return requestedPlayerId;
    }
}