package it.polimi.ingsw;
/*Type "main" connected to Round : Game supervisor with data and Round operator with actions (MODEL);
many warnings on unused things because yet to be implemented.
*/

import java.util.ArrayList;
import java.util.List; ////Intellij advises to remove Student ecc from <> when initializing List (professors don't), try to remove it in Bag
import java.util.Collections;

public class Game {
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

    /* TO PUT IN A SUPERIOR CLASS
    public void addPlayer(String name){
        Player p= new Player(name);
        this.playersList.add(p); //add in uml
    } */
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

    public void setMotherNaturePosition(int pos){
        motherNaturePos=pos;

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
    public Player getNextPlayer(){ //NEW FUNCTION(TO BE COMPLETED)!! Returns whose turn it is depending on the assistant chosen
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

    public void endGame (Player winner){
        ingameState = false;
        finishedState = true;
    }
    public int getCurrentPlayer(){
        return currentPlayer;
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



}


