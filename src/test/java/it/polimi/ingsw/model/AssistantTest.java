package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssistantTest {
    private Assistant assistant;

    @BeforeEach
    void setUp() {
        assistant = new Assistant(5, 2);
    }

    @Test
    void getValue() {
        assertEquals(5, assistant.getValue());
    }

    @Test
    void getId() {
        assertEquals(assistant.getValue(), assistant.getId());
    }

    @Test
    void getMotherNaturePosShift() {
        assertEquals(2, assistant.getMotherNaturePosShift());
    }

    @Test
    void setMotherNaturePosShift() {
        assistant.setMotherNaturePosShift(3);
        assertEquals(3, assistant.getMotherNaturePosShift());
    }
}