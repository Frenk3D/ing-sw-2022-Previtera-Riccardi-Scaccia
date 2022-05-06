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
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.StringMessage;
import it.polimi.ingsw.network.message.SyncStateMessage;
import it.polimi.ingsw.network.message.TableMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List; ////Intellij advises to remove Student ecc from <> when initializing List (professors don't), try to remove it in Bag
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class GameModel extends Observable {
    //attributes
    private int numOfPlayers;
    private int motherNaturePos;
    private boolean expertMode;
    private List<Cloud> cloudsList;
    private List<Wizard> wizardList;
    private List<TowerColor> chooseTowerColorList;
    private Bag bag;
    private final List<Player> playersList;
    private List<Character> charactersList;
    private List<Professor> tableProfessorsList;
    private Round currRound;
    private GameState state;
    private SettingState settingState;
    private List<Island> islandsList; //doesn't change even when you group islands, attrib in Island class
    private AtomicInteger tableMoney;
    public static final int SERVERID = 9999;

    //constructor
    public GameModel(){
        numOfPlayers = -1;
        expertMode=false;
        currRound=new Round();
        bag = new Bag();
        wizardList = new ArrayList<>();
        chooseTowerColorList = new ArrayList<>();
        playersList = new ArrayList<>();
        cloudsList = new ArrayList<>();
        tableProfessorsList = new ArrayList<>();
        state = GameState.LOGIN_STATE;
        settingState = SettingState.NOT_SETTING_STATE;
        tableMoney = null;
    }

    public boolean setNumOfPlayers(int chosenNumOfPlayers){
        if (chosenNumOfPlayers > 0 && chosenNumOfPlayers <= 4) {
            numOfPlayers = chosenNumOfPlayers;
            return true;
        }
        return false;
    }

    public boolean addPlayer(Player player){
        if(numOfPlayers!=-1 && playersList.size()<numOfPlayers) {
            playersList.add(player);
            notifyObserver(new StringMessage(MessageType.PLAYER_JOIN,SERVERID,player.getName()));
            return true;
        }
        return false;
    }

    public void setExpertMode(boolean expertMode){
        this.expertMode=expertMode;
    }

    public boolean init(){
        if(playersList.size()!=numOfPlayers){
            return false;
        }

        Collections.addAll(wizardList, Wizard.values());
        Collections.addAll(chooseTowerColorList, TowerColor.values());

        state=GameState.SETTING_STATE;
        if(numOfPlayers == 4){
            settingState = SettingState.CHOOSE_TEAM_STATE;
        }
        else {
            settingState = SettingState.CHOOSE_TOWER_COLOR_STATE;
        }

        notifyObserver(new SyncStateMessage(SERVERID,state,settingState));
        return true;
    }

    public boolean start(){
        if(playersList.size()!=numOfPlayers){
            return false;
        }
        //initialization of the game
        currRound.randomStartingPlayer(playersList);
        bag.initialBagFill();
        initMotherNaturePos();
        islandsList=Island.generateIslandsList();
        Island.initStudentIsland(islandsList,motherNaturePos,bag);

        bag.addAllStudents();
        cloudsList = Cloud.generateCloudsList(numOfPlayers);
        currRound.fillClouds(cloudsList,bag,numOfPlayers);

        for(PawnColor c : PawnColor.values()){
            Professor p = new Professor(c);
            tableProfessorsList.add(p);
        }

        for (Player p: playersList){
            p.getDashboard().placeStudentEntrance(numOfPlayers,bag);
            p.getDashboard().generateTower(numOfPlayers,p.getTowerColor());
        }

        if(expertMode){
            charactersList = Character.extractCharacters();
            tableMoney = new AtomicInteger();
            tableMoney.set(20);
            for (Player p: playersList){
                p.modifyMoney(2,tableMoney);
            }
            CharacterParameters parameters = new CharacterParameters();
            parameters.setBag(bag);

            for(Character c : charactersList){
                c.initCharacter(parameters);
                //the method getForbidCharacter does everything
            }
        }
        state = GameState.INGAME_STATE;
        notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),currRound.getPlanningPhasePlayer(playersList).getId()));
        return true;
    }


    public void setMotherNaturePosition(int pos){
            if(motherNaturePos!=pos&& pos>0 && pos<12) { //mother nature has to move at least of 1 pos, but we have to manage illegal back moves
                motherNaturePos = pos;
            }
            else{
                System.out.println("Choose a correct move");
            }
    }


    private void initMotherNaturePos(){
        int randomInt = (int)(Math.random() * (12));
        motherNaturePos = randomInt;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
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
        if(islandIndex > islandsList.size() || islandIndex<0){
            return null;
        }
        return islandsList.get(islandIndex);
    }


    public Cloud getCloudByIndex(int cloudIndex){
        if(cloudIndex >= cloudsList.size() || cloudIndex<0){
            return null;
        }
        return cloudsList.get(cloudIndex);
    }

    public Character getCharacterByIndex(int characterIndex){
        if(characterIndex >= charactersList.size() || characterIndex<0){
            return null;
        }
        return charactersList.get(characterIndex);
    }


    public Player checkWin(){
        return null;
        // TODO: 08/04/2022
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

    public AtomicInteger getTableMoney() {
        return tableMoney;
    }

    public Bag getBag() {
        return bag;
    }

    public List<Cloud> getCloudsList() {
        return cloudsList;
    }



    public GameState getGameState(){
        return state;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public List<Professor> getTableProfessorsList() {
        return tableProfessorsList;
    }

    public List<Wizard> getWizardList() {
        return wizardList;
    }

    public List<TowerColor> getChooseTowerColorList() {
        return chooseTowerColorList;
    }

    public void setSettingState(SettingState settingState) {
        this.settingState = settingState;
    }

    public SettingState getSettingState() {
        return settingState;
    }



    //for test purposes
    public List<Island> getIslandsList() {
        return islandsList;
    }

    public void setIslandsList(List<Island> islandsList) {
        this.islandsList = islandsList;
    }

    public void setCloudsList(List<Cloud> cloudsList) {
        this.cloudsList = cloudsList;
    }
    public void setCharactersList(List<Character> charactersList){
        this.charactersList = charactersList;
    }
    public List<Character> getCharactersList(){
        return charactersList;
    }

    public void sendTable(){
        notifyObserver(new TableMessage(SERVERID,islandsList,cloudsList,motherNaturePos));
    }

}
