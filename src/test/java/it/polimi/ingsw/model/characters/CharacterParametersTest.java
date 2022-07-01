package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Professor;
import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Here there are tests of only getters and setters
class CharacterParametersTest {
    Island island;
    CharacterParameters characterParameters;
    Player player;
    List<Player> playersList;
    PawnColor selectedColor;
    PawnColor selectedColor2;
    List<Integer> studentsIndexList;
    Character forbidCharacter;
    int studentIndex;
    List<Integer> studentIndexEntranceList;
    Bag bag;
    List<Professor> tableProfessorsList;

    @BeforeEach
    void setUp() {
        island = new Island();
        player = new Player("Nico", 1);
        characterParameters = new CharacterParameters();
        playersList = new ArrayList<>();
        selectedColor = PawnColor.RED;
        selectedColor2 = PawnColor.BLUE;
        studentsIndexList = new ArrayList<>();
        studentIndex = 0;
        studentIndexEntranceList = new ArrayList<>();
        bag = new Bag();
        tableProfessorsList = new ArrayList<>();
    }

    @Test
    void getIsland() {
        characterParameters.setIsland(island);
        assertEquals(island, characterParameters.getIsland());

    }

    @Test
    void getPlayer() {
        characterParameters.setPlayer(player);
        assertEquals(player, characterParameters.getPlayer());
    }

    @Test
    void getPlayersList() {
        characterParameters.setPlayersList(playersList);
        assertEquals(playersList, characterParameters.getPlayersList());

    }

    @Test
    void getSelectedColor() {
        characterParameters.setSelectedColor(PawnColor.RED);
        assertEquals(PawnColor.RED, characterParameters.getSelectedColor());
    }

    @Test
    void getSelectedColor2() {
        characterParameters.setSelectedColor2(PawnColor.BLUE);
        assertEquals(PawnColor.BLUE, characterParameters.getSelectedColor2());
    }

    @Test
    void getStudentsIndexList() {
        characterParameters.setStudentsIndexList(studentsIndexList);
        assertEquals(studentsIndexList, characterParameters.getStudentsIndexList());
    }

    @Test
    void getForbidCharacter() {
        characterParameters.setForbidCharacter((Characters3and4and5) forbidCharacter);
        assertEquals(forbidCharacter, characterParameters.getForbidCharacter());
    }

    @Test
    void getStudentIndex() {
        characterParameters.setStudentIndex(2);
        assertEquals(2, characterParameters.getStudentIndex());

    }

    @Test
    void getStudentsIndexEntranceList() {
        characterParameters.setStudentsIndexEntranceList(studentIndexEntranceList);
        assertEquals(studentIndexEntranceList, characterParameters.getStudentsIndexEntranceList());
    }

    @Test
    void getBag() {
        characterParameters.setBag(bag);
        assertEquals(bag, characterParameters.getBag());
    }

    @Test
    void getTableProfessorsList() {
        characterParameters.setTableProfessorsList(tableProfessorsList);
        assertEquals(tableProfessorsList, characterParameters.getTableProfessorsList());
    }
}