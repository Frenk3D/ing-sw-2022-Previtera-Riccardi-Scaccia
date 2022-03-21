package it.polimi.ingsw; //ben collegato a Game

import java.util.List;

public class Round {
    private int stage; //for now never used
    private Game game;  //intellij dice deve essere final ma no, varia
    private Assistant extractedAssistant;
    private Player currPlayer;

    public Round(Game game){
        this.game=game;
    }

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
        //chiama endgame di Game return false;
        return false;
    }
    private Player calculateInfluence(Island island){
        return null;
    }
    public boolean useCharacter(Character character){
        return false;
    }
}
