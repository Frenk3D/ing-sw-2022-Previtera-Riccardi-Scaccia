package it.polimi.ingsw.model;

import it.polimi.ingsw.client.ReducedAssistant;
import it.polimi.ingsw.model.enumerations.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is the deck containing all the game's assistants
 */
public class AssistantDeck {
    //attributes
    private Wizard wizard; //every wizard is the deck's representative, every wizard has the same deck and the attribute is set from Game for practical purposes
    private List<Assistant> assistantsList; //manage when removed...


    /**
     * constructor used to fill the deck with each assistant
     */
    public AssistantDeck(){
        assistantsList = new ArrayList<Assistant>();
        assistantsList.add(new Assistant(1,1));
        assistantsList.add(new Assistant(2,1));
        assistantsList.add(new Assistant(3,2));
        assistantsList.add(new Assistant(4,2));
        assistantsList.add(new Assistant(5,3));
        assistantsList.add(new Assistant(6,3));
        assistantsList.add(new Assistant(7,4));
        assistantsList.add(new Assistant(8,4));
        assistantsList.add(new Assistant(9,5));
        assistantsList.add(new Assistant(10,5));
    }
    //setter

    /**
     * sets the wizard's type
     * @param type the wizard's type
     */
    public void setWizard (Wizard type){
        wizard = type;
    }

    /**
     *
     * @return the list of available assistants
     */
    public List<Assistant> getAssistantsList() {
        return assistantsList;
    }

    /**
     *
     * @param id the assistant's id
     * @return the assistant chosen by id
     */
    public Assistant getAssistantById(int id){
        for(Assistant assistant : assistantsList){
            if(assistant.getId()==id){
                return assistant;
            }
        }
        return null;
    }

    /**
     * removes the assistant chosen by id
     * @param id the assistant's id
     */
    public void removeAssistantById(int id){
        for(Assistant assistant : assistantsList){
            if(assistant.getId()==id){
                assistantsList.remove(assistant);
                return;
            }
        }
    }

    /**
     *
     * @return the list of Reduced Assistants (used by the client side)
     */
    public List<ReducedAssistant> getReducedAssistantsList(){
        List<ReducedAssistant> reducedAssistants = new ArrayList<>();
        for(Assistant a : assistantsList){
            reducedAssistants.add(new ReducedAssistant(a));
        }
        return reducedAssistants;
    }

    /**
     *
     * @return a wizard
     */
    public Wizard getWizard(){
        return wizard;
    }

}
