package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This is a test class for the assistant class
class AssistantTest {
    private Assistant assistant;

    @BeforeEach
    void setUp() { //Setups the required attributes
        assistant = new Assistant(5, 2);
    }

    @Test
    void getValue() { //Tests the assistant's value getter
        assertEquals(5, assistant.getValue());
    }

    @Test
    void getId() { //Tests the assistant id getter
        assertEquals(assistant.getValue(), assistant.getId());
    }

    @Test
    void getMotherNaturePosShift() { //Tests the getter for mother nature's allowed moves
        assertEquals(2, assistant.getMotherNaturePosShift());
    }

    @Test
    void setMotherNaturePosShift() { //Tests the setter for mother nature's allowed moves
        assistant.setMotherNaturePosShift(3);
        assertEquals(3, assistant.getMotherNaturePosShift());
    }
}