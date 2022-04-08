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
        this.studentsList=studentsList;
    }

    public static List<Cloud> generateCloudsList(int numOfPlayers){
        List<Cloud> cloudsList = new ArrayList<>();
        for(int i=0; i<numOfPlayers; i++){
            cloudsList.add(new Cloud());
        }
        return cloudsList;
    }

    public void fillCloud(Bag bag, int numOfPlayer){
        int numOfStudents;
        if(numOfPlayer==3){
            numOfStudents=4;
        }
        else {
            numOfStudents=3;
        }
        studentsList=bag.extractStudents(numOfStudents);
    }


}
