package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.SocketClientManager;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class RemoteView extends Observable implements Observer {

    private SocketClientManager clientManager;

    public RemoteView(SocketClientManager clientManager){
        this.clientManager=clientManager;
    }

    @Override
    public void update(Message message) {

    }

    public void receive(Message message){

    }

}
