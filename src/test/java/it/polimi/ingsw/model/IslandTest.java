package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    Island island;
    List<Island> islandsList;
    List<Island> tmpIslandsList;
    Bag bag;
    Tower tower;
    Student student;


    @BeforeEach
    void setUp() {
        island = new Island();
        islandsList = new ArrayList<>();
        tmpIslandsList = new ArrayList<>();
        bag = new Bag();
        tower = new Tower(TowerColor.WHITE);
        student = new Student(PawnColor.RED);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initStudentIsland() {
        islandsList = Island.generateIslandsList();
        bag.addAllStudents();
        Island.initStudentIsland(islandsList,3,bag);
        assertEquals(islandsList.get(3).getStudentsList().isEmpty(),true);
        assertEquals(islandsList.get(3+6).getStudentsList().isEmpty(),true);
        assertNotEquals(islandsList.get(4).getStudentsList().isEmpty(),true);
    }

    @Test
    void generateIslandsList() {
        islandsList = Island.generateIslandsList();
        tmpIslandsList = Island.generateIslandsList();
        assertEquals(islandsList.size(),12);
        assertEquals(islandsList,tmpIslandsList);
        Island.initStudentIsland(islandsList,3,bag);
        Island.initStudentIsland(tmpIslandsList,3,bag);
        assertNotEquals(islandsList,tmpIslandsList);

    }

    @Test
    void addTower() {
        island.addTower(tower);
        assertEquals(island.getTower(), tower);
    }

    @Test
    void addStudent() {
        island.addStudent(student);
        assertEquals(island.getStudentsList().get(0),student);
    }

    @Test
    void updateIslandDomain() {
        // TODO: 08/04/2022  
    }

    @Test
    void updateIslandDomainExpert() {
    }

    @Test
    void mergeIsland() {
    }
}