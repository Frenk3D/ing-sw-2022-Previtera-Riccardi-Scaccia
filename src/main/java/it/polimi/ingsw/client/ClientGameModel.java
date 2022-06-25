package it.polimi.ingsw.client;


import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.*;

import java.util.List;
import java.util.Map;

/**
 * It is a reduced {@link it.polimi.ingsw.model.GameModel}
 * It extends {@link ModelObservable}
 * @see it.polimi.ingsw.observer.ModelObservable
 */
public class ClientGameModel extends ModelObservable {
    private Map<String,Integer> availableTeamPlayers;
    private List<TowerColor> availableTowerColors;
    private List<Wizard> availableWizards;

    private List<ReducedIsland> islandList;
    private List<ReducedAssistant> assistantList;
    private List<ReducedCloud> cloudList;
    private List<ReducedPlayer> playersList;
    private List<ReducedCharacter> charactersList;
    private int motherNaturePos;
    private int tableMoney;
    private int myPlayerId;
    private boolean expertMode;
    private int numOfPlayers;
    private RoundState roundState;

    //we use the default constructor just to use the view in ClientController

    /**
     * this constructor gets the game model lists through the use of the allGameMessage
     * @param allGameMessage allGameMessage is network message that contains all the info about the game model
     */
    public void initClientGameModel(AllGameMessage allGameMessage){
        islandList= allGameMessage.getIslandsList();
        assistantList = allGameMessage.getAssistantsList();
        cloudList = allGameMessage.getCloudsList();
        playersList = allGameMessage.getPlayersList();
        charactersList = allGameMessage.getCharactersList();
        notifyObserver(obs -> obs.updateClientGameModel(this));
        tableMoney = allGameMessage.getTableMoney();
        expertMode = allGameMessage.isExpertMode();
        numOfPlayers = allGameMessage.getPlayersList().size();
        motherNaturePos = allGameMessage.getMotherNaturePos();

    }

    /**
     * Sets the list of islands for the client
     * @param islandList islandList is the list of Islands of the game model
     */
    public void setIslandList(List<ReducedIsland> islandList) {
        this.islandList = islandList;
    }

    /**
     * Sets the list of assistants for the client
     * @param assistantList assistantLIst is the list of Assistants of the game model
     */
    public void setAssistantList(List<ReducedAssistant> assistantList) {
        this.assistantList = assistantList;
    }

    /**
     * Sets the list of clouds for the client
     * @param cloudList cloudList is the list of clouds of the game model
     */
    public void setCloudList(List<ReducedCloud> cloudList) {
        this.cloudList = cloudList;
    }

    /**
     * Sets the list of players for the client
     * @param playersList is the list of players of the game model
     */
    public void setPlayersList(List<ReducedPlayer> playersList) {
        this.playersList = playersList;
        numOfPlayers = playersList.size();
    }

    /**
     * Sets the money on the table
     * @param tableMoney tableMoney is the money of the game model
     */
    public void setTableMoney(int tableMoney) {
        this.tableMoney = tableMoney;
    }

    /**
     * Sets the player id
     * @param myPlayerId myPlayerId is the id of the client player
     */
    public void setMyPlayerId(int myPlayerId) {
        this.myPlayerId = myPlayerId;
    }

    /**
     * Sets the characters list for the client
     * @param charactersList charactersList is the list of characters taken from the game model
     */
    public void setCharactersList(List<ReducedCharacter> charactersList) {
        this.charactersList = charactersList;
    }

    /**
     * Sets expert mode
     * @param expertMode expertMode is the game mode
     */
    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    /**
     * Sets the position of mother nature for the client
     * @param motherNaturePos motherNaturePos is mother nature's position
     */
    public void setMotherNaturePos(int motherNaturePos){
        this.motherNaturePos = motherNaturePos;
    }

    /**
     *
     * @return the position of mother nature
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    /**
     *
     * @return the list of reduced islands
     */
    public List<ReducedIsland> getIslandList() {
        return islandList;
    }

    /**
     *
     * @return the list of reduced assistants
     */
    public List<ReducedAssistant> getAssistantList() {
        return assistantList;
    }

    /**
     *
     * @return the list of reduced clouds
     */
    public List<ReducedCloud> getCloudList() {
        return cloudList;
    }

    /**
     *
     * @return the list of reduced players
     */
    public List<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    /**
     *
     * @return the money on the table
     */
    public int getTableMoney() {
        return tableMoney;
    }

    /**
     *
     * @return your player id
     */
    public int getMyPlayerId() {
        return myPlayerId;
    }

    /**
     *
     * @return the reduced list of characters
     */
    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    /**
     *
     * @return true if the game is in expert mode
     * @return false if the game is in normal mode
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     *
     * @return a map of available team players' names and ids
     */
    public Map<String, Integer> getAvailableTeamPlayers() {
        return availableTeamPlayers;
    }

    /**
     * sets the map of available team players
     * @param availableTeamPlayers availableTeamPlayers is a map of the players' names and ids
     */
    public void setAvailableTeamPlayers(Map<String, Integer> availableTeamPlayers) {
        this.availableTeamPlayers = availableTeamPlayers;
    }

