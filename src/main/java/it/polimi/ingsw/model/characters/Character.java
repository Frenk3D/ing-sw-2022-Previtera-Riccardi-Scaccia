package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Factory;

import java.util.ArrayList;
import java.util.Collections;
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

    public static List<Character> extractCharacters(){
        List<Character> returnList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>(); //we use integers to avoid generating useless characters

        for (int i=1;i<=12;i++){
            idList.add(i);
        }

        Collections.shuffle(idList);

        returnList.add(Factory.newCharacter(idList.get(0)));
        returnList.add(Factory.newCharacter(idList.get(1)));
        returnList.add(Factory.newCharacter(idList.get(2)));
        return returnList;
    }
}


//constructor protected?