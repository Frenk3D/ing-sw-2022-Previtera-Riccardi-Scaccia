package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    AtomicInteger tableMoney;
    int finalMoney;
    List<Tower> towersList;
    Player tmpPlayer;
    List<Integer> wizardsList;


    @BeforeEach
    void setUp() {
        player = new Player("Gigio",2,1, TowerColor.BLACK);
        tableMoney = new AtomicInteger(20);
        finalMoney=0;
        towersList = new ArrayList<>();
        tmpPlayer = new Player( "Gigio",2,1,TowerColor.BLACK);
        wizardsList = new ArrayList<>(4);
    }

    @Test
    void modifyMoney() {
        player.modifyMoney(0,tableMoney);
        assertEquals(finalMoney,player.getMoney());
        player.modifyMoney(4,tableMoney);
        finalMoney = 4;
        assertEquals(finalMoney,player.getMoney());
        assertEquals(20-4,tableMoney.get());


    }

    @Test
    void hasTower() {
        assertEquals(true,player.hasTower());
    }

    @Test
    void selectWizard() {
        tmpPlayer.selectWizard(wizardsList,3);
        player.selectWizard(wizardsList,3);
        assertEquals(player.getAssistantDeck().getWizard(),tmpPlayer.getAssistantDeck().getWizard());
    }
    //to check if we have to do tests for getters and setters!!

}