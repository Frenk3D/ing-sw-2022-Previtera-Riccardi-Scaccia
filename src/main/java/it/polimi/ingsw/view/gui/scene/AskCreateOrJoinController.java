package it.polimi.ingsw.view.gui.scene;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


import javax.swing.event.DocumentEvent;
import java.util.Map;

public class AskCreateOrJoinController extends ViewObservable implements GenericSceneController{
    @FXML
    private Button joinBtn;

    @FXML
    private  Button createBtn;



    @FXML
    public void initialize(){
        joinBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onJoinBtnClick);
        createBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onCreateBtnClick);

    }
    @FXML
    public void onJoinBtnClick(Event event){
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        new Thread(() -> notifyObserver((obs -> obs.onSendLobbiesRequest()))).start();
    }
    @FXML
    public void onCreateBtnClick(Event event){
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        SceneController.changeRootPane(observers,"CreateScene.fxml");
    }


}
