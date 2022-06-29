package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Characters1and7and11Test {
    Character c1;
    Character c7;
    Character c11;
    CharacterParameters characterParameters;
    int studentIndex;
    Island island;
    Bag bag;
    Player p;

    @BeforeEach
    void setUp() {
        c1 = Factory.newCharacter(1);
        c7 = Factory.newCharacter(7);
        c11 = Factory.newCharacter(11);
        characterParameters = new CharacterParameters();
        List<Integer> studentsIndexList = new ArrayList<>();
        studentsIndexList.add(0);
        List<Integer> studentsIndexEntranceList = new ArrayList<>();
        studentsIndexEntranceList.add(0);
        island = new Island();
        bag = new Bag();
        p = new Player("Ciccio", 1);
        studentIndex = 0;
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setIsland(island);
        characterParameters.setStudentIndex(studentIndex);
        characterParameters.setStudentsIndexList(studentsIndexList);
        characterParameters.setStudentsIndexEntranceList(studentsIndexEntranceList);
        characterParameters.setPlayer(p);
        p.getDashboard().getEntranceList().add(new Student(PawnColor.GREEN));
        c1.initCharacter(characterParameters);
        c7.initCharacter(characterParameters);
        c11.initCharacter(characterParameters);

    }

    @Test
    void applyEffect() {
        //test for character 1
        c1.applyEffect(characterParameters);
        assertEquals(4, ((Characters1and7and11) c1).getCardStudentsList().size());
        assertEquals(1, island.getStudentsList().size());
        //test for character 7
        Student s = ((Characters1and7and11) c7).getCardStudentsList().get(0);
        c7.applyEffect(characterParameters);
        assertEquals(6, ((Characters1and7and11) c7).getCardStudentsList().size());
        assertEquals(s.getColor(), characterParameters.getPlayer().getDashboard().getEntranceList().get(0).getColor());
        //test for character 11
        Student s2 = ((Characters1and7and11) c11).getCardStudentsList().get(0);
        c11.applyEffect(characterParameters);
        assertEquals(4, ((Characters1and7and11) c11).getCardStudentsList().size());
        assertNotEquals(true, ((Characters1and7and11) c11).getCardStudentsList().get(0) == s2);

    }
}