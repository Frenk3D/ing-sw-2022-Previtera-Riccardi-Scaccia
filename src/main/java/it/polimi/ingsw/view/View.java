package it.polimi.ingsw.view;



import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.server.*;
import it.polimi.ingsw.observer.*;

import java.util.List;
import java.util.Map;
//All the checks are in the specific views, not in controller or in game model, because we WANT to have same prototypes for CLI (with prints etc.) and GUI (with shows), ok also for javadoc
//VIEW FOR CLIENT if a client has to send/write something is an ask  (from the controller, params are the things where I have to choose) or it's a show method (like the sync message or else automatic, params are the things to show)

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI, ...). It is an implementation of the {@link ModelObserver}, and extends {@link ViewObservable}
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
    public abstract void onSendSelectAssistant();

    @Override
    public abstract void onAskWhereToMoveStudent() ;

    @Override
    public abstract void onSendMoveMotherNature();


    @Override
    public abstract void onSendChooseCloud() ;

    @Override
    public abstract void onAskCharacterParameters(int characterId);

    /**
      * Shows  message.
      *
      * @param toShow It depends on the message: it's the nickname of the winner in case of win, it's the error string in case of error,
        *               it's a disconnection string in case of disconnection of a client in my lobby/game...
      */

    @Override
    public abstract void onShow(Object toShow) ;  //generic to show everything in base to the type...
    @Override
    public abstract  void onShowPlayerJoin(List<String> playersList) ;

    @Override
    public abstract void updateClientGameModel(ClientGameModel clientGameModel) ;

    @Override
    public abstract void onShowGame(ClientGameModel clientGameModel);

    @Override
    public abstract void onShowChosenTeam(String toShow);
}
