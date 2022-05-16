package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;

public class ReducedIsland {
    private int forbidCards;
    private int weight;
    private TowerColor towerColor;
    private List<PawnColor> studentsList;

    public ReducedIsland(Island island){
        forbidCards = island.getForbidCards();
        weight = island.getWeight();
        towerColor = island.getTowerColor();

        studentsList = new ArrayList<>();
        for(Student s : island.getStudentsList()){
            studentsList.add(s.getColor());
        }
    }

    public int getForbidCards() {
        return forbidCards;
    }

    public int getWeight() {
        return weight;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public List<PawnColor> getStudentsList() {
        return studentsList;
    }
}