package it.polimi.ingsw.controller; //well connected to Game

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;

public class Controller {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not

    //constructor
    public Controller(GameModel game){
        this.game=game;
    }

    //methods
    public void moveStudentIsland(int entryListIndex,int islandIndex){

    }

    public void moveStudentDashboard(int entryListIndex){

    }
    public void moveMotherNature(int islandIndex){

    }
    public void takeFromCloud(int cloudIndex){

    }

    public void selectAssistant(int playerId,int assistantId){

    }
    public boolean useCharacter(int characterIndex){
        return false;
    }

}
