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
    private final int numOfPlayers; //numofplayers, playerslist e expertmode final, deciso all' inizio,da vedere se private o friendly per generateTower?
    private int motherNaturePos;
    private final boolean expertMode;
    private List<Cloud> cloudsList;
    private Bag bag;
    private final List<Player> playersList;
    private List<Character> charactersList;
    private List<Professor> tableProfessorsList;
    private Round currRound;
    private boolean settingState;
    private boolean ingameState;
    private boolean finishedState;
    private List<Island> islandsList; //non varia anche quando si gruppano le isole, ho attrib nella classe Island


    //costruttore
    public Game(List<Player> playersList, boolean expertMode){
        this.numOfPlayers = playersList.size();
        this.expertMode = expertMode;
        this.playersList = playersList;
        bag = new Bag();
        currRound=new Round(this);
        cloudsList = new ArrayList<Cloud>();
        charactersList = new ArrayList<Character>();
        tableProfessorsList = new ArrayList<Professor>();
        islandsList = new ArrayList<Island>();
        settingState = true;
        ingameState = false;
        finishedState = false;
        generateIslands();
        initMotherNaturePos();
        initStudentIsland();
        generateProfessorsList();
        insertTowers();
        placeStudentHall();

        if(expertMode){
            extractCharacters();
        }
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

    /* DA METTERE IN UNA CLASSE SUPERIORE
    public void addPlayer(String name){
        Player p= new Player(name);
        this.playersList.add(p); //da aggiungere all'uml
    } */
    public void start(){
        settingState = false;
        ingameState = true;
        finishedState = false;

    }
    public void generateIslands(){ //come le genero, da dove vengono? serve json come dicono amici Nico?

    }
    private void setMotherNaturePosition(int pos){
        motherNaturePos=pos;

    }
    public void initMotherNaturePos(){
        int randomInt = (int)(Math.random() * (13));
        motherNaturePos = randomInt;
    }
    public void initStudentIsland(){ //da verificare
        int counter = 0;
        bag.initialBagFill();
        List<Student> l=bag.extractStudents(10);
        int emptyPos;
        if(motherNaturePos<6) emptyPos = motherNaturePos+6;
        else emptyPos = motherNaturePos-6;

        for (Island island: islandsList) {
            if(counter!=motherNaturePos && counter!=emptyPos) {
                island.addStudent(l.get(0)); //da vedere se va bene friendly o usare get
                l.remove(0);
            }
            counter++;
        }
    }
    public void generateProfessorsList(){ //come generateislands, da dove li prendo?
        tableProfessorsList.add(new Professor(PawnColor.YELLOW));
        tableProfessorsList.add(new Professor(PawnColor.RED));
        tableProfessorsList.add(new Professor(PawnColor.GREEN));
        tableProfessorsList.add(new Professor(PawnColor.BLUE));
        tableProfessorsList.add(new Professor(PawnColor.PINK));
    }
    public void insertTowers(){ //riempie la plancia di ogni giocatore con le torri, usa generate tower
        for(Player player : playersList){
            player.generateTower(this);
        }
    }
    public void selectWizard(Player player,int type){
        player.getAssistantDeck().setWizard(type);

    }


    public void placeStudentHall(){ //player piazza all'entrata della sua dashboard? serve altro attributo
        List<Student> hallList = new ArrayList<>();
        int numOfStudents;
        if(numOfPlayers==2 || numOfPlayers==4){
            numOfStudents = 7;
        }
        else{
            numOfStudents = 9;
        }
        for(Player p : playersList) {
            if(p.hasTower()) {
                hallList = bag.extractStudents(numOfStudents);
                p.getDashboard().setHallList(hallList);
            }
        }
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
        ingameState = false;
        finishedState = true;
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public void extractCharacters(){ //estraggo come? serve parametro id ?
        List<Character> newlist = Character.getAllCharacters();
        Collections.shuffle(newlist);
        charactersList.add(newlist.get(0));
        charactersList.add(newlist.get(1));
        charactersList.add(newlist.get(2));
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }



}


