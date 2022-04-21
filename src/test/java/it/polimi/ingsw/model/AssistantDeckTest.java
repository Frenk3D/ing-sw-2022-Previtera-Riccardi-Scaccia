package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantDeckTest {


    AssistantDeck assistantDeck;
    Assistant a;

    @BeforeEach
    void setUp() {
        assistantDeck = new AssistantDeck();
        /* a = new Assistant(1,2);
        assistantDeck.getAssistantsList().add(a);
        assistantDeck.getAssistantsList().add(new Assistant (3,3)); */
    }

    @Test
    void setWizard() {
        assistantDeck.setWizard(3);
        assertEquals(3,assistantDeck.getWizard());
    }

    @Test
    void getAssistantsList() {
        assertEquals(3,assistantDeck.getAssistantsList().get(3-1).getValue());
        assertEquals(1,assistantDeck.getAssistantsList().get(0).getValue());
        assertEquals(10,assistantDeck.getAssistantsList().get(9).getValue());

    }

    @Test
    void getAssistantById() {
        assertEquals(3,assistantDeck.getAssistantById(3).getValue());
    }
    @Test
    void removeAssistantById() {
        assistantDeck.removeAssistantById(1);
        assertEquals(9, assistantDeck.getAssistantsList().size());
        assistantDeck.removeAssistantById(1);
        assertEquals(9, assistantDeck.getAssistantsList().size());
    }
}