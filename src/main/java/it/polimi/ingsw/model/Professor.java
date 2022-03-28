package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    //attributes
    private PawnColor color;


    //constructor
    public Professor(PawnColor color){
        this.color = color;
    }

    //getter
    public PawnColor getColor(){
        return color;
    }

    public static List<Professor> generateProfessorsList(){
        List<Professor> result = new ArrayList<>();
        result.add(new Professor(PawnColor.YELLOW));
        result.add(new Professor(PawnColor.RED));
        result.add(new Professor(PawnColor.GREEN));
        result.add(new Professor(PawnColor.BLUE));
        result.add(new Professor(PawnColor.PINK));
        return result;
    }
}
