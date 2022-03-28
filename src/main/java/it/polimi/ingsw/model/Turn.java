package it.polimi.ingsw.model;

import java.util.List;

public class Turn {
    //attributes
    private int stage;
    private Player currPlayer;
    private Character usedCharacter;

    public Turn() {
    }

    //methods
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Character getUsedCharacter() {
        return usedCharacter;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setCurrPlayer(Player name) {
        this.currPlayer = name;
    }

    public void updateProfessorsLists(List<Player> playersList){

    }
}
