package it.polimi.ingsw.model;
/*Like "main" connected to Round : Game supervisor with data and Round operator with actions (Controller) (Model the others classes, view to do);
many warnings on unused things because yet to be implemented or other else.
? :  operator ok but better if
exceptions a bit from view, but also here
we use a lot of concatenated methods, asked, not a problem for Cugola, passing the var Game is not good

CONSEGNE:
UN PACKAGE PER CLIENT E UNO PER SERVER E QUI DENTRO PACKAGE MODEL-CONTROLLER E PACKAGE RETE(VIEW?),
METTERE ECCEZIONI, FARE I TEST,
PASSARE TIPI SEMPLICI AL POSTO DI OGGETTI IN TUTTI I METODI (INT, BOOLEAN E STRING OK, COME CON METODO playerThrowsAssistant DELLA CLASSE GAME), 
        SE PASSO OGGETTI PASSO COPIA E NON PUNTATORE, POSSO AVERE PROBLEMI.
FINIRE E FARE DESCRIZIONE NOSTRO UML PER IL GRUPPO GC57 E FARE REVIEW UML PER IL GRUPPO GC12 (INVIARE TUTTO PER EMAIL REFERENTE)
*/

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List; ////Intellij advises to remove Student ecc from <> when initializing List (professors don't), try to remove it in Bag


public class GameModel extends Observable {
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
    private Integer tableMoney;

    //constructor
    public GameModel(int numOfPlayers, boolean expertMode){
        this.numOfPlayers = numOfPlayers;
        this.expertMode = expertMode;

        playersList = new ArrayList<>();
        bag = new Bag();
        cloudsList = new ArrayList<>();
        tableProfessorsList = new ArrayList<>();
        state = GameState.SETTING_STATE;
        tableMoney = null;
    }


    public void addPlayer(Player player){
        if(playersList.size()<numOfPlayers) {
            playersList.add(player);
        }
    }

    public void start(){
        if(playersList.size()!=numOfPlayers){
            return;
        }

        //initialization of the game
        initMotherNaturePos();
        islandsList=Island.generateIslandsList();
        Island.initStudentIsland(islandsList,motherNaturePos,bag);

        for (Player p: playersList){
            p.getDashboard().generateTower(numOfPlayers,p.getTowerColor());
        }
        if(expertMode){
            charactersList = Character.extractCharacters();
            tableMoney = 20;
            for (Player p: playersList){
                p.modifyMoney(2,tableMoney);
            }
            CharacterParameters parameters = new CharacterParameters();
            parameters.setBag(bag);

            for(Character c : charactersList){
                c.initCharacter(parameters);
            }
        }

        state = GameState.INGAME_STATE;
    }


    public void setMotherNaturePosition(int pos){
            if(motherNaturePos!=pos) //todo mother nature has to move at least of 1 pos, but we have to manage illegal moves
                motherNaturePos=pos;
            else
                System.out.println("Choose a correct move");
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
        for(Player p : playersList){
            if(p.getId() == playerId){
                return p;
            }
        }
        return null;
    }

    public Island getIslandByIndex(int islandIndex){
                return islandsList.get(islandIndex);
            }




    public Cloud getCloudByIndex(int cloudIndex){

                return cloudsList.get(cloudIndex);
            }

    public Character getCharacterByIndex(int characterIndex){

                return charactersList.get(characterIndex);
            }


    public Player checkWin(){
        return null;
    }

    public Characters3and4and5 getForbidCharacter(){
        for (Character c : charactersList) {
            if (c.getId() == 5) {
                return (Characters3and4and5) c;
            }
        }
        return null;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    public Round getCurrRound(){
        return currRound;
    }

    public List<Player> getPlayersList(){
        return playersList;
    }

    public Player getCurrPlayer(){
        return getCurrRound().getCurrTurn().getCurrPlayer();
    }

    public Integer getTableMoney() {
        return tableMoney;
    }

    public Bag getBag() {
        return bag;
    }
}
