package it.polimi.ingsw.observer;

//view (CLI OR GUI) is the model observer and the model is the model observable
/*(it has same prototypes of ViewObserver but not parameters)*/

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Custom observer interface for views. It supports different types of notification. )
 */
public interface ModelObserver {

    void onAskServerInfo() ;
    /**
     * Notify to the view the login request and asks for a nickname
     *
     */
    void onSendLoginRequest();

//    /**
//     * Notify the observer of the NewLobbyRequest
//     *
//     */
//    void onNewLobbyRequest();
//
//    /**
//     * Sends a message to the server with the player number chosen by the user.
//     *
//     * @param playersNumber the number of players.
//     */
//    void onUpdatePlayersNumber(int playersNumber);
//
//    /**
//     * Handles a disconnection wanted by the user.
//     */
//    void onDisconnection();
//

}
