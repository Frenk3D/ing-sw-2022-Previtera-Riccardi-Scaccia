package it.polimi.ingsw.model;

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
}
