package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.characters.Character;

import java.io.Serializable;

public class ReducedCharacter implements Serializable {
    boolean used;
    int id;
    int initialCost;

    public ReducedCharacter(Character character){
        used = character.isUsed();
        id = character.getId();
        initialCost = character.getInitialCost();
    }

    public boolean isUsed() {
        return used;
    }

    public int getId() {
        return id;
    }

    public int getInitialCost() {
        return initialCost;
    }
}
