package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Professor;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains all the different parameters used by each character. It is used in the server side.
 */
public class CharacterParameters implements Serializable {
    private Island island;
    private Player cardPlayer;
    private List<Player> playersList;
    private List<Integer> studentsIndexList;
    private List<Integer> studentsIndexEntranceList;
    private List<Professor> tableProfessorsList;

    private int studentIndex;
    private PawnColor selectedColor;
    private PawnColor selectedColor2; //only for the character 10
    private Characters3and4and5 forbidCharacter;
    private Bag bag;
    private AtomicInteger tableMoney; //only for the char 10 and 11

    /**
     * studentIndex is set to -1
     * default constructor
     */
    public CharacterParameters() {
        island = null;
        cardPlayer = null;
        playersList = null;
        selectedColor = null;
        studentsIndexList = null;
        studentsIndexEntranceList = null;
        tableProfessorsList = null; //di default
        forbidCharacter = null;
        bag = null;
        studentIndex = -1;
        tableMoney = null;

    }

    /**
     * @return an island
     */
    public Island getIsland() {
        return island;
    }

    /**
     * Sets an island
     *
     * @param island the island
     */
    public void setIsland(Island island) {
        this.island = island;
    }

    /**
     * @return the card player
     */
    public Player getPlayer() {
        return cardPlayer;
    }

    /**
     * Sets a player
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.cardPlayer = player;
    }

    /**
     * @return the players list
     */
    public List<Player> getPlayersList() {
        return playersList;
    }

    /**
     * Sets the players list
     *
     * @param playersList the playerslist
     */
    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    /**
     * @return the selected color
     */
    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    /**
     * Sets the selected color
     *
     * @param selectedColor the selected color
     */
    public void setSelectedColor(PawnColor selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * @return the second selected color
     */
    public PawnColor getSelectedColor2() {
        return selectedColor2;
    }

    /**
     * Sets the second selected color
     *
     * @param selectedColor2 the second selected color
     */
    public void setSelectedColor2(PawnColor selectedColor2) {
        this.selectedColor2 = selectedColor2;
    }

    /**
     * @return the list of students' indexes
     */
    public List<Integer> getStudentsIndexList() {
        return studentsIndexList;
    }

    /**
     * Sets the list of student indexes
     *
     * @param studentsIndexList the list of student indexes
     */
    public void setStudentsIndexList(List<Integer> studentsIndexList) {
        this.studentsIndexList = studentsIndexList;
    }

    /**
     * @return the forbid character
     */
    public Characters3and4and5 getForbidCharacter() {
        return forbidCharacter;
    }

    /**
     * Sets the forbid character
     *
     * @param forbidCharacter the forbid character
     */
    public void setForbidCharacter(Characters3and4and5 forbidCharacter) {
        this.forbidCharacter = forbidCharacter;
    }

    /**
     * @return the student index
     */
    public int getStudentIndex() {
        return studentIndex;
    }

    /**
     * Sets the student index
     *
     * @param studentIndex the student index
     */
    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    /**
     * @return the list of the entrance list students' indexes
     */
    public List<Integer> getStudentsIndexEntranceList() {
        return studentsIndexEntranceList;
    }

    /**
     * Sets the list of the entrance list students' indexes
     *
     * @param studentsIndexEntranceList the list of the entrance list students' indexes
     */
    public void setStudentsIndexEntranceList(List<Integer> studentsIndexEntranceList) {
        this.studentsIndexEntranceList = studentsIndexEntranceList;
    }

    /**
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Sets the bag
     *
     * @param bag the bag
     */
    public void setBag(Bag bag) {
        this.bag = bag;
    }

    /**
     * @return the list of professors on the table
     */
    public List<Professor> getTableProfessorsList() {
        return tableProfessorsList;
    }

    /**
     * Sets the list of professors in the table
     *
     * @param tableProfessorsList the list of professors in the table
     */
    public void setTableProfessorsList(List<Professor> tableProfessorsList) {
        this.tableProfessorsList = tableProfessorsList;
    }

    /**
     * @return the variable table money
     */
    public AtomicInteger getTableMoney() {
        return tableMoney;
    }

    /**
     * @param tableMoney money of the table
     */
    public void setTableMoney(AtomicInteger tableMoney) {
        this.tableMoney = tableMoney;
    }
}
