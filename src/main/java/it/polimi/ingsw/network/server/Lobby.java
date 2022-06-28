package it.polimi.ingsw.network.server;

import java.io.Serializable;

/**
 * This class implements the object Lobby
 * each lobby contains a variable number of player and once it is filled with the actual number of players it becomes a game
 * each lobby has a name and a game mode (normal or expert)
 */
public class Lobby implements Serializable {
    private int numOfPlayers;
    private int actualNumOfPlayers;
    private boolean expertMode;
    private String name;

    /**
     * Default controller
     * @param numOfPlayers the number of players in the lobby
     * @param actualNumOfPlayers the player's capacity of the lobby
     * @param expertMode the game mode
     * @param name the lobby's name
     */
    public Lobby(int numOfPlayers,int actualNumOfPlayers,boolean expertMode, String name){
        this.numOfPlayers = numOfPlayers;
        this.actualNumOfPlayers = actualNumOfPlayers;
        this.expertMode = expertMode;
        this.name = name;
    }

    /**
     *
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     *
     * @return the actual number of players
     */
    public int getActualNumOfPlayers() {
        return actualNumOfPlayers;
    }

    /**
     *
     * @return {@code true} if the game is in expert mode {@code false} if it is in normal mode
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     *
     * @return the lobby's name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: "+name+" NumOfPlayers: "+numOfPlayers+" Actual NumOfPlayers: "+actualNumOfPlayers+" Expert Mode: "+expertMode;
    }
}
