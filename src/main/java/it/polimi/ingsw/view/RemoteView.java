package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.SocketClientManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
//VIEW FOR SERVER
public class RemoteView extends Observable implements Observer {

    private SocketClientManager clientManager;

    public RemoteView(SocketClientManager clientManager){
        this.clientManager=clientManager;
    }

    //receive model updates and send to client
    @Override
    public void update(Message message) {
        clientManager.sendMessage(message);
    }

    public void sendToController(Message message){
        notifyObserver(message);
    }


}
