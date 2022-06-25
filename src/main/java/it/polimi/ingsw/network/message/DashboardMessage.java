package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedDashboard;

public class DashboardMessage extends Message{
    ReducedDashboard dashboard;
    int playerId;

    public DashboardMessage(int senderId, ReducedDashboard dashboard, int playerId){
        super(MessageType.DASHBOARD,senderId,false);
        this.dashboard = dashboard;
        this.playerId = playerId;
    }

    public ReducedDashboard getDashboard() {
        return dashboard;
    }

    public int getPlayerId() {
        return playerId;
    }
}
