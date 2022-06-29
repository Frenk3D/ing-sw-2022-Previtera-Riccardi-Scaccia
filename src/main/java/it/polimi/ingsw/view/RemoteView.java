package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.SocketClientManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

/**
 * This class is used to communicate with the respective clients. It extends {@link Observable} and implements {@link Observer}
 */
public class RemoteView extends Observable implements Observer {

    private final SocketClientManager clientManager;

    /**
     * Default constructor
     *
     * @param clientManager the client manager
     */
    public RemoteView(SocketClientManager clientManager) {
        this.clientManager = clientManager;
    }

    //receive model updates and send to client
    @Override
    public void update(Message message) {
        clientManager.sendMessage(message);
    }

    /**
     * Sends a message to the controller
     *
     * @param message the message sent
     */
    public void sendToController(Message message) {
        notifyObserver(message);
    }

    /**
     * Sends a message to the client
     *
     * @param message the message sent
     */
    public void sendToClient(Message message) {
        clientManager.sendMessage(message);
    }

}
