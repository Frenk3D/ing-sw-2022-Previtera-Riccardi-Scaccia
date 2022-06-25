package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Assistant;

import java.io.Serializable;

public class ReducedAssistant implements Serializable {
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
