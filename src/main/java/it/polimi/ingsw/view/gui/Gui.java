package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.*;

//import javafx.application.Platform;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements all methods of View. Is used for the Graphic User Interface.
 */
public class Gui extends ViewObservable implements View {

    private static final String STR_ERROR = "ERROR";
    private static final String MENU_SCENE_FXML = "menu_scene.fxml";

    @Override
    public void askPlayerInfo() {

    }

    @Override
    public void askServerConfig() {

    }

    @Override
    public void askNewOrJoinGame() {

    }

    @Override
    public void showAvailableLobbies(List<Lobby> lobbiesList) {

    }

    @Override
    public void askRequestedLobby(List<Lobby> lobbiesList) {

    }

    @Override
    public void askTeam(List<Player> playersList) {

    }

    @Override
    public void showAvailableTeams(Map<String, Integer> players) {

    }

    @Override
    public void askTowerColor() {

    }

    @Override
    public void showAvailableTowerColors(List<TowerColor> availableTowerColors) {

    }

    @Override
    public void askWizard() {

    }

    @Override
    public void showAvailableWizards(List<Wizard> availableWizards) {

    }

    @Override
    public void showTable(List<Island> islandsList, List<Cloud> cloudsList, int motherNaturePos) {

    }

    @Override
    public void showThrownAssistant(Assistant thrownAssistant, int playerId) {

    }

    @Override
    public void showDashboard(Dashboard dashboard, int playerId) {

    }

    @Override
    public void showCharacterTable(List<Character> charactersList, AtomicInteger tableMoney, Map<Player, AtomicInteger> numOfMoneyMap) {

    }

    @Override
    public void showAssistantsList(List<Assistant> assistantsList) {

    }

    @Override
    public void showThrownAssistant(Assistant selectedAssistant) {

    }

    @Override
    public void askStudentToMoveIsland(List<Island> entranceStudentsList, List<Island> islandsList) {

    }

    @Override
    public void askStudentToMoveDashboard(List<Student> entranceStudentsList) {

    }

    @Override
    public void askMotherNatureMove(List<Island> islandsList) {

    }

    @Override
    public void askCloudExtraction(List<Cloud> cloudsList) {

    }

    @Override
    public void askSelectedAssistant(List<Assistant> assistantsList) {

    }

    @Override
    public void askUsedCharacter(List<Character> charactersList) {

    }

    @Override
    public void showDisconnectionMessage(String text) {

    }

    @Override
    public void showErrorAndExit(String error) {

    }

    @Override
    public void showMatchInfo(List<Player> playersList, List<Island> islandsList, List<Cloud> cloudsList, List<Assistant> assistantsList, int motherNaturePos, AtomicInteger tableMoney, List<Character> charactersList, boolean expertMode) {

    }

    @Override
    public void showWinMessage(String winner) {

    }

    /* DO DELETE
   @Override
    public void askNickname() {
        Platform.runLater(() -> SceneController.changeRootPane(observers, "login_scene.fxml"));
    }

    @Override
    public void askPlayersNumber() {
        PlayersNumberSceneController pnsc = new PlayersNumberSceneController();
        pnsc.addAllObservers(observers);
        pnsc.setPlayersRange(2, 3);
        Platform.runLater(() -> SceneController.changeRootPane(pnsc, "players_number_scene.fxml"));
    }

    @Override
    public void askInitWorkerColor(List<Color> colors) {
        ColorSceneController csc = new ColorSceneController();
        csc.addAllObservers(observers);
        csc.setAvailableColors(colors);
        Platform.runLater(() -> SceneController.changeRootPane(csc, "color_scene.fxml"));
    }

    @Override
    public void askGod(List<ReducedGod> gods, int request) {
        GodsSceneController gsc = new GodsSceneController();
        gsc.addAllObservers(observers);
        gsc.setGods(gods);
        gsc.setNumberRequest(request);
        Platform.runLater(() -> SceneController.changeRootPane(gsc, "gods_scene.fxml"));
    }

    @Override
    public void askInitWorkersPositions(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(2);
        bsc.setSpaceClickType(MessageType.INIT_WORKERSPOSITIONS);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    @Override
    public void askMovingWorker(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.PICK_MOVING_WORKER);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    @Override
    public void askMove(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.MOVE);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    @Override
    public void askBuild(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.BUILD);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    @Override
    public void askMoveFx(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.MOVE_FX);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
    }

    @Override
    public void askBuildFx(List<Position> positions) {
        BoardSceneController bsc = getBoardSceneController();
        bsc.setAvailablePositionClicks(1);
        bsc.setSpaceClickType(MessageType.BUILD_FX);
        Platform.runLater(() -> bsc.setEnabledSpaces(positions));
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
    public void showErrorAndExit(String errorMsg) {
        Platform.runLater(() -> {
            SceneController.showAlert(STR_ERROR, errorMsg);
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
    public void showMatchInfo(List<String> players, List<ReducedGod> gods, List<Color> colors, String activePlayer) {
        BoardSceneController bsc = getBoardSceneController();
        Platform.runLater(() -> bsc.updateMatchInfo(players, gods, colors, activePlayer));
    }

    @Override
    public void askFirstPlayer(List<String> nicknameList, List<ReducedGod> gods) {
        FirstPlayerSceneController fpsc = new FirstPlayerSceneController();
        fpsc.addAllObservers(observers);
        fpsc.setNicknames(nicknameList);
        fpsc.setGods(gods);
        Platform.runLater(() -> SceneController.changeRootPane(fpsc, "first_player_scene.fxml"));
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

}