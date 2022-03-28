package it.polimi.ingsw.model;

import java.util.List;

public class Characters3and4and5 extends Character{
    //attributes
    private int forbidCards;

    //constructor
    public Characters3and4and5(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;

        if(id==5){
            initForbidCards();
        }
    }

    //methods
    private void updateIslandDomain(Island island, List<Player> playerList){

    }

    private void modifyMotherNaturePosShift(Player currPlayer){

    }

    private void initForbidCards(){
        forbidCards=4;
    }

    private void moveForbidCard(Island island){

    }

    @Override
    public void applyEffect(Object object) {
        if(object instanceof Player){
            modifyMotherNaturePosShift((Player) object);
        }
        else if(object instanceof Island){
            moveForbidCard((Island) object);
        }
    }

    @Override
    public void applyEffect(Object object1, Object object2) {
        if (object1 instanceof Island && object2 instanceof List){
            updateIslandDomain((Island) object1,(List<Player>) object2);
        }
    }

    @Override
    public void applyEffect(Object object1, Object object2, Object object3) {

    }
}
