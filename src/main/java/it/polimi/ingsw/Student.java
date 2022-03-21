package it.polimi.ingsw;

public class Student {
    private  final PawnColor color;
    public Student(PawnColor color){
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    } //never used for now, but used outside of here
}
