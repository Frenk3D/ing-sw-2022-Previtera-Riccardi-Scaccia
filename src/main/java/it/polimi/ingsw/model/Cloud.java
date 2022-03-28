package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Cloud {
    //attributes
    private List<Student> studentsList;


    //Methods
    //constructor
    public Cloud(){
        studentsList = new ArrayList<Student>();
    }

    //getter
    public List<Student> getStudents(){

        return studentsList;
    }

    //setter
    public void setStudentsList(List<Student> studentsList) {

    }

    public static List<Cloud> generateCloudsList(int numOfPlayers){
        return null;
    }


}
