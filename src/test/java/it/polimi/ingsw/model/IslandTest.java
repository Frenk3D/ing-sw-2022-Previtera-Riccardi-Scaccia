package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.characters.Factory;
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
    Island island2;
    List<Island> islandsList;
    List<Island> tmpIslandsList;
    Bag bag;
    Tower tower;
    Student student;
    GameModel game1;
    Player p1,p2,p3,p4;
    Character c3 = Factory.newCharacter(3);


    @BeforeEach
    void setUp() {
        island = new Island();
        island2 = new Island();
        islandsList = new ArrayList<>();
        tmpIslandsList = new ArrayList<>();
        bag = new Bag();
        tower = new Tower(TowerColor.WHITE);
        student = new Student(PawnColor.RED);
        game1 = new GameModel();
        game1.setNumOfPlayers(4);
        game1.setExpertMode(true);
        p1 = new Player("Jorginho",1);
        p1.setPlayerTowerColor(TowerColor.WHITE);
        p1.setTeam(1);
        p2 = new Player("Ronaldinho",2);
        p2.setPlayerTowerColor(TowerColor.WHITE);
        p2.setTeam(1);
        p3 = new Player("Messi",3);
        p3.setPlayerTowerColor(TowerColor.BLACK);
        p3.setTeam(2);
        p4 = new Player("Ronaldo",4);
        p4.setPlayerTowerColor(TowerColor.BLACK);
        p4.setTeam(2);
        game1.addPlayer(p1);
        game1.addPlayer(p2);
        game1.addPlayer(p3);
        game1.addPlayer(p4);
        game1.start();


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
        bag.addAllStudents();
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
        assertEquals(island.getTowersList().get(0), tower);
    }

    @Test
    void addStudent() {
        island.addStudent(student);
        assertEquals(island.getStudentsList().get(0),student);
    }

    @Test
    void updateIslandDomainExpert() {
        p1.getDashboard().getProfessorsList().add(new Professor(PawnColor.RED));
        p3.getDashboard().getProfessorsList().add(new Professor(PawnColor.GREEN));
        p1.getDashboard().generateTower(4,TowerColor.WHITE);
        p3.getDashboard().generateTower(4,TowerColor.BLACK);
        p2.setHasTower(false);
        p4.setHasTower(false);
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game1.getIslandByIndex(0).addStudent(new Student(PawnColor.GREEN));
        //game1.getIslandByIndex(0).updateIslandDomain(game1.getPlayersList());
        game1.getIslandByIndex(0).updateIslandDomainExpert(game1.getPlayersList(), (Characters3and4and5) c3);
        assertEquals(TowerColor.WHITE,game1.getIslandByIndex(0).getTowerColor());

        game1.getIslandByIndex(1).setWeight(3);
        assertEquals(3, game1.getIslandByIndex(1).getWeight());



    }

    @Test
    void mergeIsland() {
    island.setTower(new Tower(TowerColor.GRAY));
    island2.setTower( new Tower(TowerColor.GRAY));
    island.mergeIsland(island2);
    assertEquals(2, island.getWeight());
    assertEquals(2, island.getTowersList().size());

    }
}