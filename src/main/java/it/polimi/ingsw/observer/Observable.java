package it.polimi.ingsw.observer;
//we do not use the basic observers etc... because they are deprecated, we use it for the Server

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of the deprecated Java observable. It is extended from {@link it.polimi.ingsw.model.GameModel}, {@link it.polimi.ingsw.view.RemoteView} and {@link it.polimi.ingsw.model.Round}
 */
public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * It is used to add an observer to the observers
     *
     * @param obs
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * It is used to remove an observer from the observer list
     *
     * @param obs
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * It calls update for every observer
     *
     * @param message
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

}
