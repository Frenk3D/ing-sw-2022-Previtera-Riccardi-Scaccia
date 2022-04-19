package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    void setUp() {
        game1 = new GameModel();
        game1.setNumOfPlayers(4);
        game1.setExpertMode(true);
        game2 = new GameModel();
        game2.setNumOfPlayers(3);
        game2.setExpertMode(false);
        p1 = new Player("Jorginho",1,1, TowerColor.WHITE);
        p2 = new Player("Ronaldinho",2,1,TowerColor.WHITE);
        p3 = new Player("Messi",3,2,TowerColor.BLACK);
        p4 = new Player("Ronaldo",4,2,TowerColor.BLACK);
        game1.addPlayer(p1);
        game1.addPlayer(p2);
        game1.addPlayer(p3);
        game1.addPlayer(p4);
        game1.start();
        game2.start();
        islandsList = new ArrayList<>();
        cloudsList = new ArrayList<>();
        charactersList= new ArrayList<it.polimi.ingsw.model.characters.Character>();
        tmpCharactersList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPlayer() {
        assertEquals(p1,game1.getPlayerById(1));
        assertEquals(p4,game1.getPlayersList().get(3));
        assertEquals(false,game1.addPlayer(p1));
    }

    @Test
    void start() {
        assertEquals(GameState.INGAME_STATE,game1.getGameState());

    }

    @Test
    void getIslandByIndex() {
        islandsList = Island.generateIslandsList();
        game1.setIslandsList(islandsList);
        assertEquals(islandsList.get(2),game1.getIslandByIndex(2));

    }

    @Test
    void getCloudByIndex() {
        cloudsList = Cloud.generateCloudsList(4);
        game1.setCloudsList(cloudsList);
        assertEquals(cloudsList.get(2),game1.getCloudByIndex(2));
    }

    @Test
    void getCharacterByIndex() {
         tmpCharactersList = game1.getCharactersList();
         assertEquals(tmpCharactersList.get(1),game1.getCharacterByIndex(1));
         assertNotEquals(null,game1.getCharacterByIndex(1));

    }

    @Test
    void checkWin() {
        // TODO: 08/04/2022
    }
    @Test
    void setNumOfPlayers(){
        game2.setNumOfPlayers(4);
        assertEquals(game1.getNumOfPlayers(),game2.getNumOfPlayers() );
    }
}