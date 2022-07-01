package it.polimi.ingsw.model.characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CharacterTest {
    Character character;
    List<Character> charactersList;
    List<Character> tmpCharactersList;


    @BeforeEach
    void setUp() {
        character = Factory.newCharacter(2);
        tmpCharactersList = new ArrayList<>();
    }

    @Test
    void isUsed() { //return if a char is used or not
        character.setUsed();
        assertEquals(true, character.isUsed());

    }

    @Test //if return the correct id
    void getId() {
        assertEquals(2, character.getId());
    }

    @Test //if return the correct initial cost
    void getInitialCost() {
        assertEquals(2, character.getInitialCost());
    }

    @Test
    void extractCharacters() { //test if extract correctly
        charactersList = character.extractCharacters();
        assertEquals(3, charactersList.size());
        assertNotEquals(charactersList.get(0), charactersList.get(1));


    }
}