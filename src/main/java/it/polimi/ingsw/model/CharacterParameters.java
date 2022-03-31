package it.polimi.ingsw.model;

import java.util.List;

public class CharacterParameters {
    private Island island;
    private Player player;
    private List<Player> playersList;
    private List<Integer> studentsIndexList;
    private PawnColor selectedColor;
    private Characters3and4and5 forbidCharacter;

    public CharacterParameters() {
        island = null;
        player = null;
        playersList = null;
        selectedColor = null;
        studentsIndexList = null;
        forbidCharacter = null;
    }

    public Island getIsland() {
        return island;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public PawnColor getSelectedColor() {
        return selectedColor;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public void setSelectedColor(PawnColor selectedColor) {
        this.selectedColor = selectedColor;
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
}
