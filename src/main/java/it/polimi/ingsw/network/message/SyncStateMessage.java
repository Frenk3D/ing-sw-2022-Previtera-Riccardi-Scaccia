package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.SettingState;
import it.polimi.ingsw.model.enumerations.TurnState;

public class SyncStateMessage extends Message{
    private GameState gameState;
    private SettingState settingState;
    private TurnState turnState;
    private RoundState roundState;
    private int currPlayerId;

    //in game state
    public SyncStateMessage(int senderId, GameState gameState, RoundState roundState, TurnState turnState, int currPlayerId){
        super(MessageType.SYNC_STATE, senderId, false);
        this.turnState = turnState;
        this.roundState = roundState;
        this.currPlayerId = currPlayerId;
        this.gameState = gameState;
        this.settingState = SettingState.NOT_SETTING_STATE;
    }

    //setting state
    public SyncStateMessage(int senderId, GameState gameState, SettingState settingState){
        super(MessageType.SYNC_STATE,senderId,false);
        this.gameState = gameState;
        this.settingState = settingState;
        turnState = null;
        roundState = null;
        currPlayerId = -1;
    }

    public GameState getGameState() {
        return gameState;
    }

    public SettingState getSettingState() {
        return settingState;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public RoundState getRoundState() {
        return roundState;
    }

    public int getCurrPlayerId() {
        return currPlayerId;
    }
}
