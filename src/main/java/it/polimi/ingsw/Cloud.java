package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Cloud {
    private List<Student> studentsList;

    public Cloud(){
        studentsList = new ArrayList<Student>();
    }
    public void setStudentsList(List<Student> studentsList) {

    }

    public List<Student> getStudents(){

        return studentsList;
    }
}
