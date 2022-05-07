package it.polimi.ingsw.model.characters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Character {

    boolean used;
    int id;
    int initialCost;


    //getter prototypes
    public boolean isUsed() {
        return used;
    }

    public int getId() {
        return id;
    }

    public int getInitialCost() {
        return initialCost;
    }

    //setter prototypes
    public void setUsed() {
        used = true; //can't go again to false
    }

    public abstract boolean applyEffect(CharacterParameters params);
    public abstract void initCharacter(CharacterParameters params);

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

    // only for tests purposes
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return isUsed() == character.isUsed() && getId() == character.getId() && getInitialCost() == character.getInitialCost();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isUsed(), getId(), getInitialCost());
    }
}
