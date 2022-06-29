package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class implements the game object cloud
 * the clouds are filled with students at the beginning of each round
 */
public class Cloud {
    //attributes
    private List<Student> studentsList;


    //Methods
    //constructor

    /**
     * default constructor
     */
    public Cloud() {
        studentsList = new ArrayList<Student>();
    }

    //getter

    /**
     * generates the list of clouds
     *
     * @param numOfPlayers the number of players
     * @return the generated clouds list
     */
    public static List<Cloud> generateCloudsList(int numOfPlayers) {
        List<Cloud> cloudsList = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            cloudsList.add(new Cloud());
        }
        return cloudsList;
    }

    //setter

    /**
     * @return the list of students on the cloud
     */
    public List<Student> getStudents() {
        return studentsList;
    }

    /**
     * sets the list of students on the cloud
     *
     * @param studentsList the cloud's students list
     */
    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    /**
     * fills the cloud with a random list of students
     * the number of students generated varies based on the number of players of the game
     *
     * @param bag         the bag
     * @param numOfPlayer the number of players
     */
    public void fillCloud(Bag bag, int numOfPlayer) {
        int numOfStudents;
        if (numOfPlayer == 3) {
            numOfStudents = 4;
        } else {
            numOfStudents = 3;
        }
        studentsList = bag.extractStudents(numOfStudents);
    }


}
