package it.polimi.ingsw.observer;

//view (CLI OR GUI) is the model observer and the model is the model observable, all methods are public
/*(it has more prototypes of ViewObserver but with fewer parameters)*/

import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Map;
//import java.util.concurrent.ExecutionException;

/**
 * Custom observer interface for views. It supports different types of notification. ). {@link View}  extends this.
 */
public interface ModelObserver {

    void onAskServerInfo() ;
    /**
     * Notify to the view the login request and asks for a nickname
     *
     */
    void onSendLoginRequest();

    /**
     * Notify the view to ask for create or join lobby
     *
     */
    void onAskCreateOrJoin();

    void onSendChooseLobby(List<Lobby> lobbyList);

   /* void onSendNewLobbyRequest();

    void onSendLobbiesRequest();
*/
    void onSendChooseTeam(Map<String,Integer> availablePlayers);

    void onSendChooseTowerColor(List<TowerColor> availableTowerColors);

    void onSendChooseWizard(List<Wizard> availableWizards);

    void onSendSelectAssistant();

    void onAskWhereToMoveStudent();

    void onSendMoveMotherNature();

    void onSendChooseCloud();

    void onAskCharacterParameters(int characterId);




//    /**
//     * Handles a disconnection wanted by the user.
//     */
//    void onAskDisconnection();
//

    void onShow(Object toShow);

    void onShowPlayerJoin(List<String> playersList);

    void updateClientGameModel(ClientGameModel clientGameModel);

    void onShowGame(ClientGameModel clientGameModel);

    void onShowChosenTeam(String toShow);

}
