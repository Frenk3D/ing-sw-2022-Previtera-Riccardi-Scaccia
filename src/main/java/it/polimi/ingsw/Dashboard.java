package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private int numofRedStudents;
    private int numofGreenStudents;
    private int numofYellowStudents;
    private int numofPinkStudents;
    private int numofBlueStudents;
    private List<Student> hallList;
    private List<Tower> towersList;
    private List<Professor> professorsList;

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

    public List<Tower> getTowersList() {
        return towersList;
    }

    public List<Student> getHallList() {
        return hallList;
    }

    public List<Professor> getProfessorsList() {
        return professorsList;
    }

    public void setTowersList(List<Tower> towersList) {
        this.towersList = towersList;
    }

    public void setHallList(List<Student> hallList) {
        this.hallList = hallList;
    }
}
