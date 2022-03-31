package it.polimi.ingsw.model;

import java.util.List;

public class Characters10and12 extends Character{

    //constructor

    public Characters10and12(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }
    //methods

    private void swapStudents10(Player currPlayer, List<Integer> studentsIndexList){

    }

    private void fillBagFromHall12(PawnColor color,List<Player> playersList){

    }

    @Override
    public void applyEffect(CharacterParameters params) {
        switch (id){
            case 10:
                swapStudents10(params.getPlayer(),params.getStudentsIndexList());
                break;
            case 12:
                fillBagFromHall12(params.getSelectedColor(),params.getPlayersList());
                break;
        }
    }
}
