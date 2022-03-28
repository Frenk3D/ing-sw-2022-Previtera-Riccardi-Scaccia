package it.polimi.ingsw.model;

import java.util.List;

public class Characters10and12 extends Character{

    //constructor

    public Characters10and12(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }
    //methods

    private void swapStudents(Player currPlayer){

    }

    private void fillBagFromHall(PawnColor color,List<Player> playersList){

    }

    @Override
    public void applyEffect(Object object) {
        if(object instanceof Player){
            swapStudents((Player) object);
        }
    }

    @Override
    public void applyEffect(Object object1, Object object2) {
        if (object1 instanceof PawnColor && object2 instanceof List){
            fillBagFromHall((PawnColor) object1,(List<Player>) object2);
        }
    }

    @Override
    public void applyEffect(Object object1, Object object2, Object object3) {

    }
}
