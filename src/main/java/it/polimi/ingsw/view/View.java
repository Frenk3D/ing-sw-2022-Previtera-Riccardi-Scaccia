package it.polimi.ingsw.view;



import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.client.ReducedCharacter;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.*;
import it.polimi.ingsw.observer.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
//All the checks are in the specific views, not in controller or in game model, because we WANT to have same prototypes for CLI (with prints etc.) and GUI (with shows)
//VIEW FOR CLIENT if a client has to send/write something is an ask  (from the controller, params are the things where I have to choose) or it's a show method (like the sync message or else automatic, params are the things to show)

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI, ...).
 */
public abstract class View extends ViewObservable implements ModelObserver {

    /**
     * Asks the user to write a Nickname.
     */
    @Override
    public abstract void onSendLoginRequest ();

    @Override
    public abstract void onAskServerInfo();

    @Override
    public abstract void onAskCreateOrJoin();

    @Override
    public abstract void onSendChooseLobby(List<Lobby> lobbyList);

    /*@Override
    public abstract void onSendNewLobbyRequest();

    @Override
    public abstract void onSendLobbiesRequest();*/
    @Override
    public abstract void onSendChooseTeam(Map<String,Integer> availablePlayers);

    @Override
    public abstract void onSendChooseTowerColor(List<TowerColor> availableTowerColors);

    @Override
    public abstract void onSendChooseWizard(List<Wizard> availableWizards);



//
//    public abstract void showAvailableLobbies(List<Lobby> lobbiesList);
//
//    public abstract void askRequestedLobby(List<Lobby> lobbiesList);
//
//    public abstract void askTeam(List<Player> playersList);
//
//    public abstract void showAvailableTeams(Map<String,Integer> players);
//
//    public abstract void askTowerColor();
//
//    public abstract void showAvailableTowerColors(List<TowerColor> availableTowerColors);
//
//    public abstract void askWizard();
//
//    public abstract void showAvailableWizards(List<Wizard> availableWizards);
//
//    public abstract void showTable(List<Island> islandsList, List<Cloud> cloudsList, int motherNaturePos);
//
//    public abstract void showThrownAssistant(Assistant thrownAssistant,int playerId);
//
//    public abstract void showDashboard(Dashboard dashboard,int playerId);
//
//    public abstract void showCharacterTable(List<Character> charactersList, AtomicInteger tableMoney, Map<Player,AtomicInteger> numOfMoneyMap);
//
//    public abstract void showAssistantsList(List<Assistant> assistantsList);
//
//    public abstract void showThrownAssistant(Assistant selectedAssistant);
//
//    public abstract void askStudentToMoveIsland(List<Island> entranceStudentsList,List<Island> islandsList);
//
//    public abstract void askStudentToMoveDashboard(List<Student> entranceStudentsList);
//
//    public abstract void askMotherNatureMove(List<Island> islandsList);
//
//    public abstract void askCloudExtraction(List<Cloud> cloudsList);
//
//    public abstract void askSelectedAssistant(List<Assistant> assistantsList);
//
//    public abstract void askUsedCharacter(List<Character> charactersList);
//
//
//    /**
//     * Shows a disconnection message.
//     *
//     * @param text                 a generic info text message.
//     */
//    public abstract void showDisconnectionMessage(String text);
//
//    /**
//     * Shows an error message.
//     *
//     * @param error the error message to be shown.
//     */
//    public abstract void showErrorAndExit(String error);
//
//
//    public abstract void showMatchInfo(List<Player> playersList, List<Island> islandsList, List<Cloud> cloudsList, List<Assistant> assistantsList, int motherNaturePos, AtomicInteger tableMoney, List<Character> charactersList, boolean expertMode);
//
//    /**
//     * Shows a win message.
//     *
//     * @param winner the nickname of the winner.
//     */
//    public abstract void showWinMessage(String winner);
//


    /**
     * @param toShow
     */
    @Override
    public abstract void onShow(Object toShow) ;  //generic to show everything in base to the type...

    @Override
    public abstract void updateCharactersList(List<ReducedCharacter> charactersList) ;
}
