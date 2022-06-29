package it.polimi.ingsw.model.characters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactoryTest {

    Character character;
    Characters2and6and8and9 c2;

    @BeforeEach
    void setUp() {

        c2 = new Characters2and6and8and9(2, 2);
        character = Factory.newCharacter(2);

    }

    @AfterEach
    void tearDown() {
        assertEquals(character.getInitialCost(), c2.getInitialCost());
    }

    @Test
    void newCharacter() {
        assertEquals(character.getId(), c2.getId());

    }
}