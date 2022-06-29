package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.Objects;

/**
 * This class implements the game object Student
 * A student is defined by its color
 */
public class   Student {
    //attributes
    private final PawnColor color;

    //constructor

    /**
     * default constructor
     * @param color the pawn color
     */
    public Student(PawnColor color){
        this.color = color;
    }

    //getter

    /**
     *
     * @return the student's color
     */
    public PawnColor getColor() {
        return color;
    } //never used for now, but used outside of here



    //only for tests purposes

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getColor() == student.getColor();
    }

}

