package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.SettingState;
import it.polimi.ingsw.model.enumerations.TurnState;

/**
 * This class implements the SyncStateMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains the various possible game states
 */
public class SyncStateMessage extends Message {
    private final GameState gameState;
    private final SettingState settingState;
    private final RoundState roundState;
    private final TurnState turnState;
    private final int currPlayerId;

    //in game state

    /**
     * Default constructor for in game state
     * since we are in game the setting state is set to NOT_SETTING_STATE
     *
     * @param senderId     the id of the message sender
     * @param gameState    the game state
     * @param roundState   the round state
     * @param turnState    the turn state
     * @param currPlayerId the id of the current player
     */
    public SyncStateMessage(int senderId, GameState gameState, RoundState roundState, TurnState turnState, int currPlayerId) {
        super(MessageType.SYNC_STATE, senderId, false);
        this.turnState = turnState;
        this.roundState = roundState;
        this.currPlayerId = currPlayerId;
        this.gameState = gameState;
        this.settingState = SettingState.NOT_SETTING_STATE;
    }

    //setting state

    /**
     * Default constructor for setting state
     * in the setting state the current player id is set to -1 (does not exist),and turn state and round state are set to null
     *
     * @param senderId     the id of the message sender
     * @param gameState    the game state
     * @param settingState the setting state
     */
    public SyncStateMessage(int senderId, GameState gameState, SettingState settingState) {
        super(MessageType.SYNC_STATE, senderId, false);
        this.gameState = gameState;
        this.settingState = settingState;
        turnState = null;
        roundState = null;
        currPlayerId = -1;
    }

    /**
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return the setting state
     */
    public SettingState getSettingState() {
        return settingState;
    }

    /**
     * @return the turn state
     */
    public TurnState getTurnState() {
        return turnState;
    }

    /**
     * @return the round state
     */
    public RoundState getRoundState() {
        return roundState;
    }

    /**
     * @return the id of the current player
     */
    public int getCurrPlayerId() {
        return currPlayerId;
    }
}
