package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.List;

public class Characters10and12 extends Character{

    private Bag bag;

    //constructor
    public Characters10and12(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }
    //methods

    private boolean swapStudents10(Player cardPlayer, List<Integer> studentsIndexEntranceList, PawnColor hallStudentColor1, PawnColor hallStudentColor2){
        //if we swap students of the same colors, the ref of the students swapped are not right, but the colors yes, it's ok

        try {
            for (Integer i : studentsIndexEntranceList) {
                PawnColor selectedColor = cardPlayer.getDashboard().getEntranceList().get(i).getColor(); //the ref of the students are different, but they are the same colors
                cardPlayer.getDashboard().getHallStudentsListByColor(selectedColor).add(cardPlayer.getDashboard().getEntranceList().get(i));
                cardPlayer.getDashboard().getEntranceList().remove(i.intValue());
            }
            int size1 = cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor1).size();
            cardPlayer.getDashboard().getEntranceList().add(cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor1).remove(size1 - 1));
            if (studentsIndexEntranceList.size() == 2) {
                int size2 = cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor2).size();
                cardPlayer.getDashboard().getEntranceList().add(cardPlayer.getDashboard().getHallStudentsListByColor(hallStudentColor2).remove(size2 - 1));
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private boolean fillBagFromHall12(PawnColor hallColor,List<Player> playersList){
        try {
            for (Player p : playersList) {
                for (int i = 0; i < 3; i++) {
                    try {
                        int size = p.getDashboard().getNumOfHallStudents(hallColor);
                        Student s = p.getDashboard().getHallStudentsListByColor(hallColor).remove(size - 1);
                        bag.getStudentsList().add(s);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No more students of this color");
                        e.printStackTrace();
                    }

                }
            }
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean applyEffect(CharacterParameters params) {
        switch (id){
            case 10:
                return swapStudents10(params.getPlayer(),params.getStudentsIndexList(),params.getSelectedColor(),params.getSelectedColor2());
            case 12:
                return fillBagFromHall12(params.getSelectedColor(),params.getPlayersList());
            default:
                return false;
        }
    }

    @Override
    public void initCharacter(CharacterParameters params) {
        bag = params.getBag();
    }
}
