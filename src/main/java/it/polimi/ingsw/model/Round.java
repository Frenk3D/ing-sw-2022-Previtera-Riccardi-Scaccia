package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Round {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;

    //constructor
    public Round() {
        currTurn=new Turn();
        playersOrder=new ArrayList<>();
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
                return;
            }
            while (playersOrder.get(i).getSelectedAssistant().getValue() >= currAssistantValue) {     //if a player has only a card with the same value he can throw it, or else ha can't
                i++;
            }
            playersOrder.add(i, p);
        }

    }

    public Player getNextPlayer(){
        return playersOrder.remove(0);
    }

    public Turn getCurrTurn(){
        return currTurn;
    }

}
