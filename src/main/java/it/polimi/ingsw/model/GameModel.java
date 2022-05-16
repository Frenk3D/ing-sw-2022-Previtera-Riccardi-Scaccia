package it.polimi.ingsw.model;
/*Like "main" connected to Round : Game supervisor with data and Round operator with actions (Controller) (Model the others classes, view to do);
many warnings on unused things because yet to be implemented or other else.
? :  operator ok but better if
exceptions a bit from view, but also here
passing simple parameters in the network, objects will be copied
we use a lot of concatenated methods, asked, not a problem for Cugola, passing the var Game is not good
client has a easier model

CONSEGNE:
UN PACKAGE PER CLIENT E UNO PER SERVER E QUI DENTRO PACKAGE MODEL-CONTROLLER E PACKAGE RETE(VIEW?), fare peer review

*/

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observable;

import java.util.*;
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

    public boolean init(){
        if(playersList.size()!=numOfPlayers){
            return false;
        }

        Collections.addAll(wizardList, Wizard.values());
        Collections.addAll(chooseTowerColorList, TowerColor.values());

        state=GameState.SETTING_STATE;
        if(numOfPlayers == 4){
            settingState = SettingState.CHOOSE_TEAM_STATE;
            sendAvailableTeamPlayers();
            sendSettingState();

        }
        else {
            settingState = SettingState.CHOOSE_TOWER_COLOR_STATE;
            sendAvailableTowerColors();
            sendSettingState();
        }
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
        sendInitGame();
        sendInGameState();
        return true;
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
            return true;
        }
        return false;
    }

    public void setExpertMode(boolean expertMode){
        this.expertMode=expertMode;
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
        if(settingState != SettingState.NOT_SETTING_STATE){
            sendSettingState();
        }
    }

    public SettingState getSettingState() {
        return settingState;
    }

    public List<Island> getIslandsList() {
        return islandsList;
    }

    //-------------------------------------------------------------------MESSAGES MANAGEMENT---------------------------------------------------------------
    public void sendTable(){
        notifyObserver(new TableMessage(SERVERID,getReducedIslandsList(),getReducedCloudsList(),motherNaturePos));
    }

    public void sendAvailableTeamPlayers(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TEAM_SEND, SERVERID, getPlayersWithoutTeamMap(), null, null));
    }

    public void sendAvailableTowerColors(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TOWER_SEND, SERVERID, null, chooseTowerColorList, null));
    }

    public void sendAvailableWizards(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_WIZARD_SEND, SERVERID, null, null, wizardList));
    }

    public void sendSettingState(){
        notifyObserver(new SyncStateMessage(SERVERID,state,settingState));
    }

    public void sendInGameState(){
        if(currRound.getStage() == RoundState.PLANNING_STATE){
            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),currRound.getPlanningPhasePlayer(playersList).getId()));
        }
        else {
            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),getCurrPlayer().getId()));
        }

    }

    public void sendInitGame(){
        if(expertMode){
            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, tableMoney.get(), getReducedCharacterList()));
        }
        else {
            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, -1, null));
        }
    }

    public void sendSelectedAssistant(){
        Player currPlayer = currRound.getPlanningPhasePlayer(playersList);
        notifyObserver(new ThrownAssistantMessage(SERVERID,new ReducedAssistant(currPlayer.getSelectedAssistant()),currPlayer.getId()));
    }

    public void sendDashboard(){
        notifyObserver(new DashboardMessage(SERVERID, new ReducedDashboard(getCurrPlayer().getDashboard()),getCurrPlayer().getId()));
    }

    public void sendCharacterTable(){
        notifyObserver(new CharacterTableMessage(SERVERID,tableMoney.get(),getReducedCharacterList(),getNumOfMoneyMap()));
    }

    //----------------------------------------------------------------REDUCED LIST GENERATORS-------------------------------------------------------------------------
    public List<ReducedIsland> getReducedIslandsList(){
        List<ReducedIsland> reducedIslands = new ArrayList<>();
        for(Island i : islandsList){
            reducedIslands.add(new ReducedIsland(i));
        }
        return reducedIslands;
    }

    public List<ReducedCloud> getReducedCloudsList(){
        List<ReducedCloud> reducedClouds = new ArrayList<>();
        for(Cloud c : cloudsList){
            reducedClouds.add(new ReducedCloud(c));
        }
        return reducedClouds;
    }

    public List<ReducedCharacter> getReducedCharacterList(){
        List<ReducedCharacter> reducedCharacters = new ArrayList<>();
        for(Character c : charactersList){
            reducedCharacters.add(new ReducedCharacter(c));
        }
        return reducedCharacters;
    }

    public List<ReducedPlayer> getReducedPlayersList(){
        List<ReducedPlayer> reducedPlayers = new ArrayList<>();
        for(Player p : playersList){
            reducedPlayers.add(new ReducedPlayer(p));
        }
        return reducedPlayers;
    }

    public Map<String, Integer> getPlayersWithoutTeamMap(){
        Map<String, Integer> result = new HashMap<>();
        for (Player p : playersList){ //check if all player choose team player
            if(p.getTeam()==-1){
                result.put(p.getName(),p.getId());
            }
        }
        return result;
    }

    public Map<Integer,Integer> getNumOfMoneyMap(){
        Map<Integer, Integer> result = new HashMap<>();
        for (Player p : playersList){
            result.put(p.getId(), p.getMoney());
        }
        return result;
    }

    //-------------------------------------------------------------------for test purposes-----------------------------------------------------------------------------
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
    public void setState(GameState state) {
        this.state = state;
    }
}
