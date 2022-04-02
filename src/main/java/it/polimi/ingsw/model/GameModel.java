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
        //islandsList = new ArrayList<Island>();
        state = GameState.SETTING_STATE;
        initMotherNaturePos();
        islandsList=Island.generateIslandsList();
        for (Player p: playersList){
            p.getDashboard().generateTower(numOfPlayers,p.getTowerColor());
        }

        if(expertMode){
            extractCharacters();
        }
    }


    public void start(){
        state = GameState.INGAME_STATE;
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
        return playersList;
    }

}