package it.polimi.ingsw.network.client;

/**
 * The various state of the {@link it.polimi.ingsw.controllers.ClientController}, so we can manage everything with it.
 */
public enum ClientState {  //Specifies every state for the single client
    APPLICATION_START, //WHEN CLIENT APP STARTS AND THE VIEW ASKS FOR SERVER INFO... OR THE CONTROLLER
    REQUESTING_LOGIN,
    LOGIN_ACCEPTED,
    CHOOSING_JOIN_CREATE,
    CREATING_LOBBY,
    CHOOSING_LOBBY, //we choose to join a lobby and we are choosing the lobby,
    CHOSEN_LOBBY,
    WAITING_IN_LOBBY, //while I'm waiting I will receive available... and I understand that I entered a game.
    CHOOSING_TEAM,  // I'M CHOOSING (before sending message)
    CHOSEN_TEAM,   // I CHOSE AND IF I RECEIVE ERROR I RETRY AGAIN WITH CHOOSING TEAM
    CHOOSING_TOWER_COLOR,
    CHOSEN_TOWER_COLOR,
    CHOOSING_WIZARD,
    CHOSEN_WIZARD,
    GAME_START, //after preparation the game is starting
    THROWING_ASSISTANT,
    SELECTED_ASSISTANT,
    CHOOSING_WHERE_TO_MOVE_STUDENT,
    MOVING_A_STUDENT_DASHBOARD,
    MOVING_A_STUDENT_ISLAND,
    MOVED_STUDENT,
    MOVING_MOTHER_NATURE,
    MOVED_MOTHER_NATURE,
    CHOOSING_CLOUD,
    CHOSEN_CLOUD,

    USING_CHARACTER,
    USED_CHARACTER,
    WAITING_FOR_YOUR_TURN,

    //WAITING_FOR_RESPONSE,
    GAME_FINISHED

    //NEVER CHECKED STATE...?
}
