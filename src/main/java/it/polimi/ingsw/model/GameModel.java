package it.polimi.ingsw.model;
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

import it.polimi.ingsw.controller.Controller;

import java.util.ArrayList;
import java.util.List; ////Intellij advises to remove Student ecc from <> when initializing List (professors don't), try to remove it in Bag
import java.util.Collections;

public class GameModel {
    //attributes
    private int currentPlayer;
    private final int numOfPlayers; //numofplayers, playerslist and expertmode final, decided from the start
    private int motherNaturePos;
    private final boolean expertMode;
    private List<Cloud> cloudsList;
    private List<Integer> wizardList;
    private Bag bag;
    private final List<Player> playersList;
    private List<Character> charactersList;
    private List<Professor> tableProfessorsList;
    private Round currRound;
    private GameState state;
    private List<Island> islandsList; //doesn't change even when you group islands, attrib in Island class

    //constructor
    public GameModel(List<Player> playersList, boolean expertMode){
        this.numOfPlayers = playersList.size();
        this.expertMode = expertMode;
        this.playersList = playersList;
        bag = Bag.getInstance();
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


    public void start(){
        settingState = false;
        ingameState = true;
        finishedState = false;

    }


    public void setMotherNaturePosition(int pos){
        motherNaturePos=pos;
    }


    private void initMotherNaturePos(){
        int randomInt = (int)(Math.random() * (13));
        motherNaturePos = randomInt;
    }


    public void chooseStartingPlayer(){ //chooses the first Player at the beginning of the game
        int randomInt = (int)(Math.random() * (numOfPlayers + 1));
        currentPlayer=randomInt;
    }

    public Player getPlayerById(int playerId){
        return null;
    }

    public Island getIslandByIndex(int islandIndex){
        return null;
    }

    public Cloud getCloudByIndex(int cloudIndex){
        return null;
    }

    public Character getCharacterByIndex(int characterIndex){
        return null;
    }

    public Player checkWin(){
        return null;
    }


    public void extractCharacters(){
        List<Character> newlist = Character.getAllCharacters();
        Collections.shuffle(newlist);
        charactersList.add(newlist.get(0));
        charactersList.add(newlist.get(1));
        charactersList.add(newlist.get(2));
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    public Round getCurrRound(){
        return currRound;
    }

    public List<Player> getPlayersList(){
        return null;
    }


    //------------------------------------------------------------

    public void generateProfessorsList(){
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





}

 /* TO PUT IN A SUPERIOR CLASS
    public void addPlayer(String name){
        Player p= new Player(name);
        this.playersList.add(p); //add in uml
    } */

