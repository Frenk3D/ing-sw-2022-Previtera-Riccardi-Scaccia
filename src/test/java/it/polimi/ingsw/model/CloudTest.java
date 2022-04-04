package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    Cloud cloud;
    List<Student> studentList;
    Student s;
    @BeforeEach
    void setUp() {
        cloud = new Cloud();
        studentList = new ArrayList<>();
        s = new Student(PawnColor.RED);
    }

    @Test
    void getStudents() {

        studentList.add(s);
        cloud.setStudentsList(studentList);
        assertEquals(studentList,cloud.getStudents());
    }

    @Test
    void setStudentsList() {
        studentList.add(s);
        cloud.setStudentsList(studentList);
        assertEquals(s.getColor(),cloud.getStudents().get(0).getColor());
        assertEquals(s,cloud.getStudents().get(0));
    }

    @Test
    void generateCloudsList() {
        List<Cloud> cloudList = new ArrayList<>();
        Cloud cloud1 = new Cloud();
        Cloud cloud2 = new Cloud();
        cloudList.add(cloud1);
        cloudList.add(cloud2);
        assertEquals(cloudList.size(),cloud.generateCloudsList(2).size());

    }
}