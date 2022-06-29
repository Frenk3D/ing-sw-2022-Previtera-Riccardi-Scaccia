package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this class implements the game object Dashboard
 * the dashboard contains the hall (with students and professor),the entrance (with students),and the towers
 * the towers can be missing from the dashboard if you are not the team leader of your team (in a 4 players game)
 */
public class Dashboard {
    //attributes
    private List<Student> redStudentsList;
    private List<Student> greenStudentsList;
    private List<Student> yellowStudentsList;
    private List<Student> pinkStudentsList;
    private List<Student> blueStudentsList;

    private List<Student> entranceList;
    private List<Tower> towersList;
    private List<Professor> professorsList;



    /**
     * default constructor
     */
    public Dashboard(){
        entranceList = new ArrayList<>(); //intellij suggests to remove Student from <>
        towersList = new ArrayList<>();
        professorsList= new ArrayList<>();
        redStudentsList = new ArrayList<>();
        greenStudentsList = new ArrayList<>();
        yellowStudentsList = new ArrayList<>();
        pinkStudentsList = new ArrayList<>();
        blueStudentsList = new ArrayList<>();
    }

    /**
     *
     * @param color the pawn color
     * @return the list of students in the hall of the selected color
     */
    public List<Student> getHallStudentsListByColor(PawnColor color){
        switch (color){
            case RED:
                return redStudentsList;
            case GREEN:
                return greenStudentsList;
            case YELLOW:
                return yellowStudentsList;
            case PINK:
                return pinkStudentsList;
            case BLUE:
                return blueStudentsList;
        }
        return null;
    }

    /**
     *
     * @param color the pawn color
     * @return a professor of the selected color
     */
    public Professor getProfessorByColor(PawnColor color){
        for(Professor p : professorsList){
            if(p.getColor().equals(color)){
                return p;
            }
        }
        return null;
    }

    /**
     * places the students on the entrance
     * the number of students placed varies based on the number of players in the game
     * @param numOfPlayers the number of players
     * @param bag the bag
     */
    public void placeStudentEntrance(int numOfPlayers, Bag bag){ //init function, we can't add to many students for the entrance's size
        List<Student> entranceList;
        entranceList = new ArrayList<>();
        int numOfStudents;
        if(numOfPlayers==2 || numOfPlayers==4){
            numOfStudents = 7;
        }
        else{
            numOfStudents = 9;
        }
        entranceList = bag.extractStudents(numOfStudents);
        setEntranceList(entranceList);
    }

    /**
     * generates the player's (or the team's) towers
     * the number of towers generated varies based on the number of players in the game
     * @param numOfPlayers the number of players
     * @param playerTowerColor the player's tower color
     */
    public void generateTower(int numOfPlayers, TowerColor playerTowerColor){ //number of players is needed
        int towersToGenerate;
        if(numOfPlayers==2 || numOfPlayers==4) {
            towersToGenerate = 8;
        }
        else {
            towersToGenerate = 6;
        }
        List<Tower> towers = new ArrayList<>();
        for(int i=0;i<towersToGenerate;i++){
            towers.add(new Tower(playerTowerColor));
        }
        setTowersList(towers);
    }

    /**
     *
     * @param student the student
     * @param currPlayer the current player
     * @param tableMoney the money on the table
     * @return adds a student in the hall
     */
    public boolean addStudentHall(Student student, Player currPlayer, AtomicInteger tableMoney){
        if (getHallStudentsListByColor(student.getColor()).size()==10){
            return false;
        }
        getHallStudentsListByColor(student.getColor()).add(student);
        if ((getHallStudentsListByColor(student.getColor()).size() % 3)==0 && tableMoney != null){ // if null it's not expert mode
            currPlayer.modifyMoney(1,tableMoney);
        }
        return true;
    }

    /**
     * sets the list of towers
     * @param towersList the list of towers
     */
    public void setTowersList(List<Tower> towersList) {
        this.towersList = towersList;
    }

    /**
     *
     * @return the list of towers
     */
    public List<Tower> getTowersList() {
        return towersList;
    }

    /**
     * sets the list of students in the entrance
     * @param entranceList the entrance list
     */
    public void setEntranceList(List<Student> entranceList) {
        this.entranceList = entranceList;
    }

    /**
     *
     * @return the list of students in the entrance
     */
    public List<Student> getEntranceList() {
        return entranceList;
    }

    /**
     *
     * @return the list of professors
     */
    public List<Professor> getProfessorsList() {
        return professorsList;
    }

    /**
     *
     * @param color the pawn color
     * @return the number of hall students of the selected color
     */
    public int getNumOfHallStudents(PawnColor color){
        return getHallStudentsListByColor(color).size();
    }

    /**
     *
     * @param studentIndex the index of the student
     * @return a student in the entrance student list selected by index
     */
    public Student getEntranceStudentByIndex(int studentIndex){
        if(studentIndex >= entranceList.size() || studentIndex<0){
            return null;
        }
        return entranceList.get(studentIndex);
    }
}
