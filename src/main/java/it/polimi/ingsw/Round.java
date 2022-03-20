package it.polimi.ingsw;

import java.util.List;

public class Round {
    private int stage;
    private Game game;
    private Assistant extractedAssistant;
    private Player currPlayer;

    public Round(Game game){
        this.game=game;
    }

    public void setPlayer(Player name){
        currPlayer= name;
    }

    public void fillCloud(){
        List<Cloud> cloudList = game.getClouds();
        Bag bag = game.getBag();
        int studentsToExtract=3;
        if(game.getNumOfPlayers()==3) studentsToExtract=4;

        for(Cloud c: cloudList){
            c.setStudentsList(bag.extractStudents(studentsToExtract));
        }
    }

    public void moveStudentIsland(Student shiftedStudent,Island island){

    }
    public void moveStudentDashboard(Student shiftedStudent){

    }
    public void changeMotherNaturePosition(int pos){

    }
    public void updateIslandDomain(Island island){

    }
    public void mergeIsland(Island island){

    }
    public void takeFromCloud(Cloud cloud){

    }
    public boolean checkWin(){
        return false;
    }
    private Player calculateInfluence(Island island){
        return null;
    }
    public boolean useCharacter(Character character){
        return false;
    }
}
