package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import java.util.function.BooleanSupplier;

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

    Player p;

    Player p1;

    Cloud c;

    @BeforeEach
    void setUp() {
        p = new Player("Giggio",1,1,TowerColor.WHITE);
        p1 = new Player("Scooby Doo",2,2,TowerColor.BLACK);
        bag = new Bag();
        playersList = new ArrayList<>();
        cloudsList = new ArrayList<>();
        round = new Round();
        turn = new Turn();
        tmpRound = new Round();
        playersOrder = new ArrayList<>();
        p.setSelectedAssistant(2);
        p1.setSelectedAssistant(1);
        Cloud c = new Cloud();
        bag.addAllStudents();
        c.fillCloud(bag,2);
        playersList.add(p);
        playersList.add(p1);
        cloudsList.add(c);
        stage = RoundState.PLANNING_STATE;
        planningPhasePlayer = 0;
        numOfAssistantThrows = 0;

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

    @Test
    void initRound() {
        round.initRound(playersList,cloudsList,bag);
        assertEquals(3,cloudsList.get(0).getStudents().size());
        assertEquals(p1.getId(), round.getCurrTurn().getCurrPlayer().getId());
        assertEquals(p.getId(), round.getPlayersOrder().get(0).getId()); //because init round has next turn inside and it suddenly removes the first players
        round.nextTurn();
        assertEquals(p.getName(), round.getCurrTurn().getCurrPlayer().getName());
        assertNotEquals(p1.getId(), round.getCurrTurn().getCurrPlayer().getId());
        boolean thrown = false;

        try {
            round.getPlayersOrder().get(2);

        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }

        assertEquals(true, thrown);


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