package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
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
    CharacterParameters characterParameters;
    int studentIndex;
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
        characterParameters = new CharacterParameters();
        List<Integer> studentsIndexList = new ArrayList<>();
        studentsIndexList.add(0);
        List<Integer> studentsIndexEntranceList = new ArrayList<>();
        studentsIndexEntranceList.add(0);
        island = new Island();
        bag = new Bag();
        p = new Player("Ciccio", 1);
        p.setTeam(1);
        p.setPlayerTowerColor(TowerColor.BLACK);
        p1= new Player("Carmelo", 2);
        p1.setTeam(2);
        p1.setPlayerTowerColor(TowerColor.WHITE);
        studentIndex = 0;
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setIsland(island);
        characterParameters.setStudentIndex(studentIndex);
        characterParameters.setStudentsIndexList(studentsIndexList);
        characterParameters.setStudentsIndexEntranceList(studentsIndexEntranceList);
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



    @Test
    void updateIslandDomainCharacter() { /*
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
        game1.getIslandByIndex(0).updateIslandDomainExpert(game1.getPlayersList(), (Characters3and4and5) c5);
        assertEquals(TowerColor.WHITE,game1.getIslandByIndex(0).getTowerColor());

        game1.getIslandByIndex(1).setWeight(3);
        assertEquals(3, game1.getIslandByIndex(1).getWeight());
*/
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
        c2.applyEffect(characterParameters); //the check of the modified use is in the controller
        assertEquals(PawnColor.GREEN, p.getDashboard().getProfessorByColor(PawnColor.GREEN).getColor());


    //altri test in updateIslandDomainCharacter
        assertEquals(true, c6.applyEffect(characterParameters));
        assertEquals(true, c8.applyEffect(characterParameters));
        assertEquals(true, c9.applyEffect(characterParameters));


    }


}