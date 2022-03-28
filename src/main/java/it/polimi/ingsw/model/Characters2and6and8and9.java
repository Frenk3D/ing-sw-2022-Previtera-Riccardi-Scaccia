package it.polimi.ingsw.model;

import java.util.List;

public class Characters2and6and8and9 extends Character {

    //constructor

    public Characters2and6and8and9(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    //methods
    private void updateIslandDomain(Island island,List<Player> playersList){

    }

    private void updateIslandDomain(Island island,List<Player> playersList,PawnColor color){

    }


    private void modifiedCalculateInfluence(Player player){

    }
    private void modifiedCalculateInfluence(Player player, PawnColor color){

    }

    @Override
    public void applyEffect(Object object) {

    }

    @Override
    public void applyEffect(Object object1, Object object2) {
        if (object1 instanceof Island && object2 instanceof List){
            updateIslandDomain((Island) object1,(List<Player>) object2);
        }
    }

    @Override
    public void applyEffect(Object object1, Object object2, Object object3) {
        if(object1 instanceof Island && object2 instanceof List && object3 instanceof PawnColor){
            updateIslandDomain((Island) object1, (List<Player>) object2, (PawnColor) object3);
        }
    }
}