    /**
     *
     * @return the list of available tower colors
     */
    public List<TowerColor> getAvailableTowerColors() {
        return availableTowerColors;
    }

    /**
     * sets the list of available tower colors
     * @param availableTowerColors availableTowerColors is the list of available tower colors
     */
    public void setAvailableTowerColors(List<TowerColor> availableTowerColors) {
        this.availableTowerColors = availableTowerColors;
    }

    /**
     *
     * @return the list of available wizards
     */
    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    /**
     * Sets the list of available wizards
     * @param availableWizards availableWizards is the list of available wizards
     */
    public void setAvailableWizards(List<Wizard> availableWizards) {
        this.availableWizards = availableWizards;
    }

    /**
     *
     * @return the number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Sets the number of players
     * @param numOfPlayers
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public ReducedPlayer findPlayerById(int id){
        for(ReducedPlayer rp : playersList) {
            if (rp.getId() == id) {
                return rp;
            }
        }
        return null;
    }

    //NOW THERE ARE METHODS THAT CONTROLLER CALLS WHEN HE RECEIVE A MESSAGE FROM THE SOCKET

    /**
     *Sends a request to obtain needed server info
     */
    public void sendServerInfoRequest()   {
        notifyObserver(obs -> obs.onAskServerInfo());
    }

    /**
     * sends a request to login
     */
    public void sendLoginRequest(){
        notifyObserver(obs -> obs.onSendLoginRequest());

    }

    /**
     * Asks if the player wants to create a new lobby or join an existing one
     */
    public void askCreateOrJoin(){
        notifyObserver(obs -> obs.onAskCreateOrJoin());
    }

    /**
     * Sends a request to choose from the available lobbies
     * @param lobbylist
     */
   public void sendChooseLobby(List<Lobby> lobbylist){
       notifyObserver(obs -> obs.onSendChooseLobby(lobbylist));
   }

    /**
     * sends a request to choose from the available teams
     * @param availablePlayers availablePlayers is the map of the available players
     */
   public void sendChooseTeam(Map<String,Integer> availablePlayers){
       notifyObserver(obs -> obs.onSendChooseTeam(availablePlayers));
   }

    /**
     * sends a request to choose from the available tower colors
     * @param availableTowerColors availableTowerColors is the list of the available tower colors
     */
    public void sendChooseTowerColor(List<TowerColor> availableTowerColors){
       notifyObserver(obs -> obs.onSendChooseTowerColor(availableTowerColors));
    }

    /**
     * sends a request to choose from the available wizards
     * @param availableWizards availableWizards is the list of the available wizards
     */
    public void sendChooseWizard(List<Wizard> availableWizards){
        notifyObserver(obs -> obs.onSendChooseWizard(availableWizards));
    }

    /**
     * sends a request to choose from the available assistants
     */
    public void sendSelectAssistant(){
       notifyObserver(obs -> obs.onSendSelectAssistant());
    }

    /**
     * sends a request to select the desired movement for the student
     */
    public void askWhereToMoveStudent(){
       notifyObserver(obs -> obs.onAskWhereToMoveStudent());
    }

    /**
     * sends a request to move mother nature
     */
    public void sendMoveMotherNature(){
       notifyObserver(obs -> obs.onSendMoveMotherNature());
   }
    /**
     * sends a request to choose from the available clouds
     */
   public void sendChooseCloud(){
       notifyObserver(obs -> obs.onSendChooseCloud());
   }

    /**
     * Asks the selected character parameters
     * @param characterId
     */
   public void askCharacterParameters(int characterId){
       notifyObserver(obs -> obs.onAskCharacterParameters(characterId));
   }

    //only show methods

    /**
     * shows the desired object
     * @param toShow toShow is the object to be shown
     */
    public void show(Object toShow){  //it is a generic function to print or show some info on view, Object can be everything also null
        notifyObserver(obs -> obs.onShow(toShow));
    }

    /**
     * show the list of players in the lobby
     * @param playersList list of players in the lobby
     */
    public void showPlayerJoin(List<String> playersList){
        notifyObserver(obs -> obs.onShowPlayerJoin(playersList));
    }

    /**
     * generic function to print or show some info on view, Object can be everything also null
     */
    public void showGame(){  //it is a generic function to print or show some info on view, Object can be everything also null
        notifyObserver(obs -> obs.onShowGame(this));
    }

    /**
     * Shows the chosen team
     * @param toShow toShow is a string that indicates the chosen team
     */
    public void showChosenTeam(String toShow){notifyObserver(obs -> obs.onShowChosenTeam(toShow));}

    /**
     *
     * @return the round state
     */
    public RoundState getRoundState() {
        return roundState;
    }

    /**
     * Sets the round state
     * @param roundState
     */
    public void setRoundState(RoundState roundState) {
        this.roundState = roundState;
    }

    /**
     * Resets the client game model
     */
    public void reset(){
        availableTeamPlayers = null;
        availableTowerColors = null;
        availableWizards = null;
        islandList = null;
        assistantList = null;
        cloudList = null;
        playersList = null;
        charactersList = null;
        motherNaturePos = -1;
        tableMoney = 0;
        expertMode = false;
        numOfPlayers = 0;
        roundState = null;
    }
}


