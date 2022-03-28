package it.polimi.ingsw.model;

import java.util.List;

public class Round {
    //attributes
    private Turn currTurn;
    private List<Player> playersOrder;

    //constructor
    public Round(Turn currTurn, List<Player> playersOrder) {
        this.currTurn = currTurn;
        this.playersOrder = playersOrder;
    }
    //methods
    public void initRound(List<Player> playersList,List<Cloud> cloudsList) {
        int numOfPlayers = playersList.size();
        Bag bag = Bag.getInstance();

        int studentsToExtract = 3;
        if (numOfPlayers == 3) studentsToExtract = 4;

        for (Cloud c : cloudsList) {
            c.setStudentsList(bag.extractStudents(studentsToExtract));
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
            while (playersOrder.get(i).getSelectedAssistant().getValue() >= currAssistantValue) {
                i++;
            }
            playersOrder.add(i, p);
        }

    }

    public Player getNextPlayer(){
        return null;
    }

    public Turn getCurrTurn(){
        return currTurn;
    }

}
