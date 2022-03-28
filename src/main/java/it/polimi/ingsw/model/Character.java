package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    //attributes
    private int numOfUse;
    private final int id;
    private final int initialCost;


    //Methods
    //constructor
    public Character(int id,int initialCost){
        this.id = id;
        this.initialCost = initialCost;
    }

    //getter
    public int getNumOfUse() {
        return numOfUse;
    }
    public int getId() {
        return id;
    }
    public int getInitialCost(){
        return initialCost;
    }

    //setter
    public void setNumOfUse(int numOfUse) {
        this.numOfUse = numOfUse;
    }


    public static List<Character> getAllCharacters(){
        List<Character> newlist = new ArrayList<>();
        newlist.add(new Character(1,1));
        newlist.add(new Character(2,2));
        newlist.add(new Character(3,3));
        newlist.add(new Character(4,1));
        newlist.add(new Character(5,2));
        newlist.add(new Character(6,3));
        newlist.add(new Character(7,1));
        newlist.add(new Character(8,2));
        newlist.add(new Character(9,3));
        newlist.add(new Character(10,1));
        newlist.add(new Character(11,2));
        newlist.add(new Character(12,3));
        return newlist;
    }

}
