package it.polimi.ingsw.model;

import java.util.Objects;

public class Tower {
    //attributes
    private final TowerColor color;


    //constructor
    public Tower(TowerColor color){
        this.color= color;
    }

    //getter
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

    @Override
    public int hashCode() {
        return Objects.hash(getColor());
    }
}


