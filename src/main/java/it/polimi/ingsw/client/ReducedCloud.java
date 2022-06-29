package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Cloud
 */
public class ReducedCloud implements Serializable {
    private final List<PawnColor> studentsList;

    /**
     * adds students to the reduced cloud from the game model's cloud
     *
     * @param cloud the game model's cloud
     */
    public ReducedCloud(Cloud cloud) {
        studentsList = new ArrayList<>();
        for (Student s : cloud.getStudents()) {
            studentsList.add(s.getColor());
        }
    }

    /**
     * @return the list of students on the cloud
     */
    public List<PawnColor> getStudentsList() {
        return studentsList;
    }
}
