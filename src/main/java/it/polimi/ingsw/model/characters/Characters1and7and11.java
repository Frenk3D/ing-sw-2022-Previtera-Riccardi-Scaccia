package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Characters1and7and11 extends Character {
    //attributes
    private List<Student> cardStudentsList;
    private Bag bag;
    private final Logger logger = Logger.getLogger(getClass().getName());


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
            //check if selected island and student exist
                if(island==null){ //never happen
                    logger.log(Level.SEVERE,"selected island does not exist");
                    return false;

                }

                if(cardStudentsList.get(studentIndex)==null){
                    logger.log(Level.SEVERE,"selected students does not exist");
                    return false;
                }

            island.getStudentsList().add(cardStudentsList.get(studentIndex));
            cardStudentsList.remove(studentIndex);
            List<Student> newStudentList = bag.extractStudents(1);
            if(newStudentList != null){
                cardStudentsList.add(newStudentList.get(0));
            }
            return true;
        }

        catch (Exception e){
            return false;
        }
    }

    private boolean moveStudent7(Player cardPlayer, List<Integer> studentsIndexList, List<Integer> studentsIndexEntranceList){
        try {

            //check if selected students exist
            for (Integer i : studentsIndexEntranceList) {
                if(cardPlayer.getDashboard().getEntranceStudentByIndex(i)==null){
                    logger.log(Level.SEVERE,"selected entrance students does not exist");
                    return false;
                }
            }

            for (Integer i : studentsIndexList) {
                if(cardStudentsList.get(i)==null){
                    logger.log(Level.SEVERE,"selected students from the card does not exist");
                    return false;
                }
            }


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

    private boolean moveStudent11(Player cardPlayer, int studentIndex, AtomicInteger tableMoney){

        try {
            //check if selected students exist
            if(cardPlayer.getDashboard().getEntranceStudentByIndex(studentIndex)==null){
                logger.log(Level.SEVERE,"selected entrance students does not exist");
                return false;
            }

            //cardPlayer.getDashboard().getHallStudentsListByColor(cardStudentsList.get(studentIndex).getColor()).add(cardStudentsList.get(studentIndex));
            cardPlayer.getDashboard().addStudentHall(cardStudentsList.get(studentIndex), cardPlayer,tableMoney);
            cardStudentsList.remove(studentIndex);
            List<Student> newStudentList = bag.extractStudents(1);
            if(newStudentList != null){
                cardStudentsList.add(newStudentList.get(0));
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
            case 1:
                return moveStudent1(params.getIsland(),params.getStudentIndex());
            case 7:
                return moveStudent7(params.getPlayer(),params.getStudentsIndexList(),params.getStudentsIndexEntranceList());
            case 11:
                return moveStudent11(params.getPlayer(),params.getStudentIndex(), params.getTableMoney());
            default:
                return false;
        }
    }

    @Override
    public void initCharacter(CharacterParameters params) {
        bag = params.getBag();
        initStudents();
    }


    public List<Student> getCardStudentsList() {
        return cardStudentsList;
    }
}
