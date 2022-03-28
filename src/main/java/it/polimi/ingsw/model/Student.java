package it.polimi.ingsw.model;

public class Student {
    //attributes
    private final PawnColor color;

    //constructor
    public Student(PawnColor color){
        this.color = color;
    }

    //getter
    public PawnColor getColor() {
        return color;
    } //never used for now, but used outside of here
}
