package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.model.enumerations.TowerColor.BLACK;
import static it.polimi.ingsw.model.enumerations.TowerColor.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//This is a test class for the dashboard
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
    Bag bag;
    AtomicInteger tableMoney;

    @BeforeEach
    void setUp() { //Sets the required attributes for testing
        tableMoney = new AtomicInteger(20);
        bag = new Bag();
        dashboard = new Dashboard();
        s = new Student(PawnColor.RED);
        dashboard.addStudentHall(s, player, tableMoney);
        player = new Player("Pippo", 1);
        player.setPlayerTowerColor(towerColor);
        player.setTeam(1);
        player.modifyMoney(3, tableMoney);
        professor = new Professor(PawnColor.RED);
        entranceList = new ArrayList<>();
        tower = new Tower(TowerColor.WHITE);
        hallList = new ArrayList<>();
        towerList = new ArrayList<>();
    }

    @Test
    void getHallStudentsListByColor() { //Test the hall list students' getter based on their color


        assertEquals(s, dashboard.getHallStudentsListByColor(PawnColor.RED).get(0)); //the red student must be equals to the student found by the method
        assertEquals(3, player.getMoney());
    }

    @Test
    void getProfessorByColor() { //Tests the professor by color getter
        dashboard.getProfessorsList().add(professor);
        assertEquals(professor, dashboard.getProfessorByColor(PawnColor.RED));
    }

    @Test
    void placeStudentEntrance() { //Tests the method that places students in the entrance
        bag.addAllStudents(); //we use add all students because when we fill the dashboard the bag has already been used with initialBagFill()
        List<Student> tmpList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            tmpList.add(bag.extractStudents(7).get(i));
        }
        // dashboard.setEntranceList(entranceList);
        dashboard.placeStudentEntrance(2, bag);
        assertEquals(tmpList.size(), dashboard.getEntranceList().size());
        assertNotEquals(tmpList, dashboard.getEntranceList()); //because the extracted students are random
    }

    //we use jupiter
    @RepeatedTest(2)
    //we try multiple tests
    void generateTower() { //Tests the towers' generator
        List<Tower> towerList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            towerList.add(tower);
        }

        dashboard.generateTower(2, WHITE);
        assertEquals(towerList, dashboard.getTowersList());
    }

    @Test
    void addStudentHall() { //Tests the method that adds students to the hall
        hallList.add(s);
        dashboard.addStudentHall(s, player, tableMoney);
        assertEquals(hallList.get(0), dashboard.getHallStudentsListByColor(PawnColor.RED).get(0));
    }

    @Test
    void setTowersList() { //Tests the tower list setter
        towerList.add(tower);
        dashboard.setTowersList(towerList);
        assertEquals(towerList, dashboard.getTowersList());
    }

    @Test
    void getEntranceStudentByIndex() { //Tests the method that gets a student in the entrance by its index

        assertEquals(null, dashboard.getEntranceStudentByIndex(99)); //The index should be out of bounds
    }

}

//other useless tests (getter and setters)
