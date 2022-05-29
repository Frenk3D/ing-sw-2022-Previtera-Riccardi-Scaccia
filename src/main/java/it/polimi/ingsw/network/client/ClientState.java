package it.polimi.ingsw.network.client;

public enum ClientState {  //Specifies every state for the single client
    APPLICATION_START, //WHEN CLIENT APP STARTS AND THE VIEW ASKS FOR SERVER INFO... OR THE CONTROLLER
    REQUESTING_LOGIN,
    LOGIN_REQUESTED,
    LOGIN_ACCEPTED,
    PRE_LOBBY,
    WAITING_IN_LOBBY, //while I'm waiting I will receive available... and I understand that I entered in a game.
    CHOOSING_TEAM,  // I'M CHOOSING (before sending message)
    CHOOSED_TEAM,   // I CHOSE AND IF I RECEIVE ERROR I RETRY AGAIN WITH CHOOSING TEAM
    CHOOSING_TOWER_COLOR,
    CHOOSED_TOWER_COLOR,
    CHOOSING_WIZARD,
    CHOOSED_WIZARD,
    GAME_START, //after preparation the game is starting

    GAME_FINISHED;
}
