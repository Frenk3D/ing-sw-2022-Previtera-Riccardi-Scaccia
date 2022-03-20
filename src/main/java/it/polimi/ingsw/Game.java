package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int currentPlayer;
    private int numOfPlayers;
    private int motherNaturePos;
    private boolean expertMode;
    private List<Cloud> cloudsList;
    private Bag bag;
    private List<Player> playersList;
    private List<Character> charactersList;
    private List<Professor> tableProfessorsList;
    private Round currRound;
    private List<Island> islandsList;


    //costruttore
    public Game(int numOfPlayers,boolean expertMode){
        this.numOfPlayers = numOfPlayers;
        this.expertMode = expertMode;
        bag = new Bag();
        cloudsList = new ArrayList<Cloud>();
        playersList = new ArrayList<Player>();
        charactersList = new ArrayList<Character>();
        tableProfessorsList = new ArrayList<Professor>();
        islandsList = new ArrayList<Island>();
        generateIslands();
        initStudentIsland();
        generateProfessorsList();
        insertTowers();
        placeStudentHall();
    }


    public List<Island> getIslands(){
        return islandsList;
    }
    public List<Cloud> getClouds(){
        return cloudsList;
    }
    public Bag getBag(){
        return bag;
    }

    public void addPlayer(String name){

    }
    public void start(){

    }
    public void generateIslands(){

    }
    private void setMotherNaturePosition(int pos){

    }
    public void initStudentIsland(){

    }
    public void generateProfessorsList(){

    }
    public void insertTowers(){

    }
    public void selectWizard(int type){

    }

    public void placeStudentHall(){

    }
    public void chooseStartingPlayer(){

    }

    public void endGame (Player winner){

    }
    public int getCurrentPlayer(){

        return currentPlayer;
    }

    public void extractCharacters(){

    }




}


