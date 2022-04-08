package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;
    Student tmpStudent;

    @BeforeEach
    void setUp() {
        student = new Student(PawnColor.RED);
        tmpStudent = new Student(PawnColor.RED);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getColor() {
        assertEquals(student.getColor(),tmpStudent.getColor());
    }
}