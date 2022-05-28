package it.polimi.ingsw.network.client;

public enum ClientState {  //Specifies every state for the single client
    APPLICATION_START,
    LOGIN_ACCEPTED,
    PRE_LOBBY,
    WAITING_IN_LOBBY,
    GAME_START,
    CHOOSING_TEAM,
    CHOOSING_TOWER_COLOR,
    CHOOSING_WIZARD,
    GAME_FINISHED;
}
