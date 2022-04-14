package it.polimi.ingsw.model;

public class Assistant {
    //attributes
    private final int value;
    private int motherNaturePosShift;


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
    public int getId(){
        return value;
    } //id and value are the same attributes
    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }

    //setter

    public void setMotherNaturePosShift(int motherNaturePosShift) {
        this.motherNaturePosShift = motherNaturePosShift;
    }
}
