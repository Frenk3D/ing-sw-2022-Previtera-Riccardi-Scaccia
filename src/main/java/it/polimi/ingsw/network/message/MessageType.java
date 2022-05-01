package it.polimi.ingsw.network.message;

public enum MessageType {
    LOGIN_REQUEST,
    ADD_PLAYER_REQUEST,
    NEW_LOBBY_REQUEST, DELETE_LOBBY, //new lobby request creates a controller
    LOBBY_REQUEST, LOBBY_REPLY, //request and get available lobby
    CHOOSE_LOBBY,
    PLAYER_SEND,

    CHOOSE_TEAM, TEAM_SEND,
    CHOOSE_TOWER_COLOR,TOWER_SEND,
    CHOOSE_WIZARD,WIZARD_SEND,

    GAME_SEND,
    MOVE_STUDENT_ISLAND,MOVE_STUDENT_DASHBOARD,
    MOVE_MOTHER_NATURE,
    TAKE_FROM_CLOUD,
    SELECT_ASSISTANT,
    USE_CHARACTER,

    WIN,
    LOSE,

    // CHAT_TEXT, //for 4 players, a tutor says that we can say to use an external app to chatting
    OK_REPLY,
    ERROR_REPLY
}
