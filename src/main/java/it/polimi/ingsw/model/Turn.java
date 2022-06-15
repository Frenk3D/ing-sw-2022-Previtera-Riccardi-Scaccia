package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TurnState;

import java.util.List;

public class Turn {
    //attributes
    private TurnState stage;
    private int movedStudentNumber;
    private Player currPlayer;
    private Character usedCharacter;

    public Turn() {
        currPlayer = null;
    }

    //methods
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Character getUsedCharacter() {
        return usedCharacter;
    }

    public void setStage(TurnState stage) {
        this.stage = stage;
    }

    public TurnState getStage() {
        return stage;
    }

    public void setCurrPlayer(Player name) {
        this.currPlayer = name;
    }

    public void initTurn(){
        usedCharacter=null;
        movedStudentNumber=0;
        stage=TurnState.MOVE_STUDENT_STATE;
    }

    public int updateProfessorsLists(List<Player> playersList, List<Professor> tableProfessorsList){
        for (PawnColor currColor : PawnColor.values()){ //scan of all colors
            Player tmpPlayer = null; //temp variable to store the player that has to receive the professor
            Player currentProfessorPlayer = null; //temp variable to store the player that hold the professor

            for(Player p : playersList) { //we have to check if someone have to receive the currColorProfessor
                if (p.getDashboard().getHallStudentsListByColor(currColor).size() > 0){
                    tmpPlayer = p;
                    break;
                }
            }
            if (tmpPlayer!=null) {
                for (Player p : playersList) { //for each color we check every player dashboard
                    if (p.getDashboard().getProfessorByColor(currColor) != null) { //when we find the player which already hold the professor we store in the temp variable
                        currentProfessorPlayer = p;
                    }
                    //search the player with more students of that color
                    if (p.getDashboard().getHallStudentsListByColor(currColor).size() > tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()) { //if we find a player that has more students than tmp player we update the variable
                        tmpPlayer = p;
                    } else if (p.getDashboard().getHallStudentsListByColor(currColor).size() == tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()) { //if the players have the same number of student the professor must remain to the old holder
                        if (p.getDashboard().getProfessorByColor(currColor) != null) {
                            tmpPlayer = p;
                        }
                    }
                }

                if (tmpPlayer.getDashboard().getProfessorByColor(currColor) == null) { //if the player that should have the professor doesn t have it we must give it
                    Professor professorToMove = null;

                    for (Professor tableProfessor : tableProfessorsList) { //check if the professor is on the table and remove it if it is found
                        if (tableProfessor.getColor().equals(currColor)) {
                            professorToMove = tableProfessor;
                            tableProfessorsList.remove(tableProfessor);
                            break;
                        }
                    }

                    if (professorToMove == null) { //the professor is in currentProfessorPlayer, else professor is on the table
                        professorToMove = currentProfessorPlayer.getDashboard().getProfessorByColor(currColor);
                        currentProfessorPlayer.getDashboard().getProfessorsList().remove(professorToMove);
                        tmpPlayer.getDashboard().getProfessorsList().add(professorToMove);
                        return currentProfessorPlayer.getId();
                    }


                    tmpPlayer.getDashboard().getProfessorsList().add(professorToMove);
                }
            }

        }
        return -1;
    }

    public void returnProfessorsToTable(List<Player> playersList, List<Professor> tableProfessorsList){
        for (PawnColor currColor : PawnColor.values()) {
            for (Player p : playersList){
                Professor currProfessor = p.getDashboard().getProfessorByColor(currColor);
                if(currProfessor!=null && p.getDashboard().getNumOfHallStudents(currColor)==0){
                    p.getDashboard().getProfessorsList().remove(currProfessor);
                    tableProfessorsList.add(currProfessor);
                }
            }

        }
    }

    public int updateIslandList(List<Island> islandsList){
        int updatedMotherNature = -1;
        boolean merged = false;
        for (int i = 0; i<islandsList.size(); i++){
            if(i==islandsList.size()-1){ //we are at the last island of the list
                if(islandsList.get(i).mergeIsland(islandsList.get(0))){
                    islandsList.remove(0);
                    updatedMotherNature = i;
                    merged = true;
                    break;
                }
            }
            else { //we are in the middle of the list
                if(islandsList.get(i).mergeIsland(islandsList.get(i+1))){
                    islandsList.remove(i+1);
                    updatedMotherNature = i;
                    merged = true;
                    break;
                }
            }
        }
        if (!merged){
            return updatedMotherNature;
        }
        else {updateIslandList(islandsList); //when it will be false (after almost one true) the recursion will return -1, but it will be not assigned and it will return the real updateMotherNaturePos to the controller
            return updatedMotherNature;
        }

    }








    public boolean incrementMovedStudents(int numOfPlayers){
        movedStudentNumber++;
        if((movedStudentNumber==3 && numOfPlayers != 3) || (movedStudentNumber==4 && numOfPlayers == 3)){
            stage=TurnState.MOVE_MOTHER_NATURE_STATE;
            return false;
        }
        return true;
    }

    public int getMovedStudentsNumber() {
        return movedStudentNumber;
    }

    public void setUsedCharacter(Character usedCharacter) {
        this.usedCharacter = usedCharacter;
    }


}
