package it.polimi.ingsw.network.message;

public class LoginRequestMessage extends Message{
    int appCode;

    public LoginRequestMessage(){
        super(MessageType.LOGIN_REQUEST,9999);
    }

    public int getAppCode(){
        return appCode;
    }
}
