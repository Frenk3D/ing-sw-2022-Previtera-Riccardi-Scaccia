package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Characters10and12Test {
    Character c10;
    Character c12;

    CharacterParameters characterParameters;
    Island island;
    Bag bag;
    Player p;
    Player p1;
    GameModel game;
    ArrayList students;

    @BeforeEach
    void setUp() {
        c10 = Factory.newCharacter(10);
        c12 = Factory.newCharacter(12);
        characterParameters = new CharacterParameters();
        island = new Island();
        bag = new Bag();
        p = new Player("Ciccio", 1);
        p.setTeam(1);
        p.setPlayerTowerColor(TowerColor.WHITE);
        p1= new Player("Carmelo", 2);
        p1.setTeam(2);
        p1.setPlayerTowerColor(TowerColor.GRAY); //ONLY BECAUSE IT'S A TEST, GRAY IS ONLY FOR 3 PLAYERS GAME
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setPlayer(p);
        characterParameters.setSelectedColor(PawnColor.BLUE);
        characterParameters.setSelectedColor2(PawnColor.PINK);
        p.getDashboard().getEntranceList().add(new Student(PawnColor.BLUE));
        c10.initCharacter(characterParameters);
        c12.initCharacter(characterParameters);
        game= new GameModel();
        game.setNumOfPlayers(2);
        game.setExpertMode(true);
        game.init();
        game.addPlayer(p);
        game.addPlayer(p1);
        game.start();
        characterParameters.setIsland(game.getIslandByIndex(0));
        characterParameters.setPlayersList(game.getPlayersList());
        students = new ArrayList<>();
        students.add(1);
        students.add(2);
        characterParameters.setStudentsIndexList(students);
    }

    @RepeatedTest(20)
    void applyEffect() {
        //to test character 10
        /*
        Student tmp1 = p.getDashboard().getEntranceList().get(1);
        Student tmp2 = p.getDashboard().getEntranceList().get(2);
        int oldHallColorListSize1 = p.getDashboard().getHallStudentsListByColor(tmp1.getColor()).size();
        int oldHallColorListSize2 = p.getDashboard().getHallStudentsListByColor(tmp2.getColor()).size();

        c10.applyEffect(characterParameters);
        if(tmp1.getColor()==characterParameters.getSelectedColor() && tmp2.getColor() == characterParameters.getSelectedColor2() ) {
            assertEquals(oldHallColorListSize1, p.getDashboard().getHallStudentsListByColor(tmp1.getColor()).size());
            assertEquals(oldHallColorListSize2, p.getDashboard().getHallStudentsListByColor(tmp2.getColor()).size());

        }
        else if(tmp1.getColor()!=characterParameters.getSelectedColor() || tmp1.getColor() != characterParameters.getSelectedColor2() && tmp1.getColor() != tmp2.getColor() ) {
            assertEquals(oldHallColorListSize1+1, p.getDashboard().getHallStudentsListByColor(tmp1.getColor()).size());
            assertEquals(characterParameters.getSelectedColor2(), p.getDashboard().getEntranceList().get(p.getDashboard().getEntranceList().size()-1).getColor() );
            assertEquals(characterParameters.getSelectedColor(), p.getDashboard().getEntranceList().get(p.getDashboard().getEntranceList().size()-2).getColor() );


        }

        else {} //there are too many cases to tests
        */


        //to test character 12
        p.getDashboard().getHallStudentsListByColor(PawnColor.BLUE).add(new Student(PawnColor.BLUE));
        p.getDashboard().getHallStudentsListByColor(PawnColor.BLUE).add(new Student(PawnColor.BLUE));
        p.getDashboard().getHallStudentsListByColor(PawnColor.BLUE).add(new Student(PawnColor.BLUE));
        p.getDashboard().getHallStudentsListByColor(PawnColor.BLUE).add(new Student(PawnColor.BLUE));
        p1.getDashboard().getHallStudentsListByColor(PawnColor.BLUE).add(new Student(PawnColor.BLUE));
        c12.applyEffect(characterParameters);
        assertEquals(1,p.getDashboard().getHallStudentsListByColor(characterParameters.getSelectedColor()).size() );
        assertEquals(0,p1.getDashboard().getHallStudentsListByColor(characterParameters.getSelectedColor()).size() );



    }




    @Test
    void initCharacter() {
        assertEquals(bag, ((Characters10and12)c12).getBag());
    }
}