package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Assistant;

public class ReducedAssistant {
    private int id;
    private int motherNaturePosShift;

    public ReducedAssistant(Assistant assistant){
        if(assistant == null) return;
        id = assistant.getValue();
        motherNaturePosShift = assistant.getMotherNaturePosShift();
    }

    public int getId() {
        return id;
    }

    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }
}
