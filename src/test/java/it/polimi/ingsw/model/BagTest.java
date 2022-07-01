package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//This is a test class for the bag
class BagTest {
    Bag bag;
    Bag tmpBag;
    List<Student> studentsList;
    List<Student> tmpStudentsList;

    @BeforeEach
    void setUp() { //This class sets the needed attributes
        studentsList = new ArrayList<>();
        bag = new Bag();
        tmpBag = new Bag();
        tmpStudentsList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getStudentsList() {

    }

    private boolean searchByColor(Bag tmpBag) { //This method is implemented to make testing easier for this class
        int counterRed = 0;
        int counterGreen = 0;
        int counterBlue = 0;
        int counterPink = 0;
        int counterYellow = 0;
        for (Student s : tmpBag.getStudentsList()) {
            switch (s.getColor()) {
                case RED:
                    counterRed++;
                    break;
                case GREEN:
                    counterGreen++;
                    break;
                case YELLOW:
                    counterYellow++;
                    break;
                case PINK:
                    counterPink++;
                    break;
                case BLUE:
                    counterBlue++;
                    break;
            }
        }
        if (counterBlue == 2 && counterGreen == 2 && counterRed == 2 && counterPink == 2 && counterYellow == 2) {
            return true;
        } else if (counterBlue == 24 && counterGreen == 24 && counterRed == 24 && counterPink == 24 && counterYellow == 24)
            return true;
        else
            return false;

    }

    @Test
    void initialBagFill() { //Tests the method InitialBagFill

        for (int i = 0; i < 2; i++) { //Fill a temp bag with 5 students
            tmpBag.getStudentsList().add(new Student(PawnColor.RED));
            tmpBag.getStudentsList().add(new Student(PawnColor.BLUE));
            tmpBag.getStudentsList().add(new Student(PawnColor.GREEN));
            tmpBag.getStudentsList().add(new Student(PawnColor.YELLOW));
            tmpBag.getStudentsList().add(new Student(PawnColor.PINK));
        }
        bag.initialBagFill();
        assertEquals(tmpBag.getStudentsList().size(), bag.getStudentsList().size());
        assertEquals(true, searchByColor(bag));
        assertEquals(true, searchByColor(tmpBag));

    }

    @Test
    void extractStudents() { //Tests the extact students method
        bag.addAllStudents();
        studentsList = (bag.extractStudents(3));
        tmpStudentsList = (bag.extractStudents(3));
        assertNotEquals(studentsList, tmpStudentsList); //the two list must be different since the extraction is random,even if there is a slight chance they might actually be equals
        assertNotEquals(studentsList, null);
        assertNotEquals(tmpStudentsList, null);
    }

    @Test
    void addAllStudents() { //Tests addAllStudents
        for (int i = 0; i < 24; i++) { //adds all the remaining students
            tmpBag.getStudentsList().add(new Student(PawnColor.RED));
            tmpBag.getStudentsList().add(new Student(PawnColor.BLUE));
            tmpBag.getStudentsList().add(new Student(PawnColor.GREEN));
            tmpBag.getStudentsList().add(new Student(PawnColor.YELLOW));
            tmpBag.getStudentsList().add(new Student(PawnColor.PINK));
        }
        bag.addAllStudents();
        assertEquals(tmpBag.getStudentsList().size(), bag.getStudentsList().size());
        assertEquals(true, searchByColor(bag));
        assertEquals(true, searchByColor(tmpBag));
    }
}