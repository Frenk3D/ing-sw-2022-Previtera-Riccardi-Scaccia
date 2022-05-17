package it.polimi.ingsw.view;



import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.server.Lobby;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//if a client has to send/write something is an ask  (from the controller, params are the things where i have to choose) or it's a show method (like the sync message or else automatic, params are the things to show)

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI, ...).
 */
public interface View {
    /**
     * Asks the user to write a Nickname.
     */
    void askPlayerInfo();

    void askServerConfig();

    void askNewOrJoinGame();

    void showAvailableLobbies(List<Lobby> lobbiesList);

    void askRequestedLobby(List<Lobby> lobbiesList);

    void askTeam(List<Player> playersList);

    void showAvailableTeams(Map<String,Integer> players);

    void askTowerColor();

    void showAvailableTowerColors(List<TowerColor> availableTowerColors);

    void askWizard();

    void showAvailableWizards(List<Wizard> availableWizards);

    void showTable(List<Island> islandsList, List<Cloud> cloudsList, int motherNaturePos);

    void showThrownAssistant(Assistant thrownAssistant,int playerId);

    void showDashboard(Dashboard dashboard,int playerId);

    void showCharacterTable(List<Character> charactersList, AtomicInteger tableMoney, Map<Player,AtomicInteger> numOfMoneyMap);

    void showAssistantsList(List<Assistant> assistantsList);

    void showThrownAssistant(Assistant selectedAssistant);

    void askStudentToMoveIsland(List<Island> entranceStudentsList,List<Island> islandsList);

    void askStudentToMoveDashboard(List<Student> entranceStudentsList);

    void askMotherNatureMove(List<Island> islandsList);

    void askCloudExtraction(List<Cloud> cloudsList);

    void askSelectedAssistant(List<Assistant> assistantsList);

    void askUsedCharacter(List<Character> charactersList);


    /**
     * Shows a disconnection message.
     *
     * @param text                 a generic info text message.
     */
    void showDisconnectionMessage(String text);

    /**
     * Shows an error message.
     *
     * @param error the error message to be shown.
     */
    void showErrorAndExit(String error);


    void showMatchInfo(List<Player> playersList, List<Island> islandsList, List<Cloud> cloudsList, List<Assistant> assistantsList, int motherNaturePos, AtomicInteger tableMoney, List<Character> charactersList, boolean expertMode);

    /**
     * Shows a win message.
     *
     * @param winner the nickname of the winner.
     */
    void showWinMessage(String winner);

}
