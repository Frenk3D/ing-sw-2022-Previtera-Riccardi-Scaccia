package it.polimi.ingsw.model;
//if we want more matches we have to use bag like a normal class and not like a singleton

import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class implements the bag containing the students (to be extracted from it)
 */
public class Bag {
    private final Logger logger = Logger.getLogger(getClass().getName());
    //attributes
    private final List<Student> studentsList;

    /**
     * Constructor Bag creates a new Bag instance.
     */
    public Bag() {
        studentsList = new ArrayList<>(); //removed argument from <> as Intellij suggests
    }

    /**
     * @return the bag's stduents list
     */
    public List<Student> getStudentsList() {
        return studentsList;
    }

    /**
     * adds the initial 10 students in the bag
     */
    public void initialBagFill() { //fills the bag with two students of each color
        for (int i = 0; i < 2; i++) {
            studentsList.add(new Student(PawnColor.BLUE));
            studentsList.add(new Student(PawnColor.RED));
            studentsList.add(new Student(PawnColor.GREEN));
            studentsList.add(new Student(PawnColor.PINK));
            studentsList.add(new Student(PawnColor.YELLOW));
        }
    }

    /**
     * Method extractStudents is called when someone have to extract students from the bag.
     * A check on the number of the students in the bag returns null if there aren't enough
     *
     * @param num of the students to extract
     * @return the students extracted (type List).
     */
    public List<Student> extractStudents(int num) {
        if (studentsList.size() < num) {
            logger.log(Level.SEVERE, "Not enough students in bag");
            return null;
        }

        List<Student> result = new ArrayList<>(); //..
        int remainingExtraction = num;
        while (remainingExtraction > 0) {
            int randomInt = (int) (Math.random() * (studentsList.size()));
            result.add(studentsList.get(randomInt));
            studentsList.remove(randomInt);
            remainingExtraction--;
        }
        return result;
    }

    /**
     * adds all the remaining students to the bag
     */
    public void addAllStudents() { //fills the bag with remaining students
        for (int i = 0; i < 24; i++) {
            studentsList.add(new Student(PawnColor.BLUE));
            studentsList.add(new Student(PawnColor.RED));
            studentsList.add(new Student(PawnColor.GREEN));
            studentsList.add(new Student(PawnColor.PINK));
            studentsList.add(new Student(PawnColor.YELLOW));
        }
    }

    /**
     * @return the number of remaining students in the bag
     */
    public int getRemainingStudents() {
        return studentsList.size();
    }
}
