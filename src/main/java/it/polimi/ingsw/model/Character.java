package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

    int numOfUse;
    int id;
    int initialCost;


    //getter prototypes
    public int getNumOfUse() {
        return numOfUse;
    }

    public int getId() {
        return id;
    }

    public int getInitialCost() {
        return initialCost;
    }

    //setter prototypes
    public void setNumOfUse(int numOfUse) {
        this.numOfUse = numOfUse;
    }

    public abstract void applyEffect(CharacterParameters params);

    public static List<Character> getAllCharacters(){
        List<Character> newlist = new ArrayList<>();
        newlist.add(Factory.newCharacter(1,1));
        newlist.add(Factory.newCharacter(2,2));
        newlist.add(Factory.newCharacter(3,3));
        newlist.add(Factory.newCharacter(4,1));
        newlist.add(Factory.newCharacter(5,2));
        newlist.add(Factory.newCharacter(6,3));
        newlist.add(Factory.newCharacter(7,1));
        newlist.add(Factory.newCharacter(8,2));
        newlist.add(Factory.newCharacter(9,3));
        newlist.add(Factory.newCharacter(10,1));
        newlist.add(Factory.newCharacter(11,2));
        newlist.add(Factory.newCharacter(12,3));

        return newlist;
    }
}


//constructor protected?
