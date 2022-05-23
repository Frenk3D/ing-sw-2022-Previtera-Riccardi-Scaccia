package it.polimi.ingsw.observer;

//we use this for the Server MVC
import it.polimi.ingsw.network.message.Message;

public interface Observer {
    void update(Message message);


}
