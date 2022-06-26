package it.polimi.ingsw.network.message;

 /**
  * This class implements the StringMessage,it extends {@link it.polimi.ingsw.network.message.Message}
  * this message contains a string
  */
public class StringMessage extends Message{
    private String string;

     /**
      * Default constructor
      * @param type the message type
      * @param senderId the id of the message sender
      * @param initMessage the initialization message
      * @param string the string contained in the message
      */
    public StringMessage(MessageType type, int senderId, boolean initMessage, String string){
        super(type,senderId,initMessage);
        this.string = string;
    }

     /**
      *
      * @return the string contained in the message
      */
    public String getString() {
        return string;
    }
}
