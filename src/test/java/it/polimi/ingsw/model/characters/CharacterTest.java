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
    void isUsed() {
        character.setUsed();
        assertEquals(true, character.isUsed());

    }

    @Test
    void getId() {
        assertEquals(2, character.getId());
    }

    @Test
    void getInitialCost() {
        assertEquals(2, character.getInitialCost());
    }

    @Test
    void extractCharacters() {
        charactersList = character.extractCharacters();
        assertEquals(3, charactersList.size());
        assertNotEquals(charactersList.get(0), charactersList.get(1));


    }
}