package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Factory;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.TurnState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TurnTest {
    Turn turn = new Turn();
    Character c1 = Factory.newCharacter(3);
    GameModel game1;
    List<Professor> tmpProfessorList;
    Tower t1;
    Tower t2;


    @BeforeEach
    void setUp() {
        turn.initTurn();
        game1 = new GameModel();
        game1.setNumOfPlayers(2);
        game1.addPlayer(new Player("Giorgio", 0));
        game1.addPlayer(new Player("Gigio", 1));
        t1 = new Tower(TowerColor.WHITE);
        t2 = new Tower(TowerColor.WHITE);

    }

    @Test
    void getUsedCharacter() {
        turn.setUsedCharacter(c1);
        assertEquals(c1, turn.getUsedCharacter());
    }

    @Test
    void setStage() {
        turn.setStage(TurnState.CHOOSE_CLOUD_STATE);
        assertEquals(TurnState.CHOOSE_CLOUD_STATE, turn.getStage());
    }


    @Test
    void updateProfessorsLists() {
        game1.start();
        game1.getPlayersList().get(0).getDashboard().addStudentHall(new Student(PawnColor.RED), game1.getPlayersList().get(0), null);
        game1.getPlayersList().get(1).getDashboard().addStudentHall(new Student(PawnColor.GREEN), game1.getPlayersList().get(0), null);
        tmpProfessorList = new ArrayList<>(game1.getTableProfessorsList());
        assertEquals(tmpProfessorList, game1.getTableProfessorsList());
        turn.updateProfessorsLists(game1.getPlayersList(), game1.getTableProfessorsList());
        assertNotEquals(tmpProfessorList, game1.getTableProfessorsList());
        assertEquals(3, game1.getTableProfessorsList().size());

    }


    @Test
    void updateIslandList() {
        game1.init();
        game1.start();
        game1.getIslandByIndex(0).addTower(t1);
        game1.getIslandByIndex(1).addTower(t2);
        assertEquals(12, game1.getIslandsList().size());
        game1.getIslandByIndex(0).mergeIsland(game1.getIslandByIndex(0 + 1));
        game1.getCurrRound().getCurrTurn().updateIslandList(game1.getIslandsList());
        assertEquals(11, game1.getIslandsList().size());

    }

    @Test
    void incrementMovedStudents() {
        int num = game1.getCurrRound().getCurrTurn().getMovedStudentsNumber();
        game1.getCurrRound().getCurrTurn().incrementMovedStudents(2);
        assertEquals(num + 1, game1.getCurrRound().getCurrTurn().getMovedStudentsNumber());
        game1.getCurrRound().getCurrTurn().incrementMovedStudents(2);
        game1.getCurrRound().getCurrTurn().incrementMovedStudents(2);
        assertEquals(TurnState.MOVE_MOTHER_NATURE_STATE, game1.getCurrRound().getCurrTurn().getStage());
    }


}
