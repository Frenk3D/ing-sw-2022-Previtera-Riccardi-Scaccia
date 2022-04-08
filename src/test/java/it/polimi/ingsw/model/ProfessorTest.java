package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    Professor professor;
    List<Professor> professorsList;
    List<Professor> tmpProfessorList;

    @BeforeEach
    void setUp() {
        professor = new Professor(PawnColor.RED);
        professorsList = new ArrayList<>();
        tmpProfessorList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateProfessorsList() {
        professorsList = Professor.generateProfessorsList();
        tmpProfessorList = Professor.generateProfessorsList();
        assertEquals(professorsList,tmpProfessorList);

    }
}