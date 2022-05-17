package it.polimi.ingsw.network.server;

public class Lobby {
    private int numOfPlayers;
    private int actualNumOfPlayers;
    private boolean expertMode;
    private String name;

    public Lobby(int numOfPlayers,int actualNumOfPlayers,boolean expertMode,String name){
        this.numOfPlayers = numOfPlayers;
        this.actualNumOfPlayers = actualNumOfPlayers;
        this.expertMode = expertMode;
        this.name = name;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getActualNumOfPlayers() {
        return actualNumOfPlayers;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public String getName() {
        return name;
    }
}
