package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private int numofRedStudents;
    private int numofGreenStudents;
    private int numofYellowStudents;
    private int numofPinkStudents;
    private int numofBlueStudents;
    List<Student> hallList;
    List<Tower> towersList;
    List<Professor> professorsList;

    public Dashboard(){
        numofRedStudents= 0;
        numofGreenStudents= 0;
        numofBlueStudents= 0;
        numofPinkStudents= 0;
        numofYellowStudents=0;
        hallList = new ArrayList<Student>();
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

    public void setNumofRedStudents(int numofRedStudents) {
        this.numofRedStudents = numofRedStudents;
    }

    public void setNumofGreenStudents(int numofGreenStudents) {
        this.numofGreenStudents = numofGreenStudents;
    }

    public void setNumofYellowStudents(int numofYellowStudents) {
        this.numofYellowStudents = numofYellowStudents;
    }

    public void setNumofPinkStudents(int numofPinkStudents) {
        this.numofPinkStudents = numofPinkStudents;
    }

    public void setNumofBlueStudents(int numofBlueStudents) {
        this.numofBlueStudents = numofBlueStudents;
    }

    public List<Tower> getTowersList() {
        return towersList;
    }

    public void setTowersList(List<Tower> towersList) {
        this.towersList = towersList;
    }

    public void setHallList(List<Student> hallList) {
        this.hallList = hallList;
    }
}
