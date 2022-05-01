package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Characters2and6and8and9Test {
    Character c2;
    Character c6;
    Character c8;
    Character c9;
    Character c5;
    CharacterParameters characterParameters;
    Island island;
    Bag bag;
    Player p;
    Player p1;
    GameModel game;
    List<Professor> tmpProfessorList;

    @BeforeEach
    void setUp() {
        c2 = Factory.newCharacter(2);
        c6 = Factory.newCharacter(6);
        c8 = Factory.newCharacter(8);
        c9 = Factory.newCharacter(9);
        c5 = Factory.newCharacter(5);
        characterParameters = new CharacterParameters();
        island = new Island();
        bag = new Bag();
        p = new Player("Ciccio", 1);
        p.setTeam(1);
        p.setPlayerTowerColor(TowerColor.WHITE);
        p1= new Player("Carmelo", 2);
        p1.setTeam(2);
        p1.setPlayerTowerColor(TowerColor.BLACK);
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setIsland(island);
        characterParameters.setPlayer(p);
        p.getDashboard().getEntranceList().add(new Student(PawnColor.GREEN));
        c2.initCharacter(characterParameters);
        c6.initCharacter(characterParameters);
        c8.initCharacter(characterParameters);
        c9.initCharacter(characterParameters);
        game= new GameModel();
        game.setNumOfPlayers(2);
        game.setExpertMode(true);
        game.init();
        game.addPlayer(p);
        game.addPlayer(p1);
        game.start();

    }



    @RepeatedTest(20)
    void updateIslandDomainCharacter() {
        p.getDashboard().getProfessorsList().add(new Professor(PawnColor.RED));
        p1.getDashboard().getProfessorsList().add(new Professor(PawnColor.GREEN));
        p.getDashboard().generateTower(2,TowerColor.WHITE);
        p1.getDashboard().generateTower(2,TowerColor.BLACK);
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.RED));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.GREEN));
        game.getIslandByIndex(0).addStudent(new Student(PawnColor.GREEN));
        game.getIslandByIndex(0).addTower(p1.getDashboard().getTowersList().get(0));
        game.getIslandByIndex(0).setWeight(6);
        game.getIslandByIndex(0).updateIslandDomainExpert(game.getPlayersList(),null);
        assertEquals(TowerColor.BLACK,game.getIslandByIndex(0).getTowerColor());

        //test for character 6
        ((Characters2and6and8and9) c6).updateIslandDomainCharacter(game.getCurrPlayer(),game.getIslandByIndex(0),game.getPlayersList(),game.getForbidCharacter());
        assertEquals(TowerColor.WHITE,game.getIslandByIndex(0).getTowerColor());

        //test for character 8
        game.getIslandByIndex(0).updateIslandDomainExpert(game.getPlayersList(),null);
        game.getIslandByIndex(0).setWeight(1);
        ((Characters2and6and8and9) c8).updateIslandDomainCharacter(p1,game.getIslandByIndex(0),game.getPlayersList(),game.getForbidCharacter());
        assertEquals(TowerColor.WHITE,game.getIslandByIndex(0).getTowerColor());

        //test for character 9
        characterParameters.setSelectedColor(PawnColor.RED);
        c9.applyEffect(characterParameters);
        ((Characters2and6and8and9) c9).updateIslandDomainCharacter(game.getCurrPlayer(),game.getIslandByIndex(0),game.getPlayersList(),game.getForbidCharacter());
        assertEquals(TowerColor.BLACK,game.getIslandByIndex(0).getTowerColor());




    }


    @Test
    void applyEffect() {
        game.getPlayersList().get(0).getDashboard().addStudentHall(new Student(PawnColor.RED),game.getPlayersList().get(0),null);
        game.getPlayersList().get(1).getDashboard().addStudentHall(new Student(PawnColor.GREEN),game.getPlayersList().get(0),null);
        tmpProfessorList = new ArrayList<>(game.getTableProfessorsList());
        assertEquals(tmpProfessorList,game.getTableProfessorsList());
        characterParameters.setPlayersList(game.getPlayersList());
        characterParameters.setTableProfessorsList(game.getTableProfessorsList());
        c2.applyEffect(characterParameters);
        //System.out.println("\n secondo test ");
        assertNotEquals(tmpProfessorList,game.getTableProfessorsList());
        assertEquals(3, game.getTableProfessorsList().size());
        assertEquals(PawnColor.GREEN, p1.getDashboard().getProfessorByColor(PawnColor.GREEN).getColor());
        game.getPlayersList().get(0).getDashboard().addStudentHall(new Student(PawnColor.GREEN),game.getPlayersList().get(0),null);
        //test for character 2
        c2.applyEffect(characterParameters); //the check of the modified use is in the controller
        assertEquals(PawnColor.GREEN, p.getDashboard().getProfessorByColor(PawnColor.GREEN).getColor());


    //other tests are in updateIslandDomainCharacter
        assertEquals(true, c6.applyEffect(characterParameters));
        assertEquals(true, c8.applyEffect(characterParameters));
        assertEquals(true, c9.applyEffect(characterParameters));


    }


}