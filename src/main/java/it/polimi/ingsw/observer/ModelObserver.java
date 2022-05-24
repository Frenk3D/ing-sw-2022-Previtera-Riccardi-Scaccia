package it.polimi.ingsw.observer;

//view (CLI OR GUI) is the model observer and the model is the model observable
/*(it has more prototypes of ViewObserver but with fewer parameters)*/

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

    void onAskCreateOrJoin();

   /* void onSendNewLobbyRequest();

    void onSendLobbiesRequest();
*/

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
//    void onAskDisconnection();
//

    void onShow(Object toShow);
}
