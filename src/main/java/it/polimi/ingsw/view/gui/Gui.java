package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;
import java.util.List;
import java.util.Map;

//For javaFX? use SceneBuilder?
import javafx.application.*;
import it.polimi.ingsw.view.gui.scene.*;




/**
 * This class implements all methods of View. Is used for the Graphic User Interface.
 */
public class Gui extends View {

    private static final String STR_ERROR = "ERROR";
    private static final String MENU_SCENE_FXML = "menu_scene.fxml";


    @Override
    public void onAskServerInfo() {
        LoginSceneController controller = (LoginSceneController) SceneController.getActiveController();
        Platform.runLater(controller::errorInServer);
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
        Platform.runLater(() -> SceneController.changeRootPane(observers, "AskCreateOrJoinScene.fxml"));
    }


   /* public void sendNewLobbyRequest(){

    }


    public void sendLobbiesRequest(){

    }*/

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

    /* DO DELETE
   @Override
    public void askNickname() {
        Platform.runLater(() -> SceneController.changeRootPane(observers, "login_scene.fxml"));
    }


    @Override
    public void askMoveFx(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.MOVE_FX);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        if (!nicknameAccepted || !connectionSuccessful) {
            if (!nicknameAccepted && connectionSuccessful) {
                Platform.runLater(() -> {
                    SceneController.showAlert(STR_ERROR, "Nickname already taken.");
                    SceneController.changeRootPane(observers, "login_scene.fxml");
                });
            } else {
                Platform.runLater(() -> {
                    SceneController.showAlert(STR_ERROR, "Could not contact server.");
                    SceneController.changeRootPane(observers, MENU_SCENE_FXML);
                });
            }
        }
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        Platform.runLater(() -> SceneController.showAlert("Info Message", genericMessage));
    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String text) {
        Platform.runLater(() -> {
            SceneController.showAlert("GAME OVER", "The player " + nicknameDisconnected + " disconnected.");
            SceneController.changeRootPane(observers, MENU_SCENE_FXML);
        });
    }


    @Override
    public void showLobby(List<String> nicknameList, int maxPlayers) {
        LobbySceneController lsc;

        try {
            lsc = (LobbySceneController) SceneController.getActiveController();
            lsc.setNicknames(nicknameList);
            lsc.setMaxPlayers(maxPlayers);
            Platform.runLater(lsc::updateValues);
        } catch (ClassCastException e) {
            lsc = new LobbySceneController();
            lsc.addAllObservers(observers);
            lsc.setNicknames(nicknameList);
            lsc.setMaxPlayers(maxPlayers);
            LobbySceneController finalLsc = lsc;
            Platform.runLater(() -> SceneController.changeRootPane(finalLsc, "lobby_scene.fxml"));
        }
    }

    @Override
    public void askEnableEffect(boolean forceApply) {
        if (forceApply) {
            // send to server the request to apply my effect.
            Platform.runLater(() -> notifyObserver(obs -> obs.onUpdateEnableEffect(true)));
        } else {
            BoardSceneController bsc = getBoardSceneController();
            Platform.runLater(() -> bsc.enableEffectControls(true));
        }
    }


    @Override
    public void showWinMessage(String winner) {
        Platform.runLater(() -> {
            SceneController.showWin(winner);
            SceneController.changeRootPane(observers, MENU_SCENE_FXML);
        });
    }

    }
*/

    /**
     * @param toShow
     */
    @Override
    public void onShow(Object toShow) {
        SceneController.showMessage((String) toShow);
    }

    @Override
    public void updateClientGameModel(ClientGameModel clientGameModel) {

    }

    @Override
    public void onShowGame(ClientGameModel clientGameModel){

    }
}
