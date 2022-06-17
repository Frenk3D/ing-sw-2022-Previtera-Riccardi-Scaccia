package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;
import java.util.List;
import java.util.Map;

//For javaFX? use SceneBuilder?
import javafx.application.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import it.polimi.ingsw.view.gui.scene.*;

/**
 * This class implements all methods of View. Is used for the Graphic User Interface.
 */
public class Gui extends View {

    private static final String STR_ERROR = "ERROR";
    private static final String MENU_SCENE_FXML = "menu_scene.fxml";



    @Override
    public void onAskServerInfo() {

    }
    @Override
    public void onSendLoginRequest() {

    }

    @Override
    public void onAskCreateOrJoin() {

    }


   /* public void sendNewLobbyRequest(){

    }


    public void sendLobbiesRequest(){

    }*/

    @Override
    public void onSendChooseLobby(List<Lobby> lobbylist){

    }

    @Override
    public void onSendChooseTeam(Map<String,Integer> availablePlayers){

    }

    @Override
    public void onSendChooseTowerColor(List<TowerColor> availableTowerColors){

    }

    @Override
    public void onSendChooseWizard(List<Wizard> availableWizards){

    }

    @Override
    public void onSendSelectAssistant(List<ReducedAssistant> assistantList){

    };

    @Override
    public void onAskWhereToMoveStudent() {

    }

    public void sendMoveStudentIsland(){}

    public void sendMoveStudentDashboard(){}

    /**
     * @param islandList 
     * @param selectedAssistant
     */
    @Override
    public void onSendMoveMotherNature(List<ReducedIsland> islandList, ReducedAssistant selectedAssistant) {

    }

    /**
     * @param cloudList 
     */
    @Override
    public void onSendChooseCloud(List<ReducedCloud> cloudList) {

    }

    /**
     * @param characterId
     */
    @Override
    public void onAskCharacterParameters(int characterId) {

    }

    /**
     * @param toShow
     */
    @Override
    public void onShow(Object toShow) {

    }

    @Override
    public void updateClientGameModel(ClientGameModel clientGameModel) {

    }

    @Override
    public void onShowGame(ClientGameModel clientGameModel){

    }
}
