package it.polimi.ingsw.controllers;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller;
    MessageCharacterParameters params;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    List<Professor> professorsList;
    Student s;
    Message message;


    @BeforeEach
    void setUp() { //we simulate a new game for each method

        controller = new Controller();
        professorsList = new ArrayList<>();
        p1 = new Player("Jorginho", 1);
        p2 = new Player("Ronaldinho", 2);
        p3 = new Player("Messi", 3);
        p4 = new Player("Ronaldo", 4);
        controller.configure(4, true);
        controller.addPlayer(p1);
        controller.addPlayer(p2);
        controller.addPlayer(p3);
        controller.addPlayer(p4);
        controller.chooseTeam(1, 2);
        controller.chooseTeam(3, 4);
        controller.chooseTowerColor(1, TowerColor.BLACK);
        controller.chooseTowerColor(3, TowerColor.WHITE);

        Controller controllertmp = controller;
        controller.chooseTowerColor(5, TowerColor.WHITE); //some limit cases not correct
        controller.chooseTowerColor(1, TowerColor.WHITE);
        controller.chooseTowerColor(1, null);
        assertEquals(controllertmp, controller);

        controller.chooseWizard(1, Wizard.SAGE);
        controller.chooseWizard(2, Wizard.WITCH);
        controller.chooseWizard(3, Wizard.KING);
        controller.chooseWizard(4, Wizard.ASIATIC); //some limit cases not correct
        controller.chooseWizard(7, Wizard.ASIATIC);
        controller.chooseWizard(3, null);
        assertEquals(controllertmp, controller);

        s = new Student(PawnColor.RED);
        //AT THIS MOMENT CONTROLLER STARTS THE GAME


    }

    @Test
    void configure() { //we test if the init is correct
        assertEquals(4, controller.getGame().getNumOfPlayers());
        assertNotEquals(false, controller.getGame().isExpertMode());
    }

    @Test
    void addPlayer() { //we test if the method add player works correctly
        assertEquals(4, controller.getGame().getNumOfPlayers());
        assertEquals(p1, controller.getGame().getPlayerById(1));
    }

    @RepeatedTest(5)
    void moveStudentIsland() { //we test some cases of movestudentisland
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_STUDENT_STATE);
        Student s = controller.getGame().getCurrPlayer().getDashboard().getEntranceStudentByIndex(3);
        controller.moveStudentIsland(3, 5);
        assertEquals(6, controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size());
        if (controller.getGame().getMotherNaturePos() == 5 || controller.getGame().getMotherNaturePos() == 11) {
            assertEquals(s, controller.getGame().getIslandByIndex(5).getStudentsList().get(0));
        } else {
            assertEquals(s, controller.getGame().getIslandByIndex(5).getStudentsList().get(1));
        }
    }

    @RepeatedTest(5)
    void moveStudentDashboard() { //test of some cases of movestudentdashboard action in expert mode
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_STUDENT_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(2));
        Student s = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3);
        int size = controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().getDashboard().getProfessorsList().size();
        assertEquals(size, controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        controller.moveStudentDashboard(3);
        assertNotEquals(size, controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        assertEquals(6, controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size());
        assertNotEquals(true, s == controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3));
    }

    @Test
    void moveMotherNature() { //we test some cases of movemothernature, in expertmode or not etc...
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().setSelectedAssistant(2);
        controller.getGame().setMotherNaturePosition(3);
        controller.getGame().setExpertMode(false);
        controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().getSelectedAssistant().setMotherNaturePosShift(2);
        controller.moveMotherNature(5);
        assertEquals(5, controller.getGame().getMotherNaturePos());
        controller.getGame().setExpertMode(true);
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(3));
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(6);
        assertEquals(6, controller.getGame().getMotherNaturePos());
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(6));
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(8);
        assertEquals(8, controller.getGame().getMotherNaturePos());
        controller.getGame().setMotherNaturePosition(11);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(1);
        assertEquals(1, controller.getGame().getMotherNaturePos());
        controller.moveMotherNature(3);
        assertEquals(1, controller.getGame().getMotherNaturePos());
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(8);
        assertEquals(1, controller.getGame().getMotherNaturePos());
    }

    @Test
    void takeFromCloud() { //we test the takefromcloud action (if it is not the last turn)
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.CHOOSE_CLOUD_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().getPlayersOrder().add(p3);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        int size = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size();
        assertEquals(3, controller.getGame().getCloudByIndex(0).getStudents().size());
        controller.takeFromCloud(0);
        assertEquals(0, controller.getGame().getCloudByIndex(0).getStudents().size());
        assertEquals(size + 3, p1.getDashboard().getEntranceList().size());
    }

    @Test
    void selectAssistant() { //test of the action selectassistant
        controller.getGame().getCurrRound().setStage(RoundState.PLANNING_STATE);
        Player p = controller.getGame().getCurrRound().getPlanningPhasePlayer(controller.getGame().getPlayersList());
        assertEquals(10, p.getAssistantDeck().getAssistantsList().size());
        controller.selectAssistant(1);
        assertEquals(9, p.getAssistantDeck().getAssistantsList().size());
        assertNotEquals(p.getName(), controller.getGame().getCurrRound().getPlanningPhasePlayer(controller.getGame().getPlayersList()).getName());
        controller.selectAssistant(2);
        controller.selectAssistant(3);
        controller.selectAssistant(4);
        assertEquals(RoundState.ACTION_STATE, controller.getGame().getCurrRound().getStage());


    }

    @Test
    void useCharacter() { //test of the use of a character
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        CharacterParameters params = new CharacterParameters();
        params.setIsland(new Island());
        params.setStudentIndex(1);
        Bag bag = new Bag();
        bag.addAllStudents();
        params.setBag(bag);
        Character c = Factory.newCharacter(1);
        ((Characters1and7and11) c).initCharacter(params);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCharactersList().removeAll(controller.getGame().getCharactersList());
        controller.getGame().getCharactersList().add(c);
        controller.useCharacter(1, params);
        assertEquals(1, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().getId());
        assertEquals(true, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().isUsed());
        controller.useCharacter(5, params); //return
        assertEquals(1, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().getId());
    }


    @Test
    void inGameState() {
    //already tested in update (below from here)
    }

    @RepeatedTest(5)
    void update() { //test of the manage of messages
        controller.getGame().setState(GameState.SETTING_STATE);
        message = new ChooseTowerColorMessage(1, TowerColor.WHITE);
        controller.update(message);
        assertEquals(4, controller.getGame().getNumOfPlayers());

        Message message6 = new ChooseTeamMessage(1, 3);
        controller.update(message6);
        assertEquals(4, controller.getGame().getNumOfPlayers());

        Message message12 = new ChooseTeamMessage(1, 5);
        controller.update(message12);
        assertEquals(4, controller.getGame().getNumOfPlayers()); //nothing changes

        params = new MessageCharacterParameters();
        params.setStudentIndex(1);
        List<Character> characterList = new ArrayList<>();
        Character c = Factory.newCharacter(1);
        CharacterParameters initParameters = new CharacterParameters();
        Bag bag = new Bag();
        bag.addAllStudents();
        initParameters.setBag(bag);
        c.initCharacter(initParameters);
        characterList.add(c);
        controller.getGame().setCharactersList(characterList);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCharactersList().removeAll(controller.getGame().getCharactersList());
        controller.getGame().getCharactersList().add(c);
        controller.getGame().setState(GameState.INGAME_STATE);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        Message message2 = new UseCharacterMessage(p1.getId(), params);
        controller.update(message2);


        Message message4 = new TakeFromCloudMessage(1, 0);
        controller.update(message4);
        assertNotEquals(2, controller.getGame().getCharactersList().size());

        Message message9 = new SelectAssistantMessage(p1.getId(), 0);
        controller.update(message9);
        GameModel game2 = controller.getGame();
        assertEquals(controller.getGame(), game2); //nothing change because it is not correct

        Message message10 = new MoveMotherNatureMessage(p1.getId(), 0);
        controller.update(message10);
        assertEquals(controller.getGame(), game2); //nothing change because it is not correct

        Message message11 = new ChooseTeamMessage(p1.getId(), 0);
        controller.update(message11);
        assertEquals(controller.getGame(), game2); //nothing change because it is not correct


        Message message3 = new MoveStudentDashboardMessage(p1.getId(), 0);
        controller.update(message3);

        Message message5 = new MoveStudentIslandMessage(p1.getId(), 0, 0);
        controller.update(message5);

        controller.getGame().setState(GameState.FINISHED_STATE);
        controller.update(message);

        controller.getGame().setState(GameState.SETTING_STATE);
        message = new ChooseWizardMessage(1, Wizard.ASIATIC);
        controller.update(message);
        assertEquals(4, controller.getGame().getNumOfPlayers());

        controller.getGame().setState(GameState.SETTING_STATE);
        message = new UseCharacterMessage(1, params);
        controller.update(message);
        assertNotEquals(3, controller.getGame().getNumOfPlayers());
    }

    @Test
    void NewTests() { //some limit cases tests
        controller.getGame().setState(GameState.LOGIN_STATE);
        assertEquals(true, controller.isOpen());
        assertEquals(controller.getActualNumOfPlayers(), controller.getNumOfPlayer());
        assertEquals(controller.getExpertMode(), controller.getGame().isExpertMode());
        controller.setServer(new Server(0));
        Server s = new Server(2);
        assertNotEquals(s, controller.getServer());
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        assertFalse(controller.checkWin(false));
        controller.getGame().getCurrPlayer().getDashboard().setTowersList(new ArrayList<>());
        assertEquals(false, controller.checkWin(true));
        assertEquals(controller.getGame().getCurrPlayer().getId(), controller.getWinner());

        p1.getAssistantDeck().getAssistantsList().removeAll(p1.getAssistantDeck().getAssistantsList());
        assertEquals(true, controller.checkWin(true));


        controller.getGame().setIslandsList(new ArrayList<>());
        assertEquals(true, controller.checkWin(false)); //test win on finished islands
        p1.getDashboard().setTowersList(new ArrayList<>());
        assertEquals(true, controller.checkWin(false)); //test win of finished towers


    }

    @RepeatedTest(5)
    void moveStudentDashboardNormal() {  //it is a test on normal mode
        controller.getGame().setExpertMode(false);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_STUDENT_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(2));
        Student s = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3);
        int size = controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().getDashboard().getProfessorsList().size();
        assertEquals(size, controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        controller.moveStudentDashboard(3);
        assertNotEquals(size, controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        assertEquals(6, controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size());
        assertNotEquals(true, s == controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3));

    }

    @Test
    void takeFromCloudFinished() { //take from cloud when a round is finished
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.CHOOSE_CLOUD_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().getPlayersOrder().add(p3);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrRound().getPlayersOrder().remove(0); //now it is 0
        controller.getGame().getCurrRound().setLastRound(true);
        assertEquals(0, controller.getGame().getCurrRound().getPlayersOrder().size());
        int size = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size();
        assertEquals(3, controller.getGame().getCloudByIndex(0).getStudents().size()); //now we don't take from cloud
        assertEquals(p1, controller.getGame().getCurrPlayer());
        controller.takeFromCloud(0);
        assertEquals(3, controller.getGame().getCloudByIndex(0).getStudents().size()); //round ended we fill clouds again instead of the takefromcloud upside
        assertEquals(size + 3, p1.getDashboard().getEntranceList().size());
    }
}