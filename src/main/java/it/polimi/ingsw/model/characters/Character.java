package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class is an abstract class that implements the game object Character
 * There are 12 different types of characters,each with a unique effect. The constructor for each type is made from the {@link Factory}
 * we decided to use the factory pattern to implement this
 */
public abstract class Character {

    boolean used;
    int id;
    int initialCost;


    //getter prototypes

    /**
     *
     * @return true if the character is used
     * @return false if it is not
     */
    public boolean isUsed() {
        return used;
    }

    /**
     *
     * @return the character's id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the character's initial cost
     */
    public int getInitialCost() {
        return initialCost;
    }

    //setter prototypes

    /**
     * Sets the character as used
     */
    public void setUsed() {
        used = true; //can't go again to false
    }

    /**
     * class used for the pattern factory
     * @param params
     * @return true if the effect is applied
     */
    public abstract boolean applyEffect(CharacterParameters params);

    /**
     * initializes the character
     * @param params
     */
    public abstract void initCharacter(CharacterParameters params);

    /**
     *
     * @return randomly generated characters list
     */
    public static List<Character> extractCharacters(){
        List<Character> returnList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>(); //we use integers to avoid generating useless characters

        for (int i=1;i<=12;i++){
            idList.add(i);
        }

        //Collections.shuffle(idList);

        returnList.add(Factory.newCharacter(idList.get(0))); //we can force them leaving the shuffle, it's id-1
        returnList.add(Factory.newCharacter(idList.get(4))); //or 1
        returnList.add(Factory.newCharacter(idList.get(2))); //
        return returnList;
    }


    // only for tests purposes

    /**
     * Sets the character's id
     * @param id
     */
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
