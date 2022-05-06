package it.polimi.ingsw.network.message;

public enum MessageType {

    //-----------------INITMESSAGES--------------------------
    LOGIN_REQUEST, LOGIN_REPLY,
    NEW_LOBBY_REQUEST, DELETE_LOBBY, //new lobby request creates a controller ---NEW GAME---
    LOBBY_REQUEST, LOBBY_REPLY, //request and get available lobby ---JOIN GAME---
    CHOOSE_LOBBY, PLAYER_JOIN,

    //----------------INGAMEMESSAGES--------------------------
    CHOOSE_TEAM, TEAM_SEND, //classes initMessageRequest e initMessageReply
    CHOOSE_TOWER_COLOR,TOWER_SEND,
    CHOOSE_WIZARD,WIZARD_SEND,

    INIT_SEND, TABLE, THROWN_ASSISTANT,DASHBOARD, CHARACTER_TABLE, ASSISTANTS_SEND,
    SYNC_STATE,
    MOVE_STUDENT_ISLAND,MOVE_STUDENT_DASHBOARD,
    MOVE_MOTHER_NATURE,
    TAKE_FROM_CLOUD,
    SELECT_ASSISTANT,
    USE_CHARACTER,
    WIN, LOSE,

    OK_REPLY, ERROR_REPLY


    // CHAT_TEXT, //for 4 players, a tutor says that we can say to use an external app to chatting
}
