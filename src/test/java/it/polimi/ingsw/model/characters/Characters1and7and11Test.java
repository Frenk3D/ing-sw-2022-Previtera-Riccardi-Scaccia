package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        island = new Island();
        bag = new Bag();
        p = new Player("CiccioPicuzzo",1);
        studentIndex=0;
        bag.addAllStudents();
        characterParameters.setBag(bag);
        characterParameters.setIsland(island);
        characterParameters.setStudentIndex(studentIndex);
        characterParameters.setPlayer(p);
        c1.initCharacter(characterParameters);
        c7.initCharacter(characterParameters);
        c11.initCharacter(characterParameters);

    }

    @Test
    void applyEffect() {
        c1.applyEffect(characterParameters);
        assertEquals(3,((Characters1and7and11) c1).getCardStudentsList());
        assertEquals(1,island.getStudentsList());
        Student s = ((Characters1and7and11) c7).getCardStudentsList().get(0);
        c7.applyEffect(characterParameters);
        assertEquals(3,((Characters1and7and11) c7).getCardStudentsList().size());
        assertEquals(s,characterParameters.getPlayer().getDashboard().getEntranceList().get(0));
        s = ((Characters1and7and11) c7).getCardStudentsList().get(0);
        c11.applyEffect(characterParameters);
        assertEquals(3,((Characters1and7and11) c7).getCardStudentsList().size());
        assertNotEquals(s,((Characters1and7and11) c7).getCardStudentsList().get(0));

    }
}