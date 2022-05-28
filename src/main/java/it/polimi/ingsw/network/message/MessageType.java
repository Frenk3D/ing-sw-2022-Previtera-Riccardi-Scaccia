package it.polimi.ingsw.network.message;

public enum MessageType {
    //-----------------INIT MESSAGES--------------------------
    LOGIN_REQUEST, LOGIN_REPLY, //string message, string message
    NEW_LOBBY_REQUEST, //new lobby message, string message || ---NEW GAME---
    LOBBIES_REQUEST, AVAILABLE_LOBBIES, //generic message, lobby message || request and get available lobby ---JOIN GAME---
    CHOOSE_LOBBY, PLAYER_JOIN, //string message, string message
    DISCONNECTION, //generic message

    //----------------IN-GAME MESSAGES--------------------------
    CHOOSE_TEAM, AVAILABLE_TEAM_SEND, // syncInitMessage
    CHOOSE_TOWER_COLOR, AVAILABLE_TOWER_SEND, // syncInitMessage
    CHOOSE_WIZARD, AVAILABLE_WIZARD_SEND, // syncInitMessage

    INIT_SEND, TABLE, THROWN_ASSISTANT,DASHBOARD, CHARACTER_TABLE, ASSISTANTS_SEND,
    SYNC_STATE,

    MOVE_STUDENT_ISLAND,MOVE_STUDENT_DASHBOARD,
    MOVE_MOTHER_NATURE,
    TAKE_FROM_CLOUD,
    SELECT_ASSISTANT,
    USE_CHARACTER,
    WIN,

    OK_REPLY, ERROR_REPLY, //generic message
    //OK_REPLY used to notify sync state for the specific client

}
