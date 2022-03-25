package it.polimi.ingsw;
/*Like "main" connected to Round : Game supervisor with data and Round operator with actions (Controller) (Model the others classes, view to do);
many warnings on unused things because yet to be implemented or other else.
? :  operator ok but better if
exceptions a bit from view, but also here
we use a lot of concatenated methods, asked, not a problem for Cugola

CONSEGNE:
MIGLIORARE LEGGIBILITA' E USO CLASSI/METODI AL POSTO DI IF ECC,
UN PACKAGE PER CLIENT E UNO PER SERVER E QUI DENTRO PACKAGE MODEL-CONTROLLER E PACKAGE RETE(VIEW?),
METTERE ECCEZIONI, FINIRE METODI IN ROUND, FARE I TEST, FINIRE IMPLEMENTAZIONE PERSONAGGI (CON CLASSI/SOTTO CLASSI?)
PASSARE TIPI SEMPLICI AL POSTO DI OGGETTI IN TUTTI I METODI (INT, BOOLEAN E STRING OK, COME CON METODO playerThrowsAssistant DELLA CLASSE GAME), 
        SE PASSO OGGETTI PASSO COPIA E NON PUNTATORE, POSSO AVERE PROBLEMI.
FINIRE E FARE DESCRIZIONE NOSTRO UML PER IL GRUPPO GC57 E FARE REVIEW UML PER IL GRUPPO GC12 (INVIARE TUTTO PER EMAIL REFERENTE)
*/

import java.util.ArrayList;
import java.util.List; ////Intellij advises to remove Student ecc from <> when initializing List (professors don't), try to remove it in Bag
import java.util.Collections;

public class Game {
    //attributes
    private int currentPlayer;
    private final int numOfPlayers; //numofplayers, playerslist and expertmode final, decided from the start
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
    private List<Island> islandsList; //doesn't change even when you group islands, attrib in Island class
    private List<Player> playersOrder;

    //constructor
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
        playersOrder = new ArrayList<Player>();
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

    //Methods
    //getter
    public List<Island> getIslands(){
        return islandsList;
    }
    public List<Cloud> getClouds(){
        return cloudsList;
    }
    public Bag getBag(){
        return bag;
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    public int getNumOfPlayers(){
        return numOfPlayers;
    }


    public void setMotherNaturePosition(int pos){
        motherNaturePos=pos;
    }

    public void start(){
        settingState = false;
        ingameState = true;
        finishedState = false;

    }

    public void generateIslands(){
        for(int i=0; i<12; i++){
            islandsList.add(new Island());
        }
    }


    public void initMotherNaturePos(){
        int randomInt = (int)(Math.random() * (13));
        motherNaturePos = randomInt;
    }
    public void initStudentIsland(){
        int counter = 0;
        bag.initialBagFill();
        List<Student> l=bag.extractStudents(10);
        int emptyPos;
        if(motherNaturePos<6) emptyPos = motherNaturePos+6;
        else emptyPos = motherNaturePos-6;

        for (Island island: islandsList) {
            if(counter!=motherNaturePos && counter!=emptyPos) {
                island.addStudent(l.get(0));
                l.remove(0);
            }
            counter++;
        }
    }
    public void generateProfessorsList(){
        tableProfessorsList.add(new Professor(PawnColor.YELLOW));
        tableProfessorsList.add(new Professor(PawnColor.RED));
        tableProfessorsList.add(new Professor(PawnColor.GREEN));
        tableProfessorsList.add(new Professor(PawnColor.BLUE));
        tableProfessorsList.add(new Professor(PawnColor.PINK));
    }
    public void insertTowers(){ //fills every player's dashboard with towers, uses generate tower
        for(Player player : playersList){
            player.generateTower(this);
        }
    }
    public void selectWizard(Player player,int type){
        player.getAssistantDeck().setWizard(type);

    }


    public void placeStudentHall(){
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

    public void chooseStartingPlayer(){ //chooses the first Player at the beginning of the game
        int randomInt = (int)(Math.random() * (numOfPlayers + 1));
        currentPlayer=randomInt;
    }
    public void fillCloud(){
        int studentsToExtract=3;
        if(numOfPlayers==3) studentsToExtract=4;

        for(Cloud c: cloudsList){
            c.setStudentsList(bag.extractStudents(studentsToExtract));
        }
    }

    public void playerThrowsAssistant(int playerId, int thrownAssistantIndex){
        Assistant thrownAssistant = playersList.get(playerId).getAssistantDeck().getAssistantsList().get(thrownAssistantIndex);
        int currAssistantValue = thrownAssistant.getValue();
        int i=0;
        List<Assistant> currlist= playersList.get(playerId).getAssistantDeck().getAssistantsList();
        if(currlist.isEmpty()) return ; //game finished call endgame?
        currlist.remove(thrownAssistant); //linked to the player's deck
        playersList.get(playerId).setSelectedAssistant(thrownAssistant);
        if (playersOrder.isEmpty() ) {
            playersOrder.add(playersList.get(playerId));
            return;
        }
        while (playersOrder.get(i).getSelectedAssistant().getValue()>=currAssistantValue){
            i++;
        }
        playersOrder.add(i, playersList.get(playerId));
    }



    public void endGame (Player winner){
        ingameState = false;
        finishedState = true;
    }

    public void extractCharacters(){
        List<Character> newlist = Character.getAllCharacters();
        Collections.shuffle(newlist);
        charactersList.add(newlist.get(0));
        charactersList.add(newlist.get(1));
        charactersList.add(newlist.get(2));
    }



}

 /* TO PUT IN A SUPERIOR CLASS
    public void addPlayer(String name){
        Player p= new Player(name);
        this.playersList.add(p); //add in uml
    } */

