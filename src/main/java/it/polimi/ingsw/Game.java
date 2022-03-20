package it.polimi.ingsw;
/*Tipo "main" collegato a Round : Game supervisor con dati e Round operator con azioni (MODEL);
molti warning su cose non usate perche' ancora da finire implementazione,
commenti in italiano per comodita'
*/

import java.util.ArrayList;
import java.util.List; ////Intellij consiglia di togliere Student ecc da <> quando si inizializza (i prof no), prova a toglierlo in Bag


public class Game {
    private int currentPlayer;
    private final int numOfPlayers; //numofplayers, playerslist e expertmode final, deciso all' inizio
    private int motherNaturePos;
    private final boolean expertMode;
    private List<Cloud> cloudsList;
    private Bag bag;
    private final List<Player> playersList;
    private List<Character> charactersList;
    private List<Professor> tableProfessorsList;
    private Round currRound;
    private List<Island> islandsList; //non varia anche quando si gruppano le isole, ho attrib nella classe Island


    //costruttore
    public Game(int numOfPlayers,boolean expertMode){
        this.numOfPlayers = numOfPlayers;
        this.expertMode = expertMode;
        bag = new Bag();
        currRound=new Round(this);
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
        motherNaturePos=pos;

    }
    public void initStudentIsland(){

    }
    public void generateProfessorsList(){

    }
    public void insertTowers(){ //riempie la plancia di ogni giocatore con le torri
        for(Player p : playersList){
            TowerColor color = p.getTowerColor();
            int towersToCreate=8;
            if(numOfPlayers==3) towersToCreate=6;

            while(towersToCreate>0){
                Tower t = new Tower(color);
                p.getDashboard().getTowersList().add(t);
                towersToCreate--;
            }
        }
    }
    public void selectWizard(int type){

    }

    public void placeStudentHall(){

    }

    public void chooseStartingPlayer(){ //sceglie chi deve iniziare il gioco all'inizio
        int randomInt = (int)(Math.random() * (numOfPlayers + 1));
        currentPlayer=randomInt;
    }

    public Player getNextPlayer(){ //NUOVA FUNZIONE(da completare)!! ritorna di chi è il prossimo turno in base agli assistenti scelti
        int currMin=11;
        Player currMinPlayer=null;
        for (Player p: playersList){
            if(p.getSelectedAssistant().getValue()<currMin){
                currMin=p.getSelectedAssistant().getValue();
                currMinPlayer=p;
            }
        }
        return currMinPlayer;
    }

    public void endGame (Player winner){ //round me lo usa?

    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public void extractCharacters(){

    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }



}


