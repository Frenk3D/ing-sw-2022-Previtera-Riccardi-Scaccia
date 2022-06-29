package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Island
 */
public class ReducedIsland implements Serializable {
    private final int forbidCards;
    private final int weight;
    private final TowerColor towerColor;
    private final List<PawnColor> studentsList;

    /**
     * Fills the island using the existing game model island
     *
     * @param island the game model's island
     */
    public ReducedIsland(Island island) {
        forbidCards = island.getForbidCards();
        weight = island.getWeight();
        towerColor = island.getTowerColor();

        studentsList = new ArrayList<>();
        for (Student s : island.getStudentsList()) {
            studentsList.add(s.getColor());
        }
    }

    /**
     * @return the island's forbid cards
     */
    public int getForbidCards() {
        return forbidCards;
    }

    /**
     * @return the island's weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return the island tower's color
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * @return the island's list of students
     */
    public List<PawnColor> getStudentsList() {
        return studentsList;
    }

    @Override
    /**
     * Transforms the island's info into a string (for the cli)
     */
    public String toString() {
        String result = "";
        result += "----------ISOLA----------\n";
        result += "Dimensione: " + weight + " Forbid card: " + forbidCards + "\n";

        for (PawnColor s : studentsList) {
            result += "Studente: " + s + "\n";
        }
        if (towerColor != null) {
            result += "Torre " + towerColor + " numero: " + weight + "\n";
        }
        return result;
    }
}
