package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.ClientState;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Map;

//For javaFX used SceneBuilder
import javafx.application.*;
import it.polimi.ingsw.view.gui.scene.*;


/**
 * This class implements all methods of View. Is used for the Graphic User Interface. It is an implementation of the {@link View}.
 */
public class Gui extends View {


    private ClientController clientController;

    public Gui(ClientController clientController){
        this.clientController=clientController;
    }

    @Override
    public void onAskServerInfo() {
        LoginSceneController controller = (LoginSceneController) SceneController.getActiveController();
        Platform.runLater(controller::errorInServer);
        SceneController.showMessage("Error in connection, try again");
    }
    @Override
    public void onSendLoginRequest() {
        Platform.runLater(() -> {
            if(SceneController.getCurrFxml().equals("NicknameScene.fxml")){
                NicknameSceneController controller = (NicknameSceneController) SceneController.getActiveController();
                controller.setError();
            }
            else {
                SceneController.changeRootPane(observers, "NicknameScene.fxml");
            }
        });
    }

    @Override
    public void onAskCreateOrJoin() {
        Platform.runLater(() -> {
            SceneController.changeRootPane(observers, "AskCreateOrJoinScene.fxml");
            SceneController.getMainStage().setMaximized(false);
            SceneController.getMainStage().setWidth(900);
            SceneController.getMainStage().setHeight(600);
        });
    }

    @Override
    public void onSendChooseLobby(List<Lobby> lobbylist){
        Platform.runLater(() -> {
            if(!SceneController.getCurrFxml().equals("JoinScene.fxml")){
                SceneController.changeRootPane(observers, "JoinScene.fxml");
            }
            JoinSceneController controller = (JoinSceneController) SceneController.getActiveController();
            controller.updateList(lobbylist);
        });
    }

    @Override
    public void onSendChooseTeam(Map<String,Integer> availablePlayers){
        Platform.runLater(() -> {
            if(!SceneController.getCurrFxml().equals("ChooseTeamScene.fxml")){
                SceneController.changeRootPane(observers, "ChooseTeamScene.fxml");
            }
            ChooseTeamSceneController controller = (ChooseTeamSceneController) SceneController.getActiveController();
            controller.updateList(availablePlayers);
        });

    }

    @Override
    public void onSendChooseTowerColor(List<TowerColor> availableTowerColors){
        Platform.runLater(() -> {
            if(!SceneController.getCurrFxml().equals("ChooseTowerColorScene.fxml")){
                SceneController.changeRootPane(observers, "ChooseTowerColorScene.fxml");
            }
            ChooseTowerColorSceneController controller = (ChooseTowerColorSceneController) SceneController.getActiveController();
            controller.updateList(availableTowerColors);
        });
    }

    @Override
    public void onSendChooseWizard(List<Wizard> availableWizards){
        Platform.runLater(() -> {
            if(!SceneController.getCurrFxml().equals("ChooseWizardScene.fxml")){
                SceneController.changeRootPane(observers, "ChooseWizardScene.fxml");
            }
            ChooseWizardSceneController controller = (ChooseWizardSceneController) SceneController.getActiveController();
            controller.updateList(availableWizards);
        });
    }

    @Override
    public void onSendSelectAssistant(){
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.requestedChooseAssistant();
            }
        });
    }

    @Override
    public void onAskWhereToMoveStudent() {
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.requestedMoveStudent();
            }
        });
    }

    @Override
    public void onSendMoveMotherNature() {
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.requestedMoveMotherNature();
            }
        });
    }

    @Override
    public void onSendChooseCloud() {
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.requestedChooseCloud();
            }
        });
    }

    /**
     * @param characterId
     */
    @Override
    public void onAskCharacterParameters(int characterId) {
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.requestedCharacterParameters(characterId);
            }
        });
    }

    /**
     * @param toShow
     */
    @Override
    public void onShow(Object toShow) {
        ClientState state = clientController.getClientState();
        if(state != ClientState.WAITING_FOR_YOUR_TURN){
            SceneController.showMessage((String) toShow);
        }
        else {
            Platform.runLater(() -> {
                if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                    TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                    controller.manageGameInfo((String) toShow);
                }
            });
        }
    }

    @Override
    public void updateClientGameModel(ClientGameModel clientGameModel) {
        Platform.runLater(() -> {
            if (!SceneController.getCurrFxml().equals("TableScene.fxml")) {
                SceneController.changeRootPane(observers, "TableScene.fxml");
                SceneController.getMainStage().setMaximized(true);
            }
        });
    }

    @Override
    public void onShowGame(ClientGameModel clientGameModel){
        if(clientGameModel.getIslandList()!=null){
        Platform.runLater(() -> {
            if (SceneController.getCurrFxml().equals("TableScene.fxml")) {
                TableSceneController controller = (TableSceneController) SceneController.getActiveController();
                controller.updateGraphics(clientGameModel);
            }
        }); }
    }

    @Override
    public void onShowPlayerJoin(List<String> playersList) {
        Platform.runLater(() -> {
            if(!SceneController.getCurrFxml().equals("LobbyScene.fxml")){
                SceneController.changeRootPane(observers, "LobbyScene.fxml");
            }
            LobbySceneController controller = (LobbySceneController) SceneController.getActiveController();
            controller.setPlayersLabel(playersList);
        });
    }

    @Override
    public void onShowChosenTeam(String toShow) {
        Platform.runLater(() -> {
            if(SceneController.getCurrFxml().equals("ChooseTeamScene.fxml")) {
                ChooseTeamSceneController controller = (ChooseTeamSceneController) SceneController.getActiveController();
                controller.disableChoice(toShow);
            }
        });
    }
}
