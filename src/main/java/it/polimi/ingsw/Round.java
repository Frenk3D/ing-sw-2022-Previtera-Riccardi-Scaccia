package it.polimi.ingsw; //well connected to Game

import java.util.List;

public class Round {
    //attributes
    private int stage; //for now never used
    private Game game;  //intellij says it should be final,but it actually changes so it's not
    private Assistant extractedAssistant;
    private Player currPlayer;

    //Methods
    //constructor
    public Round(Game game){
        this.game=game;
    }

    //setter
    public void setPlayer(Player name){
        currPlayer= name;
    }


    public void moveStudentIsland(Student shiftedStudent,Island island){
        currPlayer.getDashboard().getHallList().remove(shiftedStudent);
        island.addStudent(shiftedStudent);
    }

    public void moveStudentDashboard(Student shiftedStudent){
        currPlayer.getDashboard().getHallList().remove(shiftedStudent);
        switch(shiftedStudent.getColor()){

            case RED:
                currPlayer.getDashboard().addRedStudent();
                break;
            case GREEN:
                currPlayer.getDashboard().addGreenStudent();
                break;
            case YELLOW:
                currPlayer.getDashboard().addYellowStudent();
                break;
            case PINK:
                currPlayer.getDashboard().addPinkStudent();
                break;
            case BLUE:
                currPlayer.getDashboard().addBlueStudent();
                break;
        }

    }
    public void updateIslandDomain(Island island){

    }
    public void mergeIsland(Island island){

    }

    public void takeFromCloud(Cloud cloud){
        for(Student s : cloud.getStudents()){
            currPlayer.getDashboard().getHallList().add(s);
            cloud.getStudents().remove(s);
        }
    }
    public boolean checkWin(){
        //chiama endgame of Game and returns false;
        return false;
    }

    private Player calculateInfluence(Island island){
        return null;
    }

    public boolean useCharacter(Character character){
        return false;
    }
}
