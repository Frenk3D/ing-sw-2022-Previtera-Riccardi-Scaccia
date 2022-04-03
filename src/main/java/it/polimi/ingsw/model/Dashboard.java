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
        redStudentsList = new ArrayList<>();
        greenStudentsList = new ArrayList<>();
        yellowStudentsList = new ArrayList<>();
        pinkStudentsList = new ArrayList<>();
        blueStudentsList = new ArrayList<>();
    }

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

    public Professor getProfessorByColor(PawnColor color){
        for(Professor p : professorsList){
            if(p.getColor().equals(color)){
                return p;
            }
        }
        return null;
    }

    public void placeStudentEntrance(int numOfPlayers){ //init function
        List<Student> entranceList = new ArrayList<>();
        int numOfStudents;
        if(numOfPlayers==2 || numOfPlayers==4){
            numOfStudents = 7;
        }
        else{
            numOfStudents = 9;
        }
        Bag bag = Bag.getInstance();
        entranceList = bag.extractStudents(numOfStudents);
        setEntranceList(entranceList);
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

    public void addStudentHall(Student student, Player currPlayer){
        getHallStudentsListByColor(student.getColor()).add(student);
        if ((getHallStudentsListByColor(student.getColor()).size() % 3)==0 && currPlayer.getMoney()!=-1){
            currPlayer.setMoney(currPlayer.getMoney()+1);
        }

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

    public int getNumOfHallStudents(PawnColor color){
        return getHallStudentsListByColor(color).size();
    }

}
