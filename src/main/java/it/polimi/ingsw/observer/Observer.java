package it.polimi.ingsw.observer;

//we use this for the Server MVC
import it.polimi.ingsw.network.message.Message;

/**
 * This class is an implementation of the deprecated Java observer. It is implemented by the {@link it.polimi.ingsw.controller.Controller} and the {@link it.polimi.ingsw.view.RemoteView}
 */
public interface Observer {
    /**
     * Called by the observable, do something based on the message.
     * @param message
     */
    void update(Message message);


}
