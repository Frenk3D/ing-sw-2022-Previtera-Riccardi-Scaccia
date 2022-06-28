package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.Objects;

/**
 * implements the game object tower
 */
public class Tower {
    //attributes
    private final TowerColor color;


    //constructor

    /**
     * default constructor
     * @param color the tower color
     */
    public Tower(TowerColor color){
        this.color= color;
    }

    //getter

    /**
     *
     * @return the tower's color
     */
    public TowerColor getColor(){
        return color;
    }


    //for test purposes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tower)) return false;
        Tower tower = (Tower) o;
        return getColor() == tower.getColor();
    }

   /* @Override
    public int hashCode() {
        return Objects.hash(getColor());
    }

    */
}


