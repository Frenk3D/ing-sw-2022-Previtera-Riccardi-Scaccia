package it.polimi.ingsw.model;

/**
 *This class implements the game object Assistant
 * at the beginning of each round the players must choose one of the 10(or the remaining) assistants
 */
public class Assistant {
    //attributes
    private final int value;
    private int motherNaturePosShift;


    //Methods
    //constructor

    /**
     * default constructor
     * @param value
     * @param motherNaturePosShift
     */
    public Assistant(int value,int motherNaturePosShift){
        this.value = value;
        this.motherNaturePosShift= motherNaturePosShift;
    }

    //getter

    /**
     *
     * @return the assistant's value
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return the assistant's id
     */
    public int getId(){
        return value;
    } //id and value are the same attributes
    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }

    //setter

    /**
     * sets the number of moves mother noture can do
     * @param motherNaturePosShift
     */

    public void setMotherNaturePosShift(int motherNaturePosShift) {
        this.motherNaturePosShift = motherNaturePosShift;
    }
}
