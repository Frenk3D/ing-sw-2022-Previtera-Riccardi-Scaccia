package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    //attributes
    private int numofRedStudents;
    private int numofGreenStudents;
    private int numofYellowStudents;
    private int numofPinkStudents;
    private int numofBlueStudents;
    private List<Student> hallList;
    private List<Tower> towersList;
    private List<Professor> professorsList;


    //Methods
    //constructor
    public Dashboard(){
        numofRedStudents= 0;
        numofGreenStudents= 0;
        numofBlueStudents= 0;
        numofPinkStudents= 0;
        numofYellowStudents=0;
        hallList = new ArrayList<Student>(); //intellij suggests to remove Student from <>
        towersList = new ArrayList<Tower>();
        professorsList= new ArrayList<Professor>();
    }

    //getter
    public int getNumofRedStudents() {
        return numofRedStudents;
    }
    public int getNumofGreenStudents() {
        return numofGreenStudents;
    }
    public int getNumofYellowStudents() {
        return numofYellowStudents;
    }
    public int getNumofPinkStudents() {
        return numofPinkStudents;
    }
    public int getNumofBlueStudents() {
        return numofBlueStudents;
    }
    public List<Tower> getTowersList() {
        return towersList;
    }
    public List<Student> getHallList() {
        return hallList;
    }
    public List<Professor> getProfessorsList() {
        return professorsList;
    }

    //setter
    public void setTowersList(List<Tower> towersList) {
        this.towersList = towersList;
    }
    public void setHallList(List<Student> hallList) {
        this.hallList = hallList;
    }


    public void addRedStudent() {
        numofRedStudents++;
    }

    public void addBlueStudent() {
        numofBlueStudents++;
    }

    public void addGreenStudent() {
        numofGreenStudents++;
    }

    public void addYellowStudent() {
        numofYellowStudents++;
    }

    public void addPinkStudent() { numofPinkStudents++; }




}
