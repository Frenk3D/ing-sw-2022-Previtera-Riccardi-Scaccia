package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TowerTest {
    Tower tower;
    Tower tmpTower;

    @BeforeEach
    void setUp() {
        tower = new Tower(TowerColor.WHITE);
        tmpTower = new Tower(TowerColor.BLACK);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getColor() {
        assertNotEquals(tower.getColor(), tmpTower.getColor());
        assertEquals(TowerColor.WHITE, tower.getColor());

    }
}