package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//This is a test class for the round
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

    Player tmpPlayer;


    @BeforeEach
    void setUp() { //Sets the required attributes for testing
        p = new Player("Giggio", 1);
        p.setTeam(1);
        p.setPlayerTowerColor(TowerColor.WHITE);
        p1 = new Player("Scooby Doo", 2);
        p.setTeam(2);
        p.setPlayerTowerColor(TowerColor.BLACK);
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
        c.fillCloud(bag, 2);
        playersList.add(p);
        playersList.add(p1);
        cloudsList.add(c);
        stage = RoundState.PLANNING_STATE;
        planningPhasePlayer = 0;
        numOfAssistantThrows = 0;


    }

    @Test
    void resetRound() { //Tests the method that resets the round
        round.setStage(RoundState.ACTION_STATE);
        tmpRound.setStage(RoundState.PLANNING_STATE);
        round.setNumOfAssistantThrows(2);
        tmpRound.setNumOfAssistantThrows(1);
        tmpRound.resetRound();
        round.resetRound();
        assertEquals(round.getStage(), tmpRound.getStage());
        assertEquals(round.getNumOfAssistantThrows(), tmpRound.getNumOfAssistantThrows()); //The number of assistant throws must be equals
        assertEquals(RoundState.PLANNING_STATE, round.getStage()); //After reset the round must be in planning state
    }

    @Test
    void initRound() { //Tests the method that initializes the round
        round.initRound(playersList);
        assertEquals(3, cloudsList.get(0).getStudents().size());
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

        round.nextTurn();
        assertEquals(RoundState.PLANNING_STATE, round.getStage());


    }

    @Test
    void setNextPlayerPlanning() { //Tests the method that sets the next planning player
        round.resetRound();
        round.randomStartingPlayer(playersList);
        tmpPlayer = round.getPlanningPhasePlayer(playersList);
        round.setNextPlayerPlanning(2);
        assertNotEquals(tmpPlayer, round.getNextPlayer());
    }
}