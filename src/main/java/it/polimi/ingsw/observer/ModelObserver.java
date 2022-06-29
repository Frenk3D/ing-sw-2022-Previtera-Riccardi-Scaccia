package it.polimi.ingsw.observer;

//view (CLI OR GUI) is the model observer and the model is the model observable, all methods are public
/*(it has more prototypes of ViewObserver but with fewer parameters)*/

import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Map;
//import java.util.concurrent.ExecutionException;

/**
 * Custom observer interface for views. It supports different types of notification. ). {@link View}  extends this.
 */
public interface ModelObserver {

    void onAskServerInfo();

    /**
     * Notify to the view the login request and asks for a nickname
     */
    void onSendLoginRequest();

    /**
     * Notify the view to ask for create or join lobby
     */
    void onAskCreateOrJoin();

    /**
     * Notify the view to choose a lobby from the lobby list
     *
     * @param lobbyList the lobby list
     */
    void onSendChooseLobby(List<Lobby> lobbyList);

   /* void onSendNewLobbyRequest();

    void onSendLobbiesRequest();
*/

    /**
     * Notify the view to choose the team player from the available players map
     *
     * @param availablePlayers the available players map
     */
    void onSendChooseTeam(Map<String, Integer> availablePlayers);

    /**
     * Notify the view to choose the tower color from the available tower colors
     *
     * @param availableTowerColors the available tower colors
     */
    void onSendChooseTowerColor(List<TowerColor> availableTowerColors);

    /**
     * Notify the view to choose the wizard from the available wizards
     *
     * @param availableWizards the available wizards
     */
    void onSendChooseWizard(List<Wizard> availableWizards);

    /**
     * Notify the view to select an Assistant from the available assistant
     */
    void onSendSelectAssistant();

    /**
     * Notify the view to ask where to move the student (island or dashboard)
     */
    void onAskWhereToMoveStudent();

    /**
     * Notify the view to choose mother nature's position
     */
    void onSendMoveMotherNature();

    /**
     * Notify the view to choose a cloud from the available clouds
     */
    void onSendChooseCloud();

    /**
     * Notify the view to ask character parameters to the player
     *
     * @param characterId the id of the character
     */
    void onAskCharacterParameters(int characterId);


//    /**
//     * Handles a disconnection wanted by the user.
//     */
//    void onAskDisconnection();
//

    /**
     * Shows a certain object
     *
     * @param toShow the object to show
     */
    void onShow(Object toShow);

    /**
     * Show the modified player list when a player joins
     *
     * @param playersList the players list
     */
    void onShowPlayerJoin(List<String> playersList);

    /**
     * Updates client game model info
     *
     * @param clientGameModel the client game model
     */
    void updateClientGameModel(ClientGameModel clientGameModel);

    /**
     * Shows the client game model info
     *
     * @param clientGameModel the client game model
     */
    void onShowGame(ClientGameModel clientGameModel);

    /**
     * Shows a chosen team warning
     *
     * @param toShow the chosen team warning
     */
    void onShowChosenTeam(String toShow);

}
