package it.polimi.ingsw.controller; //well connected to Game, need to Observ, with strategy or with checks

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TurnState;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer {
    //attributes
    private GameModel game;  //intellij says it should be final,but it actually changes so it's not
    private InputVerifier verifier;

    //constructor
    public Controller(GameModel game){
        this.game=game;
        verifier = new InputVerifier(game);
    }

    //methods
    public void moveStudentIsland(int entranceListIndex,int islandIndex){

        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_STUDENT_STATE) {
            Player currPlayer = game.getCurrPlayer();
            Student studentToMove = currPlayer.getDashboard().getEntranceList().get(entranceListIndex);
            game.getIslandByIndex(islandIndex).addStudent(studentToMove);
            currPlayer.getDashboard().getEntranceList().remove(studentToMove);
        }
        else {
            System.out.println("forbidden move");
        }
    }

    public void moveStudentDashboard(int entranceListIndex){
        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_STUDENT_STATE){
            Player currPlayer = game.getCurrPlayer();
            Student studentToMove = currPlayer.getDashboard().getEntranceList().get(entranceListIndex);
            currPlayer.getDashboard().addStudentHall(studentToMove,currPlayer,game.getTableMoney());
            currPlayer.getDashboard().getEntranceList().remove(studentToMove);
        }
        else {
            System.out.println("forbidden move");
        }
    }

    public void moveMotherNature(int islandIndex){
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage()== TurnState.MOVE_MOTHER_NATURE_STATE){
            game.setMotherNaturePosition(islandIndex);
        }
        else {
            System.out.println("forbidden move");
        }
    }

    public void takeFromCloud(int cloudIndex){ //they go in the entranceList
        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE && game.getCurrRound().getCurrTurn().getStage() == TurnState.CHOOSE_CLOUD_STATE){
            if(game.getCloudByIndex(cloudIndex).getStudents().size()>0){
                for (Student s : game.getCloudByIndex(cloudIndex).getStudents()){
                    game.getCurrPlayer().getDashboard().getEntranceList().add(s);
                    game.getCloudByIndex(cloudIndex).getStudents().remove(s);
                }
            }
        }
        else {
            System.out.println("forbidden move");
        }

    }

    public void selectAssistant(int playerId,int assistantId){
        game.getPlayerById(playerId).setSelectedAssistant(assistantId);
        game.getPlayerById(playerId).getAssistantDeck().removeAssistantById(assistantId);
    }

    public void useCharacter(int characterIndex){ // TODO: 08/04/2022
        Character usedCharacter = game.getCharacterByIndex(characterIndex);
        int characterCost = usedCharacter.getNumOfUse()+usedCharacter.getInitialCost();
        if (game.getCurrPlayer().getMoney() >= characterCost){
            game.getCurrPlayer().modifyMoney(-characterCost,game.getTableMoney());
            game.getCurrRound().getCurrTurn().setUsedCharacter(game.getCharacterByIndex(characterIndex));
            usedCharacter.setNumOfUse(usedCharacter.getNumOfUse()+1);
        }
    }

    public int nextTurn(){
        game.getCurrRound().getCurrTurn().getCurrPlayer();
        return 0;
    }

    @Override
    public void update(Message message) {

    }
}
