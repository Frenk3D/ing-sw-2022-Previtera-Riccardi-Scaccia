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
    List<Player> playersList;


    @BeforeEach
    void setUp() {
        player = new Player("Gigio",2);
        player.setPlayerTowerColor(TowerColor.BLACK);
        player.setTeam(1);
        tableMoney = new AtomicInteger(20);
        finalMoney=0;
        towersList = new ArrayList<>();
        playersList = new ArrayList<>();
        tmpPlayer = new Player( "Gigio",2);
        tmpPlayer.setPlayerTowerColor(TowerColor.BLACK);
        tmpPlayer.setTeam(1);
        wizardsList = new ArrayList<>(4);
        playersList.add(player);
        playersList.add(tmpPlayer);
    }

    @Test
    void modifyMoney() {
        player.modifyMoney(0,tableMoney);
        assertEquals(finalMoney,player.getMoney());
        player.modifyMoney(4,tableMoney);
        finalMoney = 4;
        assertEquals(finalMoney,player.getMoney());
        assertEquals(20-4,tableMoney.get());
        player.modifyMoney(-4,tableMoney, true);
        assertEquals(20, tableMoney.get());
        player.modifyMoney(10,tableMoney);
        player.modifyMoney(-4,tableMoney, false);
        assertEquals(10+3, tableMoney.get());
        player.modifyMoney(-1,tableMoney);
        assertEquals(10+3+1, tableMoney.get());
        int tmp = player.getMoney();
        player.modifyMoney(1,tableMoney, true);
        assertEquals(14, tableMoney.get());
        assertEquals(tmp, player.getMoney());





    }

    @Test
    void hasTower() {
        assertEquals(true,player.hasTower());
        player.setHasTower(false);
        assertEquals(false, player.hasTower());
    }

    @Test
    void selectWizard() {
        tmpPlayer.selectWizard(wizardsList,3);
        player.selectWizard(wizardsList,3);
        assertEquals(player.getAssistantDeck().getWizard(),tmpPlayer.getAssistantDeck().getWizard());
    }

    @Test
    void getTeamPlayer() {
        Player p = player.getTeamPlayer(playersList);
        assertEquals(tmpPlayer, p);

    }

//to check if we have to do tests for getters and setters!!

}