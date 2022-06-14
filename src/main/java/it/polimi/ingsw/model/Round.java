package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Round extends Observable {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;
    private RoundState stage;
    private int planningPhasePlayer; //index in the list playersList of Game
    private int numOfAssistantThrows;
    private boolean lastRound = false;

    //constructor
    public Round() {
        currTurn=new Turn();
        playersOrder=new ArrayList<>();
        numOfAssistantThrows = 0;
        stage = RoundState.PLANNING_STATE;
    }

    //methods
    public void resetRound(){ //resets round to planning state
        currTurn.setCurrPlayer(null);
        stage = RoundState.PLANNING_STATE;
        numOfAssistantThrows = 0;
    }

    public void fillClouds(List<Cloud> cloudsList, Bag bag, int numOfPlayers){
        int requiredStudents = 12;
        if(numOfPlayers == 2){
            requiredStudents = 6;
        }
        if (bag.getRemainingStudents()<requiredStudents){
            lastRound=true;
            return;
        }
        else if(bag.getRemainingStudents()==requiredStudents){
            lastRound=true;
        }
        for (Cloud c : cloudsList) {
            c.fillCloud(bag,numOfPlayers);
        }
    }


    public void initRound(List<Player> playersList) { //called by the controller after the planning state is finished
        int i = 0;

        //CREATE PLAYERS ORDER LIST FOR THE ROUND
        for(Player p : playersList) {
            Assistant thrownAssistant = p.getSelectedAssistant();
            int currAssistantValue = thrownAssistant.getValue();

            if (playersOrder.isEmpty()) {
                playersOrder.add(p);
            }
            else {
                while (i < playersOrder.size() && currAssistantValue >= playersOrder.get(i).getSelectedAssistant().getValue()) {     //if a player has only a card with the same value he can throw it, or else he can't
                    i++;
                }
                playersOrder.add(i, p);
                i=0;
            }
        }

        planningPhasePlayer=playersList.indexOf(playersOrder.get(0)); //set the starting planning player for the next round
        stage = RoundState.ACTION_STATE;
        nextTurn();
    }

    public boolean nextTurn(){
        Player nextPlayer = getNextPlayer();

        if(nextPlayer!=null) {
            currTurn.setCurrPlayer(nextPlayer);
            currTurn.initTurn(); //re-initialise the turn for the player setting the state in MOVE_STUDENT_STATE
            return true;
        }
        else {
            resetRound();
            return false;
        }
    }

    public Player getNextPlayer(){
        if(playersOrder.size()==0){
            return null;
        }
        return playersOrder.remove(0);
    }

    public Turn getCurrTurn(){
        return currTurn;
    }

    public RoundState getStage() {
        return stage;
    }

    public int getNumOfAssistantThrows() {
        return numOfAssistantThrows;
    }

    public Player getPlanningPhasePlayer(List<Player> playersList){
        return playersList.get(planningPhasePlayer);
    }

    public void randomStartingPlayer(List<Player> playersList){ //chooses the first Player at the beginning of the game
        int numOfPlayers = playersList.size();
        int randomInt = (int)(Math.random() * (numOfPlayers));
        planningPhasePlayer = randomInt;
    }

    public void setNextPlayerPlanning(int numOfPlayers){ //set the next player that must throw the assistant card
        if(stage == RoundState.PLANNING_STATE && numOfAssistantThrows<numOfPlayers) {
                planningPhasePlayer = (planningPhasePlayer + 1) % numOfPlayers;
                numOfAssistantThrows++;
        }
    }

    //for test purposes
    public void setStage(RoundState stage) {
        this.stage = stage;
    }

    public void setNumOfAssistantThrows(int numOfAssistantThrows) {
        this.numOfAssistantThrows = numOfAssistantThrows;
    }

    public List<Player> getPlayersOrder() {
        return playersOrder;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
}
