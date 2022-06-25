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

    public void setIslandList(List<ReducedIsland> islandList) {
        this.islandList = islandList;
    }

    public void setAssistantList(List<ReducedAssistant> assistantList) {
        this.assistantList = assistantList;
    }

    public void setCloudList(List<ReducedCloud> cloudList) {
        this.cloudList = cloudList;
    }

    public void setPlayersList(List<ReducedPlayer> playersList) {
        this.playersList = playersList;
        numOfPlayers = playersList.size();
    }

    public void setTableMoney(int tableMoney) {
        this.tableMoney = tableMoney;
    }

    public void setMyPlayerId(int myPlayerId) {
        this.myPlayerId = myPlayerId;
    }

    public void setCharactersList(List<ReducedCharacter> charactersList) {
        this.charactersList = charactersList;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public void setMotherNaturePos(int motherNaturePos){
        this.motherNaturePos = motherNaturePos;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    public List<ReducedIsland> getIslandList() {
        return islandList;
    }

    public List<ReducedAssistant> getAssistantList() {
        return assistantList;
    }

    public List<ReducedCloud> getCloudList() {
        return cloudList;
    }

    public List<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    public int getTableMoney() {
        return tableMoney;
    }

    public int getMyPlayerId() {
        return myPlayerId;
    }

    public List<ReducedCharacter> getCharactersList() {
        return charactersList;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public Map<String, Integer> getAvailableTeamPlayers() {
        return availableTeamPlayers;
    }

    public void setAvailableTeamPlayers(Map<String, Integer> availableTeamPlayers) {
        this.availableTeamPlayers = availableTeamPlayers;
    }

    public List<TowerColor> getAvailableTowerColors() {
        return availableTowerColors;
    }

    public void setAvailableTowerColors(List<TowerColor> availableTowerColors) {
        this.availableTowerColors = availableTowerColors;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public void setAvailableWizards(List<Wizard> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

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
    public void sendServerInfoRequest()   {
        notifyObserver(obs -> obs.onAskServerInfo());
    }

    public void sendLoginRequest(){
        notifyObserver(obs -> obs.onSendLoginRequest());

    }

    public void askCreateOrJoin(){
        notifyObserver(obs -> obs.onAskCreateOrJoin());
    }

   public void sendChooseLobby(List<Lobby> lobbylist){
       notifyObserver(obs -> obs.onSendChooseLobby(lobbylist));
   }

   public void sendChooseTeam(Map<String,Integer> availablePlayers){
       notifyObserver(obs -> obs.onSendChooseTeam(availablePlayers));
   }

    public void sendChooseTowerColor(List<TowerColor> availableTowerColors){
       notifyObserver(obs -> obs.onSendChooseTowerColor(availableTowerColors));
    }

    public void sendChooseWizard(List<Wizard> availableWizards){
        notifyObserver(obs -> obs.onSendChooseWizard(availableWizards));
    }

    public void sendSelectAssistant(){
       notifyObserver(obs -> obs.onSendSelectAssistant());
    }

    public void askWhereToMoveStudent(){
       notifyObserver(obs -> obs.onAskWhereToMoveStudent());
    }

    public void sendMoveMotherNature(){
       notifyObserver(obs -> obs.onSendMoveMotherNature());
   }

   public void sendChooseCloud(){
       notifyObserver(obs -> obs.onSendChooseCloud());
   }

   public void askCharacterParameters(int characterId){
       notifyObserver(obs -> obs.onAskCharacterParameters(characterId));
   }

    //only show methods
    public void show(Object toShow){  //it is a generic function to print or show some info on view, Object can be everything also null
        notifyObserver(obs -> obs.onShow(toShow));
    }

    public void showPlayerJoin(List<String> playersList){
        notifyObserver(obs -> obs.onShowPlayerJoin(playersList));
    }

    public void showGame(){  //it is a generic function to print or show some info on view, Object can be everything also null
        notifyObserver(obs -> obs.onShowGame(this));
    }

    public void showChosenTeam(String toShow){notifyObserver(obs -> obs.onShowChosenTeam(toShow));}

    public RoundState getRoundState() {
        return roundState;
    }

    public void setRoundState(RoundState roundState) {
        this.roundState = roundState;
    }

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


