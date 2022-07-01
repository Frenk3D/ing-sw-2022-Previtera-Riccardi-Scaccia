package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This is a test class for the cloud
class CloudTest {
    Cloud cloud;
    List<Student> studentList;
    Student s;

    @BeforeEach
    void setUp() { //Sets the required attributes for testing
        cloud = new Cloud();
        studentList = new ArrayList<>();
        s = new Student(PawnColor.RED);
    }

    @Test
    void getStudents() { //Tests the students' getter

        studentList.add(s);
        cloud.setStudentsList(studentList);
        assertEquals(studentList, cloud.getStudents());
    }

    @Test
    void setStudentsList() { //Tests the students' list setter
        studentList.add(s);
        cloud.setStudentsList(studentList);
        assertEquals(s.getColor(), cloud.getStudents().get(0).getColor());
        assertEquals(s, cloud.getStudents().get(0));
    }

    @Test
    void generateCloudsList() { //Tests the clouds' list generator
        List<Cloud> cloudList = new ArrayList<>();
        Cloud cloud1 = new Cloud();
        Cloud cloud2 = new Cloud();
        cloudList.add(cloud1);
        cloudList.add(cloud2);
        assertEquals(cloudList.size(), Cloud.generateCloudsList(2).size()); //the size of the two clouds must be equals

    }
}