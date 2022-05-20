package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Professor;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReducedDashboard implements Serializable {

    private Map<PawnColor,Integer> studentsHall;
    private List<PawnColor> entranceList;
    private List<PawnColor> professorsList;
    private int towerNumber;

    public ReducedDashboard(Dashboard dashboard){
        studentsHall = new HashMap<>();
        studentsHall.put(PawnColor.RED,dashboard.getNumOfHallStudents(PawnColor.RED));
        studentsHall.put(PawnColor.GREEN,dashboard.getNumOfHallStudents(PawnColor.GREEN));
        studentsHall.put(PawnColor.YELLOW,dashboard.getNumOfHallStudents(PawnColor.YELLOW));
        studentsHall.put(PawnColor.PINK,dashboard.getNumOfHallStudents(PawnColor.PINK));
        studentsHall.put(PawnColor.BLUE,dashboard.getNumOfHallStudents(PawnColor.BLUE));

        entranceList = new ArrayList<>();
        for(Student s : dashboard.getEntranceList()){
            entranceList.add(s.getColor());
        }

        professorsList = new ArrayList<>();
        for (Professor p: dashboard.getProfessorsList()){
            professorsList.add(p.getColor());
        }
        towerNumber = dashboard.getTowersList().size();
    }

    public Map<PawnColor, Integer> getStudentsHall() {
        return studentsHall;
    }

    public List<PawnColor> getEntranceList() {
        return entranceList;
    }

    public List<PawnColor> getProfessorsList() {
        return professorsList;
    }

    public int getTowerNumber() {
        return towerNumber;
    }
}
