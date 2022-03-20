package it.polimi.ingsw;

public class Student {
    private  final PawnColor color;
    public Student(PawnColor color){
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    } //per ora never used, ma da fuori si
}
