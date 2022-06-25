package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Characters1and7and11;
import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Character
 */
public class ReducedCharacter implements Serializable {
    private boolean used;
    private int id;
    private int initialCost;
    private List<PawnColor> cardStudentsList;
    int numOfForbidCards;

    /**
     * Sets character's used status,character's id,initial cost and the students list
     * depending on which character is passed card students or forbid cards are added
     * @param character is a Character
     */
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

    /**
     *
     * @return true if the character is used
     * @return false if the character is not used
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

    /**
     *
     * @return the list of students on the character
     */
    public List<PawnColor> getCardStudentsList() {
        return cardStudentsList;
    }

    /**
     *
     * @return the number of forbid cards on the character
     */
    public int getNumOfForbidCards() {
        return numOfForbidCards;
    }
}
