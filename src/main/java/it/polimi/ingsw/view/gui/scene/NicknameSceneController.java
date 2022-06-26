package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * This class implements the scene where users choose their nicknames.
 *
 */
public class NicknameSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private TextField textNickname;
    @FXML
    private Button buttonSubmitNickname;

    /**
     * The method that initialize the fxml elements
     */
    @FXML
    public void initialize() {
        buttonSubmitNickname.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onLoginBtnClick);
    }

    /**
     * The event handler that will notify to send the nickname
     * @param event the click event
     */
    @FXML
    private void onLoginBtnClick(Event event) {
        String nickname = textNickname.getText();
        new Thread(() -> notifyObserver(obs -> obs.onSendLoginRequest(nickname))).start();
    }

    /**
     * If there is an error it will reset the text field
     */
    public void setError(){
        textNickname.setText("");
    }
}
