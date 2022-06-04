package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//controller is the view observer and the view is the view observable
/*(it has fewer prototypes of ModelObserver but with more parameters to say to the controller witch message send)*/

/**
 * Custom observer interface for views. It supports different types of notification. From view to clientController and from clientModel to View (CLI OR GUI)
 */
public interface ViewObserver {

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onAskServerInfo(Map<String, String> serverInfo) ;

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    void onSendLoginRequest(String nickname);

//    void onAskCreateOrJoin(String input);

    void onSendNewLobbyRequest(String input,int numOfPlayers,boolean expertMode);

    void onSendLobbiesRequest();

    void onSendChooseLobby(String chosenLobby);

    void onSendChooseTeam(int chosenTeamPlayerId);

    void onSendChooseTowerColor(TowerColor color);

    void onSendChooseWizard(Wizard wizard);

    void onSendSelectAssistant(int selectedAssistantId);


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
//    void onAskDisconnection(); from the view we can choose to close the game ?

}
