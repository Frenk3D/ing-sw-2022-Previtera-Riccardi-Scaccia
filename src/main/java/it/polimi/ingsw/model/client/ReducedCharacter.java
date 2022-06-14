package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Characters1and7and11;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReducedCharacter implements Serializable {
    private boolean used;
    private int id;
    private int initialCost;
    private List<PawnColor> cardStudentsList;
    int numOfForbidCards;


    public ReducedCharacter(Character character){
        used = character.isUsed();
        id = character.getId();
        initialCost = character.getInitialCost();
        cardStudentsList = new ArrayList<>();

        switch (id){
            case 1:
            case 7:
            case 11:
                Characters1and7and11 characters1and7and11 = (Characters1and7and11) character;
                for (Student s : characters1and7and11.getCardStudentsList()){
                    cardStudentsList.add(s.getColor());
                }
                break;
            case 5:
                Characters3and4and5 character5 = (Characters3and4and5) character;
                numOfForbidCards = character5.getForbidCards();
                break;
        }
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

    public List<PawnColor> getCardStudentsList() {
        return cardStudentsList;
    }

    public int getNumOfForbidCards() {
        return numOfForbidCards;
    }
}
