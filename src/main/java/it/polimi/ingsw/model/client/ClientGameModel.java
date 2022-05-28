package it.polimi.ingsw.model.client;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static it.polimi.ingsw.network.server.Server.SERVERID;

public class ClientGameModel extends ModelObservable {
    private List<ReducedIsland> islandList;
    private List<ReducedAssistant> assistantList;
    private List<ReducedCloud> cloudList;
    private List<ReducedPlayer> playersList;
    private List<ReducedCharacter> charactersList;
    private int tableMoney;
    private int myPlayerId;
    private boolean expertMode;
    private int numOfPlayers;

    //we use the default constructor just to use the view in ClientController

    public void initClientGameModel(AllGameMessage allGameMessage){
        islandList= allGameMessage.getIslandsList();
        assistantList = allGameMessage.getAssistantsList();
        cloudList = allGameMessage.getCloudsList();
        playersList = allGameMessage.getPlayersList();
        charactersList = allGameMessage.getCharactersList();
        tableMoney = allGameMessage.getTableMoney();
        expertMode = allGameMessage.isExpertMode();
        numOfPlayers = allGameMessage.getPlayersList().size();
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



   /*public void sendNewLobbyRequest(){
        notifyObserver(obs -> obs.onSendNewLobbyRequest());

    }
    public void sendLobbiesRequest(){
        notifyObserver(obs -> obs.onSendLobbiesRequest());
    }*/

   public void sendChooseLobby(List<Lobby> lobbylist){
       notifyObserver(obs -> obs.onSendChooseLobby(lobbylist));
   }

   public void sendChooseTeam(Map<String,Integer> availablePlayers){
       //notifyObserver(obs -> obs.onSendChooseTeam(availablePlayers));
   }

   public void sendChooseWizard(List<Wizard> availableWizards){
       //notifyObserver(obs -> obs.onSendChooseWizard(availableWizards));
   }

    public void sendChooseTowerColor(List<TowerColor> availableTowerColors){
        //notifyObserver(obs -> obs.onSendChooseTowerColor(availableTowerColors));
    }

//    public void sendPlayerJoin(){
//        notifyObserver(new StringMessage(MessageType.PLAYER_JOIN,SERVERID,playersList.get(playersList.size()-1).getName()));
//    }
//
//    public void sendTable(){
//        notifyObserver(new TableMessage(SERVERID,getReducedIslandsList(),getReducedCloudsList(),motherNaturePos));
//    }
//
//    public void sendAvailableTeamPlayers(){
//        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TEAM_SEND, SERVERID, getPlayersWithoutTeamMap(), null, null));
//    }
//
//    public void sendAvailableTowerColors(){
//        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_TOWER_SEND, SERVERID, null, chooseTowerColorList, null));
//    }
//
//    public void sendAvailableWizards(){
//        notifyObserver(new SyncInitMessage(MessageType.AVAILABLE_WIZARD_SEND, SERVERID, null, null, wizardList));
//    }
//
//    public void sendSettingState(){
//        notifyObserver(new SyncStateMessage(SERVERID,state,settingState));
//    }
//
//    public void sendInGameState(){
//        if(currRound.getStage() == RoundState.PLANNING_STATE){
//            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),currRound.getPlanningPhasePlayer(playersList).getId()));
//        }
//        else {
//            notifyObserver(new SyncStateMessage(SERVERID,state,currRound.getStage(),currRound.getCurrTurn().getStage(),getCurrPlayer().getId()));
//        }
//
//    }
//
//    public void sendInitGame(){
//        if(expertMode){
//            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, tableMoney.get(), getReducedCharacterList()));
//        }
//        else {
//            notifyObserver(new AllGameMessage(SERVERID,getReducedPlayersList(), expertMode, getReducedIslandsList(),getReducedCloudsList(),playersList.get(0).getAssistantDeck().getReducedAssistantsList(), motherNaturePos, -1, null));
//        }
//    }
//
//    public void sendSelectedAssistant(){
//        Player currPlayer = currRound.getPlanningPhasePlayer(playersList);
//        notifyObserver(new ThrownAssistantMessage(SERVERID,new ReducedAssistant(currPlayer.getSelectedAssistant()),currPlayer.getId()));
//    }
//
//    public void sendDashboard(){
//        notifyObserver(new DashboardMessage(SERVERID, new ReducedDashboard(getCurrPlayer().getDashboard()),getCurrPlayer().getId()));
//    }
//
//    public void sendCharacterTable(){
//        notifyObserver(new CharacterTableMessage(SERVERID,tableMoney.get(),getReducedCharacterList(),getNumOfMoneyMap()));
//    }

    public void show(Object toShow){  //it is a generic function to print or show some info on view, Object can be everything also null
        notifyObserver(obs -> obs.onShow(toShow));
    }

    public void showPlayerJoin(List<String> playersList){
        System.out.println("The players in the lobby are:");
        for(String player: playersList){
            System.out.print("Player:");
            notifyObserver(obs -> obs.onShow(player));
        }
    }
}


