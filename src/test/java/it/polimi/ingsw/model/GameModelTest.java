package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel game1;
    GameModel game2;
    Player p1;
    Player p2;
    Player p3;
    Player p4;

    @BeforeEach
    void setUp() {
        game1 = new GameModel(4,true);
        game2 = new GameModel(3,false);
        p1 = new Player("Giorginho",1,1, TowerColor.WHITE);
        p2 = new Player("Ronaldinho",2,1,TowerColor.WHITE);
        p3 = new Player("Messi",3,2,TowerColor.BLACK);
        p4 = new Player("Ronaldo",4,2,TowerColor.BLACK);
        game1.start();
        game2.start();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPlayer() {
        game1.addPlayer(p1);
        game1.addPlayer(p2);
        game1.addPlayer(p3);
        game1.addPlayer(p4);
        assertEquals(p1,game1.getPlayerById(1));
        assertEquals(p4,game1.getPlayersList().get(3));
    }

    @Test
    void start() {

    }

    @Test
    void chooseStartingPlayer() {
    }

    @Test
    void getPlayerById() {
    }

    @Test
    void getIslandByIndex() {
    }

    @Test
    void getCloudByIndex() {
    }

    @Test
    void getCharacterByIndex() {
    }

    @Test
    void checkWin() {
    }
}