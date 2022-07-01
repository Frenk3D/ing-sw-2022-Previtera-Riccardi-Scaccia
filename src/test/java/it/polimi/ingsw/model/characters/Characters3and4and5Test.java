package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Characters3and4and5Test {
    Character c3;
    Character c4;
    Character c5;
    CharacterParameters characterParameters;
    Island island;
    Bag bag;
    Player p;
    Player p1;
    GameModel game;


    @BeforeEach
    void setUp() {

        c3 = Factory.newCharacter(3);
        c4 = Factory.newCharacter(4);
        c5 = Factory.newCharacter(5);
        characterParameters = new CharacterParameters();
        island = new Island();
        bag = new Bag();
        p = new Player("Ciccio", 1);
        p.setTeam(1);
        p.setPlayerTowerColor(TowerColor.WHITE);
        p1 = new Player("Carmelo", 2);
        p1.setTeam(2);
        p1.setPlayerTowerColor(TowerColor.BLACK);
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setPlayer(p);
        p.getDashboard().getEntranceList().add(new Student(PawnColor.GREEN));
        c3.initCharacter(characterParameters);
        c4.initCharacter(characterParameters);
        c5.initCharacter(characterParameters);
        game = new GameModel();
        game.setNumOfPlayers(2);
        game.setExpertMode(true);
        game.init();
        game.addPlayer(p);
        game.addPlayer(p1);
        game.start();
        characterParameters.setIsland(game.getIslandByIndex(0));

    }

    @Test
    void addForbidCard5() { //test if forbid cards are added
        ((Characters3and4and5) c5).addForbidCard5();
        assertEquals(4, ((Characters3and4and5) c5).getForbidCards()); //too many no entry tiles
        ((Characters3and4and5) c5).setForbidCards(3);
        ((Characters3and4and5) c5).addForbidCard5();
        assertEquals(4, ((Characters3and4and5) c5).getForbidCards());


    }

    @Test
    void applyEffect() {
        //test for character 3
        p.getDashboard().getProfessorsList().add(new Professor(PawnColor.RED));
        p1.getDashboard().getProfessorsList().add(new Professor(PawnColor.GREEN));
        p.getDashboard().generateTower(2, TowerColor.WHITE);
        p1.getDashboard().generateTower(2, TowerColor.BLACK);
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.GREEN));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.GREEN));
        game.getIslandByIndex(0).addTower(p1.getDashboard().getTowersList().get(0));
        game.getIslandByIndex(0).setWeight(5);
        c3.applyEffect(characterParameters);
        assertEquals(TowerColor.BLACK, game.getIslandByIndex(0).getTowerColor());

        //test for character 4
        p.setSelectedAssistant(1);
        int old = p.getSelectedAssistant().getMotherNaturePosShift();
        c4.applyEffect(characterParameters);
        assertEquals(old + 2, p.getSelectedAssistant().getMotherNaturePosShift());


        //test for character 5
        c5.applyEffect(characterParameters);
        assertEquals(1, game.getIslandByIndex(0).getForbidCards());


        //validation tests
        //assertNotEquals(false, c3.applyEffect(characterParameters));
        ((Characters3and4and5) c3).setId(20);
        assertEquals(false, c3.applyEffect(characterParameters));
    }


}