package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This is a test class for the Assistant deck class
class AssistantDeckTest {


    AssistantDeck assistantDeck;
    Assistant a;

    @BeforeEach
    void setUp() { //Setups the required attributes
        assistantDeck = new AssistantDeck();
        /* a = new Assistant(1,2);
        assistantDeck.getAssistantsList().add(a);
        assistantDeck.getAssistantsList().add(new Assistant (3,3)); */
    }

    @Test
    void setWizard() { //Tests the wizards setter
        assistantDeck.setWizard(Wizard.KING);
        assertEquals(Wizard.KING, assistantDeck.getWizard());
    }

    @Test
    void getAssistantsList() { //Tests the assistants list getter
        assertEquals(3, assistantDeck.getAssistantsList().get(3 - 1).getValue());
        assertEquals(1, assistantDeck.getAssistantsList().get(0).getValue());
        assertEquals(10, assistantDeck.getAssistantsList().get(9).getValue());

    }

    @Test
    void getAssistantById() { //Tests the assistant by id getter
        assertEquals(3, assistantDeck.getAssistantById(3).getValue());
    }

    @Test
    void removeAssistantById() { //Tests removing an assistant using its id from the deck
        assistantDeck.removeAssistantById(1);
        assertEquals(9, assistantDeck.getAssistantsList().size());
        assistantDeck.removeAssistantById(1);
        assertEquals(9, assistantDeck.getAssistantsList().size());
    }
}