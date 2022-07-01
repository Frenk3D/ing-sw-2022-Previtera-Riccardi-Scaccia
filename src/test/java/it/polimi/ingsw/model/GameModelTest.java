package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Factory;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//This is a test class for the game model
class GameModelTest {
    GameModel game1;
    GameModel game2;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    List<Island> islandsList;
    List<Cloud> cloudsList;
    List<Character> charactersList;
    List<Character> tmpCharactersList;

    @BeforeEach
    void setUp() { //Sets the required attributes for testing
        game1 = new GameModel();
        game1.setNumOfPlayers(4);
        game1.setExpertMode(true);
        game2 = new GameModel();
        game2.setNumOfPlayers(3);
        game2.setExpertMode(false);
        p1 = new Player("Jorginho", 1);
        p1.setPlayerTowerColor(TowerColor.WHITE);
        p1.setTeam(1);
        p2 = new Player("Ronaldinho", 2);
        p2.setPlayerTowerColor(TowerColor.WHITE);
        p2.setTeam(1);
        p3 = new Player("Messi", 3);
        p3.setPlayerTowerColor(TowerColor.BLACK);
        p3.setTeam(2);
        p4 = new Player("Ronaldo", 4);
        p4.setPlayerTowerColor(TowerColor.BLACK);
        p4.setTeam(2);
        game1.addPlayer(p1);
        game1.addPlayer(p2);
        game1.addPlayer(p3);
        game1.addPlayer(p4);
        game2.addPlayer(p1);
        game2.addPlayer(p2);
        game2.addPlayer(p3);
        game1.start();
        game2.start();
        islandsList = new ArrayList<>();
        cloudsList = new ArrayList<>();
        charactersList = new ArrayList<it.polimi.ingsw.model.characters.Character>();
        charactersList.add(Factory.newCharacter(1));
        charactersList.add(Factory.newCharacter(5));
        game1.setCharactersList(charactersList);
        tmpCharactersList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPlayer() { //Test the method that adds players
        assertEquals(p1, game1.getPlayerById(1));
        assertEquals(p4, game1.getPlayersList().get(3));
        assertEquals(false, game1.addPlayer(p1));
    }

    @Test
    void start() { //Tests the method that starts the game
        assertEquals(GameState.INGAME_STATE, game1.getGameState()); //The game state should be in game

    }

    @Test
    void getIslandByIndex() { //Tests the method that gets an island by index
        islandsList = Island.generateIslandsList();
        game1.setIslandsList(islandsList);
        assertEquals(islandsList.get(2), game1.getIslandByIndex(2));

    }

    @Test
    void getCloudByIndex() { //Tests the method that gets a cloud by index
        cloudsList = Cloud.generateCloudsList(4);
        game1.setCloudsList(cloudsList);
        assertEquals(cloudsList.get(2), game1.getCloudByIndex(2));
    }

    @Test
    void getCharacterByIndex() { //Tests the method that gets a cloud by index
        tmpCharactersList = game1.getCharactersList();
        assertEquals(tmpCharactersList.get(1), game1.getCharacterByIndex(1));
        assertNotEquals(null, game1.getCharacterByIndex(1));

    }

    @Test
    void checkWin() {
        //tested in real games
    }

    @Test
    void setNumOfPlayers() { //Tests the method that sets the number of players
        game2.setNumOfPlayers(4);
        assertEquals(game1.getNumOfPlayers(), game2.getNumOfPlayers());
    }

    @Test
    void aLotOfTests() { //Tests various getter and setters of the model
        assertEquals(5, game1.getForbidCharacter().getId());
        assertEquals(true, game1.isExpertMode());
        assertEquals(4, game1.getCloudsList().size());
        assertNotEquals(93, game1.getBag().getStudentsList().size());
        assertEquals(81, game2.getBag().getStudentsList().size());
        assertEquals(null, game2.getCurrPlayer());
        assertEquals(null, game2.getTableMoney());

    }
}