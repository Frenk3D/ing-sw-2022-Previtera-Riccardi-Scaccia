package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Assistant;

import java.io.Serializable;

/**
 * This class impllements the  {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Assistant
 */
public class ReducedAssistant implements Serializable {
    private int id;
    private int motherNaturePosShift;

    /**
     * gets the assistant id and the related number of moves mother nature can do
     * @param assistant is an Assistant
     */
    public ReducedAssistant(Assistant assistant){
        if(assistant == null) return;
        id = assistant.getValue();
        motherNaturePosShift = assistant.getMotherNaturePosShift();
    }

    /**
     *
     * @return the reduced assistant id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the number of moves mother nature can make
     */
    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }
}
