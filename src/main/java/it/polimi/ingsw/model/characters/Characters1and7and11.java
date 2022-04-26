package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Characters1and7and11 extends Character {
    //attributes
    private List<Student> cardStudentsList;
    Bag bag;

    //constructor

    public Characters1and7and11(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    //methods

    private void initStudents(){
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

    private boolean moveStudent1(Island island, int studentIndex){
        try {
            island.getStudentsList().add(cardStudentsList.get(studentIndex));
            cardStudentsList.remove(studentIndex);
            return true;
        }

        catch (Exception e){
            return false;
        }
    }

    private boolean moveStudent7(Player cardPlayer, List<Integer> studentsIndexList, List<Integer> studentsIndexEntranceList){
        try {
            List<Student> tmpDashboardStudents = new ArrayList<>();
            List<Student> tmpCardStudents = new ArrayList<>();
            for (Integer i : studentsIndexEntranceList) {
                tmpDashboardStudents.add(cardPlayer.getDashboard().getEntranceList().get(i));
            }

            for (Integer i : studentsIndexList) {
                tmpCardStudents.add(cardStudentsList.get(i));
            }

            for (Student s : tmpDashboardStudents){
                cardStudentsList.add(s);
                cardPlayer.getDashboard().getEntranceList().remove(s);
            }

            for (Student s : tmpCardStudents) {
                cardPlayer.getDashboard().getEntranceList().add(s);
                cardStudentsList.remove(s);
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private boolean moveStudent11(Player cardPlayer, int studentIndex){
        try {
            cardPlayer.getDashboard().getEntranceList().add(cardStudentsList.get(studentIndex));
            cardStudentsList.remove(studentIndex);
            cardStudentsList.add(bag.extractStudents(1).get(0));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean applyEffect(CharacterParameters params) {
        switch (id){
            case 1:
                return moveStudent1(params.getIsland(),params.getStudentIndex());
            case 7:
                return moveStudent7(params.getPlayer(),params.getStudentsIndexList(),params.getStudentsIndexEntranceList());
            case 11:
                return moveStudent11(params.getPlayer(),params.getStudentIndex());
            default:
                return false;
        }
    }

    @Override
    public void initCharacter(CharacterParameters params) {
        bag = params.getBag();
        initStudents();
    }

    //for test purposes


    public List<Student> getCardStudentsList() {
        return cardStudentsList;
    }
}
