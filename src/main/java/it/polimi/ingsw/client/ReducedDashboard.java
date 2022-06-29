package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Professor;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class implements the {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Dashboard
 */
public class ReducedDashboard implements Serializable {

    private final Map<PawnColor, Integer> studentsHall;
    private final List<PawnColor> entranceList;
    private final List<PawnColor> professorsList;
    private final int towerNumber;

    /**
     * fills the reduced dashboard using the game model's dashboard
     *
     * @param dashboard the game model's dashboard
     */
    public ReducedDashboard(Dashboard dashboard) {
        studentsHall = new HashMap<>();
        studentsHall.put(PawnColor.RED, dashboard.getNumOfHallStudents(PawnColor.RED));
        studentsHall.put(PawnColor.GREEN, dashboard.getNumOfHallStudents(PawnColor.GREEN));
        studentsHall.put(PawnColor.YELLOW, dashboard.getNumOfHallStudents(PawnColor.YELLOW));
        studentsHall.put(PawnColor.PINK, dashboard.getNumOfHallStudents(PawnColor.PINK));
        studentsHall.put(PawnColor.BLUE, dashboard.getNumOfHallStudents(PawnColor.BLUE));

        entranceList = new ArrayList<>();
        for (Student s : dashboard.getEntranceList()) {
            entranceList.add(s.getColor());
        }

        professorsList = new ArrayList<>();
        for (Professor p : dashboard.getProfessorsList()) {
            professorsList.add(p.getColor());
        }
        towerNumber = dashboard.getTowersList().size();
    }

    /**
     * @return the students hall's pawn color and index map
     */
    public Map<PawnColor, Integer> getStudentsHall() {
        return studentsHall;
    }

    /**
     * @return the entrance list of students
     */
    public List<PawnColor> getEntranceList() {
        return entranceList;
    }

    /**
     * @return the list of professors
     */
    public List<PawnColor> getProfessorsList() {
        return professorsList;
    }

    /**
     * @return the number of towers
     */
    public int getTowerNumber() {
        return towerNumber;
    }
}
