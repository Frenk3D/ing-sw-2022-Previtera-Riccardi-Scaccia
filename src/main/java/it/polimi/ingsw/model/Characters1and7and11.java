package it.polimi.ingsw.model;

import java.util.List;

public class Characters1and7and11 extends Character {
    //attributes
    private List<Student> studentsList;

    //constructor


    public Characters1and7and11(int id, int initialCost, List<Student> studentsList) {
        super(id, initialCost);
        this.studentsList = studentsList;
    }

    //methods

    public void initStudents(){

    }
    public void moveStudent(Island island){

    }
    public void moveStudent(Player currPlayer){

    }
}
