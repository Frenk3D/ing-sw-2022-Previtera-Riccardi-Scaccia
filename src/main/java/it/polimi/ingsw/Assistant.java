package it.polimi.ingsw;

public class Assistant {
    //attributes
    int value;
    int motherNaturePosShift;


    //Methods
    //constructor
    public Assistant(int value,int motherNaturePosShift){
        this.value = value;
        this.motherNaturePosShift= motherNaturePosShift;
    }

    //getter
    public int getValue() {
        return value;
    }
    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }
}
