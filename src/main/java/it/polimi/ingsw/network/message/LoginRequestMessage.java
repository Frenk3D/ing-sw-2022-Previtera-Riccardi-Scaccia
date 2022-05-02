package it.polimi.ingsw.network.message;

public class LoginRequestMessage extends Message{
    private int appCode;

    public LoginRequestMessage(int senderId, int appCode){
        super(MessageType.LOGIN_REQUEST,senderId);
        this.appCode = appCode;
    }

    public int getAppCode(){
        return appCode;
    }
}
