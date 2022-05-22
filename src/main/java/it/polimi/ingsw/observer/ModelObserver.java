package it.polimi.ingsw.observer;

import java.util.Map;

//view is the model observer and the model is the model observable

/**
 * Custom observer interface for views. It supports different types of notification. From view to clientController and from clientModel to View (CLI OR GUI)
 */
public interface ModelObserver {

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onUpdateServerInfo(Map<String, String> serverInfo);

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    void onUpdateNickname(String nickname);

    /**
     * Sends a message to the server with the player number chosen by the user.
     *
     * @param playersNumber the number of players.
     */
    void onUpdatePlayersNumber(int playersNumber);

    /**
     * Handles a disconnection wanted by the user.
     */
    void onDisconnection();

}
