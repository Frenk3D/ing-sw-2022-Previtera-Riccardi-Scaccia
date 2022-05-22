package it.polimi.ingsw.network.message;

import java.util.Map;

public class ServerInfoMessage extends Message{
    private Map<String, String> serverInfo;

    public ServerInfoMessage(MessageType type, int senderId, Map<String, String> serverInfo){
        super(type,senderId,true);
        this.serverInfo = serverInfo;
    }

    public Map<String, String> getServerInfo() {
        return serverInfo;
    }
}
