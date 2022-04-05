package it.polimi.ingsw.model;
//we do not use assume statements
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.TowerColor.*;
import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {
    Dashboard dashboard;
    List<Student> hallList;
    Student s;
    Player player;
    TowerColor towerColor = BLACK;
    Professor professor;
    List<Student> entranceList;
    Tower tower;
    List<Tower> towerList;

    @BeforeEach
    void setUp() {
        dashboard = new Dashboard();
        s = new Student(PawnColor.RED);
        dashboard.addStudentHall(s,player);
        player = new Player("Pippo", 1, 1, towerColor);
        player.setMoney(3);
        professor = new Professor(PawnColor.RED);
        entranceList = new ArrayList<>();
        tower = new Tower(TowerColor.WHITE);
        hallList = new ArrayList<>();
        towerList = new ArrayList<>();
    }

    @Test
    void getHallStudentsListByColor() {


        assertEquals(s,dashboard.getHallStudentsListByColor(PawnColor.RED).get(0));
        assertEquals(3,player.getMoney());
    }

    @Test
    void getProfessorByColor() {
        dashboard.getProfessorsList().add(professor);
        assertEquals(professor,dashboard.getProfessorByColor(PawnColor.RED));
    }

    @Test
    void placeStudentEntrance() {
        Bag bag = Bag.getInstance();
        bag.addAllStudents();
        List<Student> tmpList = new ArrayList<>();
        for(int i=0;i<7;i++){
            tmpList.add(bag.extractStudents(7).get(i));
        }
       // dashboard.setEntranceList(entranceList);
        dashboard.placeStudentEntrance(2);
        assertEquals(tmpList.size(),dashboard.getEntranceList().size());
        assertNotEquals(tmpList,dashboard.getEntranceList()); //because the extracted students are random
    }

   //we use jupiter
    @RepeatedTest(2) //we try multiple tests
    void generateTower() {
        List<Tower> towerList = new ArrayList<>();
        for(int i=0;i<8;i++) {
            towerList.add(tower);
        }

        dashboard.generateTower(2,WHITE);
        assertEquals(towerList,dashboard.getTowersList());


    }

    @Test
    void addStudentHall() {
        hallList.add(s);
        dashboard.addStudentHall(s,player);
        assertEquals(hallList.get(0),dashboard.getHallStudentsListByColor(PawnColor.RED).get(0));
    }

    @Test
    void setTowersList() {
        towerList.add(tower);
        dashboard.setTowersList(towerList);
        assertEquals(towerList,dashboard.getTowersList());
    }

}

    //other useless tests (getter and setters)
