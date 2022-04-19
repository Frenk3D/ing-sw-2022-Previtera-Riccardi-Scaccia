package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    Round round;
    Round tmpRound;
    Turn turn;
    List<Player> playersOrder;
    RoundState stage;
    int planningPhasePlayer;
    int numOfAssistantThrows;
    List<Player> playersList;
    List<Cloud> cloudsList;
    Bag bag;

    @BeforeEach
    void setUp() {
        round = new Round();
        turn = new Turn();
        tmpRound = new Round();
        playersOrder = new ArrayList<>();
        stage = RoundState.PLANNING_STATE;
        planningPhasePlayer = 0;
        numOfAssistantThrows = 0;
        playersList = new ArrayList<>();
        cloudsList = new ArrayList<>();
        bag = new Bag();
    }

    @Test
    void resetRound() {
        round.setStage(RoundState.ACTION_STATE);
        tmpRound.setStage(RoundState.END_ROUND);
        round.setNumOfAssistantThrows(2);
        tmpRound.setNumOfAssistantThrows(1);
        tmpRound.resetRound();
        round.resetRound();
        assertEquals(round.getStage(),tmpRound.getStage());
        assertEquals(round.getNumOfAssistantThrows(),tmpRound.getNumOfAssistantThrows());
        assertEquals(RoundState.PLANNING_STATE,round.getStage());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    void initRound() {
        Player p = new Player("Giggio",2,1,TowerColor.WHITE);
        Player p1 = new Player("Scooby Doo",1,2,TowerColor.BLACK);
        p.setSelectedAssistant(1);
        p1.setSelectedAssistant(2);
        Cloud c = new Cloud();
        bag.addAllStudents();
        c.fillCloud(bag,4);
        playersList.add(p);
        cloudsList.add(c);
        round.initRound(playersList,cloudsList,bag);
        assertEquals(4,cloudsList.get(0).getStudents().size());
        assertEquals(p, round.getPlayersOrder().get(0));
        assertEquals(p1, round.getPlayersOrder().get(1));
        assertNotEquals(p, round.getPlayersOrder().get(1));
        assertThrows(IndexOutOfBoundsException.class, (2) -> {round.getPlayersOrder().get()});

    }

    @Test
    void nextTurn() {
    }

    @Test
    void getNextPlayer() {
    }

    @Test
    void getCurrTurn() {
    }

    @Test
    void getStage() {
    }

    @Test
    void getNumOfAssistantThrows() {
    }

    @Test
    void getPlanningPhasePlayer() {
    }

    @Test
    void randomStartingPlayer() {
    }

    @Test
    void setNextPlayerPlanning() {
    }
}