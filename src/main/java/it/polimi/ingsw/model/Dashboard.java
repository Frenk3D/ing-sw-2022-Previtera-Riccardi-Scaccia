package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

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


    //Methods
    //constructor
    public Dashboard(){
        entranceList = new ArrayList<Student>(); //intellij suggests to remove Student from <>
        towersList = new ArrayList<Tower>();
        professorsList= new ArrayList<Professor>();
    }

    public List<Student> getStudentsListByColor(PawnColor color){
        return null;
    }

    public void placeStudentEntrance(int numOfPlayers){
        List<Student> hallList = new ArrayList<>();
        int numOfStudents;
        if(numOfPlayers==2 || numOfPlayers==4){
            numOfStudents = 7;
        }
        else{
            numOfStudents = 9;
        }
        Bag bag = Bag.getInstance();
        hallList = bag.extractStudents(numOfStudents);
        setEntranceList(hallList);
    }

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

    public void setTowersList(List<Tower> towersList) {
        this.towersList = towersList;
    }

    public List<Tower> getTowersList() {
        return towersList;
    }


    public void setEntranceList(List<Student> studentList) {
        this.entranceList = entranceList;
    }


    public List<Student> getEntranceList() {
        return entranceList;
    }


    public List<Professor> getProfessorsList() {
        return professorsList;
    }


}