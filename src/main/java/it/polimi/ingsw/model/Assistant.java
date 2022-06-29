package it.polimi.ingsw.model;

/**
 *This class implements the game object Assistant
 * at the beginning of each round the players must choose one of the 10(or the remaining) assistants
 */
public class Assistant {
    //attributes
    private final int value;
    private int motherNaturePosShift;



    /**
     * default constructor
     * @param value the assistant's value
     * @param motherNaturePosShift the number of moves mother nature can make
     */
    public Assistant(int value,int motherNaturePosShift){
        this.value = value;
        this.motherNaturePosShift= motherNaturePosShift;
    }


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

    /**
     *
     * @return the number of moves mother nature can do
     */
    public int getMotherNaturePosShift() {
        return motherNaturePosShift;
    }

    //setter

    /**
     * sets the number of moves mother nature can do
     * @param motherNaturePosShift the number of moves mother nature can do
     */
    public void setMotherNaturePosShift(int motherNaturePosShift) {
        this.motherNaturePosShift = motherNaturePosShift;
    }
}
