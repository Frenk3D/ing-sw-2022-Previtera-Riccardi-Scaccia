package it.polimi.ingsw.model;
/*Like "main" connected to Round : Game supervisor with data and Round operator with actions (Controller) (Model the others classes, view to do);
many warnings on unused things because yet to be implemented or other else.
? :  operator ok but better if
exceptions a bit from view, but also here
passing simple parameters in the network, objects will be copied
we use a lot of concatenated methods, asked, not a problem for Cugola, passing the var Game is not good
client has an easier model
Comment with block doesn't work with javadoc

*/

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterParameters;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.network.server.Server.SERVERID;

/**
 * this is the main class of the game
 * it contains the logic of the game itself
 * the structure and all the info of the game is collected here
 *
 * It is an {@link Observable}.
 */
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

    //constructor

    /**
     * number of players set by default to -1
     * expert mode set to false
     * the game state is initially set to LOGIN_STATE
     * the setting state is set to NOT_SETTING_STATE
     * the rest is a default constructor
     */
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

    /**
     *
     * @return
     */
    public boolean init(){
        if(playersList.size()!=numOfPlayers){
            return false;
        }

        Collections.addAll(wizardList, Wizard.values());

        if(numOfPlayers == 3){
            Collections.addAll(chooseTowerColorList, TowerColor.values());
        }
        else {
            chooseTowerColorList.add(TowerColor.BLACK);
            chooseTowerColorList.add(TowerColor.WHITE);
        }

        state=GameState.SETTING_STATE;
        if(numOfPlayers == 4){
            settingState = SettingState.CHOOSE_TEAM_STATE;
            sendSettingState();
            sendAvailableTeamPlayers();
        }
        else {
            settingState = SettingState.CHOOSE_TOWER_COLOR_STATE;
            sendSettingState();
            sendAvailableTowerColors();
        }
        return true;
    }

    /**
     *
     * @return
     */
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
            tableMoney.set(200);
            for (Player p: playersList){
                p.modifyMoney(40,tableMoney);
            }
            CharacterParameters parameters = new CharacterParameters();
            parameters.setBag(bag);

            for(Character c : charactersList){
                c.initCharacter(parameters);
                //the method getForbidCharacter search if there is id 5 otherwise return null
            }
        }
        state = GameState.INGAME_STATE;
        sendInitGame();
        sendInGameState(); //give information about current state and prints the game
        return true;
    }

    /**
     * sets the chosen number of players
     * @param chosenNumOfPlayers
     * @return true if the chosen number is between 0 and 4
     * @return false otherwise
     */
    public boolean setNumOfPlayers(int chosenNumOfPlayers){
        if (chosenNumOfPlayers > 0 && chosenNumOfPlayers <= 4) {
            numOfPlayers = chosenNumOfPlayers;
            return true;
        }
        return false;
    }

    /**
     * adds a player to the player list
     * @param player
     * @return
     */
    public boolean addPlayer(Player player){
        if(numOfPlayers!=-1 && playersList.size()<numOfPlayers) {
            playersList.add(player);
            sendPlayerJoin();
            return true;
        }
        return false;
    }

    /**
     * sets expert mode
     * @param expertMode
     */
    public void setExpertMode(boolean expertMode){
        this.expertMode=expertMode;
    }

    /**
     * sets mother nature position(only if its different from the current position and inferior to 12)
     * @param pos
     */
    public void setMotherNaturePosition(int pos){
            if(motherNaturePos!=pos&& pos>=0 && pos<12) { //mother nature has to move at least of 1 pos, but we have to manage illegal back moves
                motherNaturePos = pos;
            }
    }

    /**
     * random set of the initial position of mother nature
     */
    private void initMotherNaturePos(){
        int randomInt = (int)(Math.random() * (12));
        motherNaturePos = randomInt;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    /**
     *
     * @param playerId
     * @return a player chosen by id
     */
    public Player getPlayerById(int playerId){
        for(Player p : playersList){
            if(p.getId() == playerId){
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @param islandIndex
     * @return an island chosen by index
     */
    public Island getIslandByIndex(int islandIndex){
        if(islandIndex > islandsList.size() || islandIndex<0){
            return null;
        }
        return islandsList.get(islandIndex);
    }

    /**
     *
     * @param cloudIndex
     * @return a cloud chosen by index
     */
    public Cloud getCloudByIndex(int cloudIndex){
        if(cloudIndex >= cloudsList.size() || cloudIndex<0){
            return null;
        }
        return cloudsList.get(cloudIndex);
    }

    /**
     *
     * @param characterIndex
     * @return a character chosen by index
     */
    public Character getCharacterByIndex(int characterIndex) {
        if (characterIndex >= charactersList.size() || characterIndex < 0) {
            return null;
        }
        return charactersList.get(characterIndex);
    }

    /**
     *
     * @param characterId
     * @return a character chosen by id
     */
    public Character getCharacterById(int characterId){
        for (Character c : charactersList){
            if (c.getId() == characterId){
                return c;
            }
        }
        return null;
    }

    /**
     *
     * @return the fifth character (forbid character)
     */
    public Characters3and4and5 getForbidCharacter(){
        for (Character c : charactersList) {
            if (c.getId() == 5) {
                return (Characters3and4and5) c;
            }
        }
        return null;
    }

    /**
     *
     * @return the number of players
     */
    public int getNumOfPlayers(){
        return numOfPlayers;
    }

    /**
     *
     * @return the current round
     */
    public Round getCurrRound(){
        return currRound;
    }

    /**
     *
     * @return the list of players
     */
    public List<Player> getPlayersList(){
        return playersList;
    }

    /**
     *
     * @return the current player
     */
    public Player getCurrPlayer(){
        return getCurrRound().getCurrTurn().getCurrPlayer();
    }

    /**
     *
     * @return the money on the table
     */
    public AtomicInteger getTableMoney() {
        return tableMoney;
    }

    /**
     *
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     *
     * @return the list of clouds
     */
    public List<Cloud> getCloudsList() {
        return cloudsList;
    }

    /**
     *
     * @return the state of the game
     */
    public GameState getGameState(){
        return state;
    }

    /**
     *
     * @return true if the game is in expert mode
     * @return false otherwise
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     *
     * @return the list of professors on the table
     */
    public List<Professor> getTableProfessorsList() {
        return tableProfessorsList;
    }

    /**
     *
     * @return the list of wizards
     */
    public List<Wizard> getWizardList() {
        return wizardList;
    }

    /**
     *
     * @return the list of chosen tower colors
     */
    public List<TowerColor> getChooseTowerColorList() {
        return chooseTowerColorList;
    }

    /**
     * sets the state in settingState
     * @param settingState
     */
    public void setSettingState(SettingState settingState) {
        this.settingState = settingState;
        if(settingState != SettingState.NOT_SETTING_STATE){
            sendSettingState();
        }
    }

    /**
     *
     * @return setting state
     */
    public SettingState getSettingState() {
        return settingState;
    }

    /**
     *
     * @return the list of islands
     */
    public List<Island> getIslandsList() {
        return islandsList;
    }

    //-------------------------------------------------------------------MESSAGES MANAGEMENT---------------------------------------------------------------
    /**
     * the following classes are used for message management,they notify the observer with the needed info
     */

    /**
     * sends the message that notifies that a player has joined
     */
    public void sendPlayerJoin(){
        List<String> result = new ArrayList<>();
        for(Player p : playersList){
            result.add(p.getName());
        }
        notifyObserver(new PlayerJoinMessage(SERVERID,result));
    }

    /**
     * sends the message containing the table
     */
    public void sendTable(){
        notifyObserver(new TableMessage(SERVERID,getReducedIslandsList(),getReducedCloudsList(),motherNaturePos));
    }

    /**
     * sends the message containing the available team players
     */
    public void sendAvailableTeamPlayers(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TEAM_SEND, SERVERID, getPlayersWithoutTeamMap(), null, null));
    }

    /**
     * sends the message containing the available tower colors
     */
    public void sendAvailableTowerColors(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TOWER_SEND, SERVERID, null, chooseTowerColorList, null));
    }

    /**
     * sends the message containing the available wizards
     */
    public void sendAvailableWizards(){
        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_WIZARD_SEND, SERVERID, null, null, wizardList));
    }

    /**
     * sends a sync state message,setting it to setting state
     */
    public void sendSettingState(){
        notifyObserver(new SyncStateMessage(SERVERID,state,settingState));
    }

    /**
     * sends a sync state message,setting it to in game state
     */
    public void sendInGameState(){
        if(currRound.getStage() == RoundState.PLANNING_STATE){
            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),currRound.getPlanningPhasePlayer(playersList).getId()));
        }
        else {
            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),getCurrPlayer().getId()));
        }

    }

    /**
     * sends game initialization message
     */
    public void sendInitGame(){
        if(expertMode){
            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, tableMoney.get(), getReducedCharacterList()));
        }
        else {
            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, -1, null));
        }
    }

    /**
     * sends a message notifying the observer with the selected assistant
     */
    public void sendSelectedAssistant(){
        Player currPlayer = currRound.getPlanningPhasePlayer(playersList);
        notifyObserver(new ThrownAssistantMessage(SERVERID,new ReducedAssistant(currPlayer.getSelectedAssistant()),currPlayer.getId()));
    }

    /**
     * sends the dashboard of the current player
     */
    public void sendDashboard(){
        notifyObserver(new DashboardMessage(SERVERID, new ReducedDashboard(getCurrPlayer().getDashboard()),getCurrPlayer().getId()));
    }

    /**
     * sends the dashboard of a player chosen by id
     * @param playerId
     */
    public void sendDashboard(int playerId){
        notifyObserver(new DashboardMessage(SERVERID, new ReducedDashboard(getPlayerById(playerId).getDashboard()),playerId));
    }

    /**
     * sends the dashboards of all players
     */
    public void sendAllDashboards(){
        for (Player p : playersList){
            notifyObserver(new DashboardMessage(SERVERID, new ReducedDashboard(p.getDashboard()),p.getId()));
        }
    }

    /**
     * sends the list of characters,the table money,and the map number of money
     */
    public void sendCharacterTable(){
        notifyObserver(new CharacterTableMessage(SERVERID,tableMoney.get(),getReducedCharacterList(),getNumOfMoneyMap()));
    }

    /**
     * sends the thrown character chosen by id
     * @param characterId
     */
    public void sendThrownCharacter(int characterId){
        notifyObserver(new ThrownCharacterMessage(SERVERID,characterId,getCurrPlayer().getId()));
    }

    /**
     * sends a message that notifies the end of the game,and the player who won
     * @param winnerId
     */
    public void sendWin(int winnerId){
        StringBuilder names = new StringBuilder();
        if(numOfPlayers==4){
            for (Player p : playersList){
                if (p.getTeam()==winnerId){
                    names.append(" " + p.getName());
                }
            }
            notifyObserver(new StringMessage(MessageType.WIN,SERVERID,false, names.toString()));
        }
        else {notifyObserver(new StringMessage(MessageType.WIN,SERVERID,false,getPlayerById(winnerId).getName()));}
    }

    //----------------------------------------------------------------REDUCED LIST GENERATORS-------------------------------------------------------------------------

    /**
     *
     * @return the list of reduced islands
     */
    public List<ReducedIsland> getReducedIslandsList(){
        List<ReducedIsland> reducedIslands = new ArrayList<>();
        for(Island i : islandsList){
            reducedIslands.add(new ReducedIsland(i));
        }
        return reducedIslands;
    }

    /**
     *
     * @return the list of reduced clouds
     */
    public List<ReducedCloud> getReducedCloudsList(){
        List<ReducedCloud> reducedClouds = new ArrayList<>();
        for(Cloud c : cloudsList){
            reducedClouds.add(new ReducedCloud(c));
        }
        return reducedClouds;
    }

    /**
     *
     * @return the list of reduced characters
     */
    public List<ReducedCharacter> getReducedCharacterList(){
        List<ReducedCharacter> reducedCharacters = new ArrayList<>();
        for(Character c : charactersList){
            reducedCharacters.add(new ReducedCharacter(c));
        }
        return reducedCharacters;
    }

    /**
     *
     * @return the list of reduced players
     */
    public List<ReducedPlayer> getReducedPlayersList(){
        List<ReducedPlayer> reducedPlayers = new ArrayList<>();
        for(Player p : playersList){
            reducedPlayers.add(new ReducedPlayer(p));
        }
        return reducedPlayers;
    }

    /**
     *
     * @return the map containing players without a team
     */
    public Map<String, Integer> getPlayersWithoutTeamMap(){
        Map<String, Integer> result = new HashMap<>();
        for (Player p : playersList){ //check if all player choose team player
            if(p.getTeam()==-1){
                result.put(p.getName(),p.getId());
            }
        }
        return result;
    }

    /**
     *
     * @return the map containing the number of money of each player
     */
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
