package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.ViewObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * This class implements the game object Round
 * Each round is made by as many turns as the number of players in the game
 *
 *
 */
public class Round {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;
    private RoundState stage;
    private int planningPhasePlayer; //index in the list playersList of Game
    private int numOfAssistantThrows;
    private boolean lastRound = false;

    //constructor

    /**
     * the round state is set to planning state
     * the rest is a default constructor
     */
    public Round() {
        currTurn=new Turn();
        playersOrder=new ArrayList<>();
        numOfAssistantThrows = 0;
        stage = RoundState.PLANNING_STATE;
    }

    //methods

    /**
     * resets the round state to planning state
     */
    public void resetRound(){ //resets round to planning state
        currTurn.setCurrPlayer(null);
        stage = RoundState.PLANNING_STATE;
        numOfAssistantThrows = 0;
    }

    /**
     * fills the clouds with a number of students varying based on the number of players
     * @param cloudsList the list of clouds
     * @param bag the bag
     * @param numOfPlayers the number of players
     */
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

    /**
     * initializes the round and the player's order
     * called by the controller after the planning state is finished
     * @param playersList the list of players
     */
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

    /**
     *
     * @return the next turn in the round
     */
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

    /**
     *
     * @return the next player
     */
    public Player getNextPlayer(){
        if(playersOrder.size()==0){
            return null;
        }
        return playersOrder.remove(0);
    }

    /**
     *
     * @return the current turn
     */
    public Turn getCurrTurn(){
        return currTurn;
    }

    /**
     *
     * @return the round stage
     */
    public RoundState getStage() {
        return stage;
    }

    /**
     *
     * @return the number of assistants thrown
     */
    public int getNumOfAssistantThrows() {
        return numOfAssistantThrows;
    }

    /**
     *
     * @param playersList the list of players
     * @return the planning phase player
     */
    public Player getPlanningPhasePlayer(List<Player> playersList){
        return playersList.get(planningPhasePlayer);
    }

    /**
     * randomly generates a starting player
     * @param playersList the list of players
     */
    public void randomStartingPlayer(List<Player> playersList){ //chooses the first Player at the beginning of the game
        int numOfPlayers = playersList.size();
        int randomInt = (int)(Math.random() * (numOfPlayers));
        planningPhasePlayer = randomInt;
    }

    /**
     * Sets the next planning phase player
     * @param numOfPlayers the number of players
     */
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
