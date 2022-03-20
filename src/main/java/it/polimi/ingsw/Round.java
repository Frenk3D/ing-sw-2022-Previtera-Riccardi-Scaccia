package it.polimi.ingsw;

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

    }
    private Player calculateInfluence(Island island){

    }
    public boolean useCharacter(Character character){

    }
}
