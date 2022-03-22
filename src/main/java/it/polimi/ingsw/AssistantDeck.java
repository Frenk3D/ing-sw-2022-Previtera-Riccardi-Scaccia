package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class AssistantDeck {
    private int wizard; //every wizard is the deck's representative, every wizard has the same deck and the attribute is set from Game for practical purposes
    private List<Assistant> assistantsList; //manage when removed...

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

    public Assistant extractAssistant(int listPos){
        if(assistantsList.isEmpty()) return null;
        Assistant extractedAssistant;
        extractedAssistant=assistantsList.get(listPos);
        assistantsList.remove(listPos);
        return extractedAssistant;
    }

    public void setWizard (int type){
        wizard = type;
    }

}
