package it.polimi.ingsw.model;

import java.util.List;

public class Round {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;

    //constructor
    public Round(Turn currTurn, List<Player> playersOrder) {
        this.currTurn = currTurn;
        this.playersOrder = playersOrder;
    }
    //methods
    public void initRound(List<Player> playersList,List<Cloud> cloudsList){

    }
    public Player getNextPlayer(){
        return null;
    }
    public Turn getCurrTurn(){
        return currTurn;
    }

}
