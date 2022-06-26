package it.polimi.ingsw.network.message;

import it.polimi.ingsw.client.ReducedDashboard;

/**
 * This class implements the DashboardMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message contains a reduced dashboard
 */
public class DashboardMessage extends Message{
    ReducedDashboard dashboard;
    int playerId;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param dashboard the reduced dashboard
     * @param playerId the id of the player who own the dashboard
     */
    public DashboardMessage(int senderId, ReducedDashboard dashboard, int playerId){
        super(MessageType.DASHBOARD,senderId,false);
        this.dashboard = dashboard;
        this.playerId = playerId;
    }

    /**
     *
     * @return the reduced dashboard
     */
    public ReducedDashboard getDashboard() {
        return dashboard;
    }

    /**
     *
     * @return the id of the player who owns the dashboard
     */
    public int getPlayerId() {
        return playerId;
    }
}
