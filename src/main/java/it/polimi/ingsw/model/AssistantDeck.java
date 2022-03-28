package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class AssistantDeck {
    //attributes
    private int wizard; //every wizard is the deck's representative, every wizard has the same deck and the attribute is set from Game for practical purposes
    private List<Assistant> assistantsList; //manage when removed...


    //Methods
    //constructor
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
    public void setWizard (int type){
        wizard = type;
    }

    public List<Assistant> getAssistantsList() {
        return assistantsList;
    }


    public Assistant getAssistantById(int id){
        for(Assistant assistant : assistantsList){
            if(assistant.getId()==id){
                return assistant;
            }
        }
        return null;
    }



}
