package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class NicknameSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private TextField textNickname;
    @FXML
    private Button buttonSubmitNickname;

    @FXML
    public void initialize() {
        buttonSubmitNickname.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onLoginBtnClick);
    }

    @FXML
    private void onLoginBtnClick(Event event) {
        String nickname = textNickname.getText();
        new Thread(() -> notifyObserver(obs -> obs.onSendLoginRequest(nickname))).start();
    }

    public void setError(){
        textNickname.setText("");
    }
}
