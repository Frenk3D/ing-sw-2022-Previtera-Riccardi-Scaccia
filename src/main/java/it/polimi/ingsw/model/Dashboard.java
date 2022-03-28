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
