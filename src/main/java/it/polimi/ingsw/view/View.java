package it.polimi.ingsw.view;



import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.network.server.SocketClientManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//VIEW FOR CLIENT if a client has to send/write something is an ask  (from the controller, params are the things where i have to choose) or it's a show method (like the sync message or else automatic, params are the things to show)

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI, ...).
 */
public abstract class View extends ViewObservable implements Observer {
    private ClientSocket clientSocket;

    public View(ClientSocket clientSocket){
        this.clientSocket=clientSocket;
    }
    /**
     * Asks the user to write a Nickname.
     */
    public abstract void askPlayerInfo();

    public abstract void askServerConfig();

    public abstract void askNewOrJoinGame();

    public abstract void showAvailableLobbies(List<Lobby> lobbiesList);

    public abstract void askRequestedLobby(List<Lobby> lobbiesList);

    public abstract void askTeam(List<Player> playersList);

    public abstract void showAvailableTeams(Map<String,Integer> players);

    public abstract void askTowerColor();

    public abstract void showAvailableTowerColors(List<TowerColor> availableTowerColors);

    public abstract void askWizard();

    public abstract void showAvailableWizards(List<Wizard> availableWizards);

    public abstract void showTable(List<Island> islandsList, List<Cloud> cloudsList, int motherNaturePos);

    public abstract void showThrownAssistant(Assistant thrownAssistant,int playerId);

    public abstract void showDashboard(Dashboard dashboard,int playerId);

    public abstract void showCharacterTable(List<Character> charactersList, AtomicInteger tableMoney, Map<Player,AtomicInteger> numOfMoneyMap);

    public abstract void showAssistantsList(List<Assistant> assistantsList);

    public abstract void showThrownAssistant(Assistant selectedAssistant);

    public abstract void askStudentToMoveIsland(List<Island> entranceStudentsList,List<Island> islandsList);

    public abstract void askStudentToMoveDashboard(List<Student> entranceStudentsList);

    public abstract void askMotherNatureMove(List<Island> islandsList);

    public abstract void askCloudExtraction(List<Cloud> cloudsList);

    public abstract void askSelectedAssistant(List<Assistant> assistantsList);

    public abstract void askUsedCharacter(List<Character> charactersList);


    /**
     * Shows a disconnection message.
     *
     * @param text                 a generic info text message.
     */
    public abstract void showDisconnectionMessage(String text);

    /**
     * Shows an error message.
     *
     * @param error the error message to be shown.
     */
    public abstract void showErrorAndExit(String error);


    public abstract void showMatchInfo(List<Player> playersList, List<Island> islandsList, List<Cloud> cloudsList, List<Assistant> assistantsList, int motherNaturePos, AtomicInteger tableMoney, List<Character> charactersList, boolean expertMode);

    /**
     * Shows a win message.
     *
     * @param winner the nickname of the winner.
     */
    public abstract void showWinMessage(String winner);


    @Override
    public void update(Message message) {

    }
}
