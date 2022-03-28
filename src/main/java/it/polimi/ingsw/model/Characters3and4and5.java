package it.polimi.ingsw.model;

import java.util.List;

public class Characters3and4and5 extends Character{
    //attributes
    private int forbidCards;

    //constructor
    public Characters3and4and5(int id, int initialCost) {
        super(id, initialCost);
        forbidCards=4;
    }
    //methods
    public void updateIslandDomain(Island island, List<Player> playerList){

    }
    public void modifyMotherNaturePosShift(Player currPlayer){

    }
    public void initForbidCards(){

    }
    public void moveForbidCard(Island island){

    }
}
