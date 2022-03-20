package it.polimi.ingsw;

public class Assistant {
    int value;
    int motherNaturePosShift;

    public Assistant(int value,int motherNaturePosShift){
        this.value = value;
        this.motherNaturePosShift= motherNaturePosShift;
    }
    public int getValue() {
        return value;
    }

    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }
}
