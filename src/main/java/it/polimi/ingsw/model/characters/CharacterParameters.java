package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.List;

public class CharacterParameters {
    private Island island;
    private Player cardPlayer;
    private List<Player> playersList;
    private List<Integer> studentsIndexList;
    private List<Integer> studentsIndexEntranceList;
    private int studentIndex;
    private PawnColor selectedColor;
    private PawnColor selectedColor2;
    private Characters3and4and5 forbidCharacter;
    private Bag bag;

    public CharacterParameters() {
        island = null;
        cardPlayer = null;
        playersList = null;
        selectedColor = null;
        studentsIndexList = null;
        studentsIndexEntranceList = null;
        forbidCharacter = null;
        bag = null;
        studentIndex = -1;
    }

    public Island getIsland() {
        return island;
    }

    public Player getPlayer() {
        return cardPlayer;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    public PawnColor getSelectedColor2() { return selectedColor2;}

    public void setIsland(Island island) {
        this.island = island;
    }

    public void setPlayer(Player player) {
        this.cardPlayer = player;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public void setSelectedColor(PawnColor selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setSelectedColor2(PawnColor selectedColor2) {
        this.selectedColor2 = selectedColor2;
    }

    public List<Integer> getStudentsIndexList() {
        return studentsIndexList;
    }

    public void setStudentsIndexList(List<Integer> studentsIndexList) {
        this.studentsIndexList = studentsIndexList;
    }

    public Characters3and4and5 getForbidCharacter() {
        return forbidCharacter;
    }

    public void setForbidCharacter(Characters3and4and5 forbidCharacter) {
        this.forbidCharacter = forbidCharacter;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public List<Integer> getStudentsIndexEntranceList() {
        return studentsIndexEntranceList;
    }

    public void setStudentsIndexEntranceList(List<Integer> studentsIndexEntranceList) {
        this.studentsIndexEntranceList = studentsIndexEntranceList;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}
