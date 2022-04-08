package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.RoundState;

import java.util.ArrayList;
import java.util.List;

public class Round {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;
    private RoundState stage;

    //constructor
    public Round() {
        currTurn=new Turn();
        playersOrder=new ArrayList<>();
        resetRound();
    }

    public void resetRound(){
        stage = RoundState.PLANNING_STATE;
    }

    //methods
    public void initRound(List<Player> playersList,List<Cloud> cloudsList, Bag bag) {
        int numOfPlayers = playersList.size();

        //fill the clouds
        for (Cloud c : cloudsList) {
            c.fillCloud(bag,numOfPlayers);
        }

        //CREATE PLAYERS ORDER LIST FOR THE ROUND
        for(Player p : playersList) {
            Assistant thrownAssistant = p.getSelectedAssistant();
            int currAssistantValue = thrownAssistant.getValue();
            int i = 0;

            if (playersOrder.isEmpty()) {
                playersOrder.add(p);
            }
            else {
                while (i < playersOrder.size() && currAssistantValue >= playersOrder.get(i).getSelectedAssistant().getValue()) {     //if a player has only a card with the same value he can throw it, or else ha can't
                    i++;
                }
                playersOrder.add(i, p);
            }
        }

        nextTurn();
        stage = RoundState.ACTION_STATE;
    }

    public boolean nextTurn(){
        Player nextPlayer = getNextPlayer();

        if(nextPlayer!=null) {
            currTurn.setCurrPlayer(nextPlayer);
            currTurn.initTurn();
            return true;
        }
        else {
            stage=RoundState.END_ROUND;
            System.out.println("The round is ended");
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
}
