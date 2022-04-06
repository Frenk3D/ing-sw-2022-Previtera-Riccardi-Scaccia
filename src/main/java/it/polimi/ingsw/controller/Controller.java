package it.polimi.ingsw.controller; //well connected to Game, need to Observ, with strategy or with checks

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not

    //constructor
    public Controller(GameModel game){
        this.game=game;
    }

    //methods
    public void moveStudentIsland(int entryListIndex,int islandIndex){

    }

    public void moveStudentDashboard(int entranceListIndex){
        Player currPlayer = game.getCurrPlayer();
        Student studentToMove = currPlayer.getDashboard().getEntranceList().get(entranceListIndex);
        currPlayer.getDashboard().addStudentHall(studentToMove,currPlayer,game.getTableMoney());
        currPlayer.getDashboard().getEntranceList().remove(studentToMove);
    }

    public void moveMotherNature(int islandIndex){

    }

    public void takeFromCloud(int cloudIndex){ //they go in the entranceList

    }

    public void selectAssistant(int playerId,int assistantId){

    }
    public void useCharacter(int characterIndex){

    }

    public int nextTurn(){
        game.getCurrRound().getCurrTurn().getCurrPlayer();
        return 0;
    }

    @Override
    public void update(Message message) {

    }
}
