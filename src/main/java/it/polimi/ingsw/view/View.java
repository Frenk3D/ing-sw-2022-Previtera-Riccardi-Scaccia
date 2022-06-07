package it.polimi.ingsw.view;



import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
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

    @Override
    public abstract void onSendSelectAssistant(List<ReducedAssistant> assistantList);

    @Override
    public abstract void onAskWhereToMoveStudent() ;

    /**
     * @param islandList
     * @param selectedAssistant
     */
    @Override
    public abstract void onSendMoveMotherNature(List<ReducedIsland> islandList, ReducedAssistant selectedAssistant) ;

    /**
     * @param cloudList
     */
    @Override
    public abstract void onSendChooseCloud(List<ReducedCloud> cloudList) ;

    /**
      * Shows  message.
      *
      * @param toShow It depends on the message: it's the nickname of the winner in case of win, it's the error string in case of error,
        *               it's a disconnection string in case of disconnection of a client in my lobby/game...
      */

    @Override
    public abstract void onShow(Object toShow) ;  //generic to show everything in base to the type...

    @Override
    public abstract void updateClientGameModel(ClientGameModel clientGameModel) ;

    @Override
    public abstract void onShowGame(ClientGameModel clientGameModel);
}
