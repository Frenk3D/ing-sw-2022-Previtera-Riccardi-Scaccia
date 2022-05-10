package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.ArrayList;
import java.util.List;

public class ReducedCloud {
    private List<PawnColor> studentsList;

    public ReducedCloud(Cloud cloud){
        studentsList = new ArrayList<>();
        for(Student s : cloud.getStudents()){
            studentsList.add(s.getColor());
        }
    }

    public List<PawnColor> getStudentsList() {
        return studentsList;
    }
}
