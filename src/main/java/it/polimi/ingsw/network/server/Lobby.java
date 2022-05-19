package it.polimi.ingsw.network.server;

import java.io.Serializable;

public class Lobby implements Serializable {
    private int numOfPlayers;
    private int actualNumOfPlayers;
    private boolean expertMode;
    private String name;

    public Lobby(int numOfPlayers,int actualNumOfPlayers,boolean expertMode, String name){
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

    @Override
    public String toString() {
        return "Name: "+name+" NumOfPlayers: "+numOfPlayers+" Actual NumOfPlayers: "+actualNumOfPlayers+" Expert Mode: "+expertMode;
    }
}
