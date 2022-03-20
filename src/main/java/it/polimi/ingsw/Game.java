package it.polimi.ingsw;
/*Tipo "main" collegato a Round : Game supervisor con dati e Round operator con azioni (MODEL);
molti warning su cose non usate perche' ancora da finire implementazione,
commenti in italiano per comodita'
*/

import java.util.ArrayList;
import java.util.List; ////Intellij consiglia di togliere Student ecc da <> quando si inizializza (i prof no), prova a toglierlo in Bag
import java.util.Collections;

public class Game {
    private int currentPlayer;
    final int numOfPlayers; //numofplayers, playerslist e expertmode final, deciso all' inizio,da vedere se private o friendly per generateTower?
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

    public void addPlayer(Player player){
        this.playersList.add(player); //da aggiungere all'uml
    }
    public void start(){

    }
    public void generateIslands(){ //come le genero, da dove vengono? serve json come dicono amici Nico?

    }
    private void setMotherNaturePosition(int pos){
        motherNaturePos=pos;

    }
    public void initStudentIsland(){ //da verificare
        int counter = 0;
        Collections.shuffle(bag.studentsList); //mischia l'arraylist per randomicità
        for (Island island: islandsList) {
            if(counter!=motherNaturePos && counter!=motherNaturePos+6) {
                island.addStudent(bag.studentsList.get(0)); //da vedere se va bene friendly o usare get
                bag.studentsList.remove(0);
                island.addStudent(bag.studentsList.get(0));
                bag.studentsList.remove(0);
            }
            counter++;
        }
    }
    public void generateProfessorsList(){ //come generateislands, da dove li prendo?

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
        playersList.get(currentPlayer).getAssistantDeck().setWizard(type);

    }

    public void placeStudentHall(){ //player piazza all'entrata della sua dashboard? serve altro attributo

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

    public void extractCharacters(){ //estraggo come? serve parametro id ?

    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }



}


