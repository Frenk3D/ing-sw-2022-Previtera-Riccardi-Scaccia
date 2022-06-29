package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * The scene where the player choose to join or create a lobby (for his match).
 */
public class AskCreateOrJoinController extends ViewObservable implements GenericSceneController {
    @FXML
    private Button joinBtn;

    @FXML
    private Button createBtn;


    @FXML
    public void initialize() {
        joinBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onJoinBtnClick);
        createBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onCreateBtnClick);

    }

    /**
     * The user choose to join in an existing lobby.
     *
     * @param event click event.
     */
    @FXML
    public void onJoinBtnClick(Event event) {
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        new Thread(() -> notifyObserver((obs -> obs.onSendLobbiesRequest()))).start();
    }

    /**
     * The user choose to create a lobby.
     *
     * @param event click event.
     */
    @FXML
    public void onCreateBtnClick(Event event) {
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        SceneController.changeRootPane(observers, "CreateScene.fxml");
    }


}
