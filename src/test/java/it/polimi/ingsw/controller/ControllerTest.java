package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller;
    CharacterParameters params;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    List<Professor> professorsList;
    Student s;
    Message message;


    @BeforeEach
    void setUp() {
        controller = new Controller("lobby1");
        professorsList = new ArrayList<>();
        p1 = new Player("Jorginho",1);
        p2 = new Player("Ronaldinho",2);
        p3 = new Player("Messi",3);
        p4 = new Player("Ronaldo",4);
        controller.configure(4, true);
        controller.addPlayer(p1);
        controller.addPlayer(p2);
        controller.addPlayer(p3);
        controller.addPlayer(p4);
        controller.chooseTeam(1,2);
        controller.chooseTeam(3,4);
        controller.chooseTowerColor(1,TowerColor.BLACK);
        controller.chooseTowerColor(3,TowerColor.WHITE);
        controller.chooseWizard(1, Wizard.OLD_WIZARD);
        controller.chooseWizard(2, Wizard.GIRL_WIZARD);
        controller.chooseWizard(3, Wizard.KING_WIZARD);
        controller.chooseWizard(4, Wizard.ASIATIC_WIZARD);
        s = new Student(PawnColor.RED);
        //AT THIS MOMENT CONTROLLER STARTS THE GAME



    }

    @Test
    void configure() {
        assertEquals(4,controller.getGame().getNumOfPlayers());
        assertNotEquals(false,controller.getGame().isExpertMode());
    }

    @Test
    void addPlayer() {
        assertEquals(4,controller.getGame().getNumOfPlayers());
        assertEquals(p1,controller.getGame().getPlayerById(1));
    }

    @Test
    void moveStudentIsland() {
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_STUDENT_STATE);
        Student s = controller.getGame().getCurrPlayer().getDashboard().getEntranceStudentByIndex(3);
        controller.moveStudentIsland(3,5);
        assertEquals(6, controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size());
        assertEquals(s,controller.getGame().getIslandByIndex(5).getStudentsList().get(1));
    }

    @RepeatedTest(5)
    void moveStudentDashboard() {
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_STUDENT_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(2));
        Student s = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3);
        int size = controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().getDashboard().getProfessorsList().size();
        assertEquals(size,controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        controller.moveStudentDashboard(3);
        assertNotEquals(size,controller.getGame().getCurrPlayer().getDashboard().getProfessorsList().size());
        assertEquals(6,controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size());
        assertNotEquals(true,s == controller.getGame().getCurrPlayer().getDashboard().getEntranceList().get(3));
    }

    @Test
    void moveMotherNature() {
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().setSelectedAssistant(2);
        controller.getGame().setMotherNaturePosition(3);
        controller.getGame().setExpertMode(false);
        controller.getGame().getCurrRound().getCurrTurn().getCurrPlayer().getSelectedAssistant().setMotherNaturePosShift(2);
        controller.moveMotherNature(5);
        assertEquals(5,controller.getGame().getMotherNaturePos());
        controller.getGame().setExpertMode(true);
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(3));
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(6);
        assertEquals(6,controller.getGame().getMotherNaturePos());
        controller.getGame().getCurrRound().getCurrTurn().setUsedCharacter(Factory.newCharacter(6));
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(8);
        assertEquals(8,controller.getGame().getMotherNaturePos());
        controller.getGame().setMotherNaturePosition(11);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(1);
        assertEquals(1,controller.getGame().getMotherNaturePos());
        controller.moveMotherNature(3);
        assertEquals(1,controller.getGame().getMotherNaturePos());
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.MOVE_MOTHER_NATURE_STATE);
        controller.moveMotherNature(8);
        assertEquals(1,controller.getGame().getMotherNaturePos());
    }

    @Test
    void takeFromCloud() {
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setStage(TurnState.CHOOSE_CLOUD_STATE);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCurrRound().getPlayersOrder().add(p3);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        controller.getGame().getCurrPlayer().getDashboard().getEntranceList().remove(0);
        int size = controller.getGame().getCurrPlayer().getDashboard().getEntranceList().size();
        assertEquals(3,controller.getGame().getCloudByIndex(0).getStudents().size());
        controller.takeFromCloud(0);
        assertEquals(0,controller.getGame().getCloudByIndex(0).getStudents().size());
        assertEquals(size +3 ,p1.getDashboard().getEntranceList().size());
    }

    @Test
    void selectAssistant() {
        controller.getGame().getCurrRound().setStage(RoundState.PLANNING_STATE);
        Player p = controller.getGame().getCurrRound().getPlanningPhasePlayer(controller.getGame().getPlayersList());
        assertEquals(10,p.getAssistantDeck().getAssistantsList().size());
        controller.selectAssistant(1);
        assertEquals(9,p.getAssistantDeck().getAssistantsList().size());
        assertNotEquals(p.getName(),controller.getGame().getCurrRound().getPlanningPhasePlayer(controller.getGame().getPlayersList()).getName());
        controller.selectAssistant(2);
        controller.selectAssistant(3);
        controller.selectAssistant(4);
        assertEquals(RoundState.ACTION_STATE,controller.getGame().getCurrRound().getStage());


    }

    @Test
    void useCharacter() {
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
        controller.useCharacter(0,params);
        assertEquals(1, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().getId());
        assertEquals(true, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().isUsed());
        controller.useCharacter(5,params); //return
        assertEquals(1, controller.getGame().getCurrRound().getCurrTurn().getUsedCharacter().getId());
    }

    @Test
    void settingState(){
        assertEquals("lobby1",(controller.getName()));

    }

    @Test
    void inGameState(){

    }

    @Test
    void update(){
        controller.getGame().setState(GameState.SETTING_STATE);
        message = new ChooseTowerColorMessage(controller.getGame().SERVERID,1,TowerColor.WHITE);
        controller.update(message);
        assertEquals(4, controller.getGame().getNumOfPlayers());

        params = new CharacterParameters();
        params.setIsland(new Island());
        params.setStudentIndex(1);
        Bag bag = new Bag();
        bag.addAllStudents();
        params.setBag(bag);
        params.setPlayer(p1);
        Character c = Factory.newCharacter(1);
        c.initCharacter(params);
        controller.getGame().getCurrRound().getCurrTurn().setCurrPlayer(p1);
        controller.getGame().getCharactersList().removeAll(controller.getGame().getCharactersList());
        controller.getGame().getCharactersList().add(c);
        controller.getGame().setState(GameState.INGAME_STATE);
        controller.getGame().getCurrRound().setStage(RoundState.ACTION_STATE);
        Message message2 = new UseCharacterMessage(p1.getId(), params);
        controller.update(message2);
        Message message4 = new TakeFromCloudMessage(controller.getGame().SERVERID, 0);
        controller.update(message4);
        assertNotEquals(2, controller.getGame().getCharactersList().size());
        Message message3 = new MoveStudentDashboardMessage(p1.getId(),0);
        controller.update(message3);


        controller.getGame().setState(GameState.FINISHED_STATE);
        controller.update(message);
        assertEquals("lobby1", controller.getName());




    }




}