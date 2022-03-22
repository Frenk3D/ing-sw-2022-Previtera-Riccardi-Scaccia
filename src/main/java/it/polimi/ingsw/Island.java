package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private int nextIsland; //used to manage groups
    private int prevIsland;
    private boolean forbidCard;
    private Tower tower;
    private List<Student> studentsList;

    public Island(){
        nextIsland= -1;
        prevIsland= -1;
        forbidCard= false;
        tower= null;
        studentsList= new ArrayList<Student>();
    }
    public int getNextIsland() {
        return nextIsland;
    }

    public int getPrevIsland() {
        return prevIsland;
    }

    public boolean getForbidCard() {
        return forbidCard;
    }

    public Tower getTower() {
        return tower;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void setNextIsland(int nextIsland) {
        this.nextIsland = nextIsland;
    }

    public void setPrevIsland(int prevIsland) {
        this.prevIsland = prevIsland;
    }

    public void setForbidCard(boolean forbidCard) {
        this.forbidCard = forbidCard;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public void addStudent(Student student){
        this.studentsList.add(student);
    }
}
