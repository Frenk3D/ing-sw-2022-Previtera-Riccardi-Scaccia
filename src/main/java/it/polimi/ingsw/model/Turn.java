package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TurnState;

import java.util.List;

/**
 * implement the game object Turn
 * A turn is the sets of action the a player can make inside a round
 */
public class Turn {
    //attributes
    private TurnState stage;
    private int movedStudentNumber;
    private Player currPlayer;
    private Character usedCharacter;

    /**
     * default constructor
     */
    public Turn() {
        currPlayer = null;
    }

    //methods

    /**
     *
     * @return the current player
     */
    public Player getCurrPlayer() {
        return currPlayer;
    }

    /**
     *
     * @return the used character
     */
    public Character getUsedCharacter() {
        return usedCharacter;
    }

    /**
     * Sets the turn stage
     * @param stage
     */
    public void setStage(TurnState stage) {
        this.stage = stage;
    }

    /**
     *
     * @return the turn stage
     */
    public TurnState getStage() {
        return stage;
    }

    /**
     * Sets the current player
     * @param name
     */
    public void setCurrPlayer(Player name) {
        this.currPlayer = name;
    }

    /**
     * Initializes the turn
     */
    public void initTurn(){
        usedCharacter=null;
        movedStudentNumber=0;
        stage=TurnState.MOVE_STUDENT_STATE;
    }

    /**
     * Updates the list of professors
     * @param playersList
     * @param tableProfessorsList
     * @return
     */
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

    /**
     *
     * @param playersList
     * @param tableProfessorsList
     */
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

    /**
     * Updates the list of islands
     * @param islandsList
     * @return
     */
    public int updateIslandList(List<Island> islandsList){
        int updatedMotherNature = -1;
        boolean merged = false;

        for (int i = 0; i<islandsList.size()-1; i++){
            int j= islandsList.size()-1;
            if(j==islandsList.size()-1){ //we are at the last island of the list
                if(islandsList.get(0).mergeIsland(islandsList.get(j))){ //this is the opposite of the next because of the recursion
                    islandsList.remove(j);
                    updatedMotherNature = 0;
                    merged = true;
                    break;
                }
            }
            //else { //we are in the middle of the list
                if(islandsList.get(i).mergeIsland(islandsList.get(i+1))){
                    islandsList.remove(i+1);
                    updatedMotherNature = i;
                    merged = true;
                    break;
                }
           // }
        }
        if (!merged){
            return updatedMotherNature;
        }
        else {updateIslandList(islandsList); //when it will be false (after almost one true) the recursion will return -1, but it will be not assigned and it will return the real updateMotherNaturePos to the controller
            return updatedMotherNature;
        }

    }


    /**
     * increments the number of moved students
     * @param numOfPlayers
     * @return the incremented number of moved students
     */
    public boolean incrementMovedStudents(int numOfPlayers){
        movedStudentNumber++;
        if((movedStudentNumber==3 && numOfPlayers != 3) || (movedStudentNumber==4 && numOfPlayers == 3)){
            stage=TurnState.MOVE_MOTHER_NATURE_STATE;
            return false;
        }
        return true;
    }

    /**
     *
     * @return the number of moved students
     */
    public int getMovedStudentsNumber() {
        return movedStudentNumber;
    }

    /**
     * Sets the number of used students
     * @param usedCharacter
     */
    public void setUsedCharacter(Character usedCharacter) {
        this.usedCharacter = usedCharacter;
    }


}
