package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Professor {
    //attributes
    private final PawnColor color;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor professor = (Professor) o;
        return getColor() == professor.getColor();
    }

  /*  @Override
    public int hashCode() {
        return Objects.hash(getColor());
    }

   */
}
