package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

import java.util.List;

public class Characters1and7and11 extends Character {
    //attributes
    private List<Student> cardStudentsList;

    //constructor

    public Characters1and7and11(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
        initStudents();
    }

    //methods

    private void initStudents(){
        Bag bag = Bag.getInstance();
        switch (id){
            case 1:
            case 11:
                cardStudentsList=bag.extractStudents(4);
                break;
            case 7:
                cardStudentsList=bag.extractStudents(6);
                break;
            default:
                break;
        }
    }

    private void moveStudent1(Island island, int studentIndex){
        try {
            island.getStudentsList().add(cardStudentsList.get(studentIndex));
            cardStudentsList.remove(studentIndex);
        }

        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private void moveStudent7(Player cardPlayer, List<Integer> studentsIndexList, List<Integer> studentsIndexEntranceList){
        for(Integer i : studentsIndexEntranceList){
            cardStudentsList.add(cardPlayer.getDashboard().getEntranceList().get(i));
            cardPlayer.getDashboard().getEntranceList().remove(i.intValue());
        }

        for (Integer i : studentsIndexList){
            cardPlayer.getDashboard().getEntranceList().add(cardStudentsList.get(i));
            cardStudentsList.remove(i.intValue());
        }
    }

    private void moveStudent11(Player cardPlayer, int studentIndex){
        cardPlayer.getDashboard().getEntranceList().add(cardStudentsList.get(studentIndex));
        cardStudentsList.remove(studentIndex);
        Bag bag = Bag.getInstance();
        cardStudentsList.add(bag.extractStudents(1).get(0));
    }

    @Override
    public void applyEffect(CharacterParameters params) {
        switch (id){

            case 1:
                moveStudent1(params.getIsland(),params.getStudentIndex());
                break;
            case 7:
                moveStudent7(params.getPlayer(),params.getStudentsIndexList(),params.getStudentsIndexEntranceList());
                break;
            case 11:
                moveStudent11(params.getPlayer(),params.getStudentIndex());
                break;
            default:
                break;
        }
    }

}
