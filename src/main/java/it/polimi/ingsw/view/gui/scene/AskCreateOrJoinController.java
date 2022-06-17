package it.polimi.ingsw.view.gui.scene;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import javax.swing.event.DocumentEvent;
import java.util.Map;

public class AskCreateOrJoinController extends ViewObservable implements GenericSceneController{
    @FXML
    private Button joinBtn;

    @FXML
    private  Button createBtn;

    @FXML
    private Button createConfirmBtn;

    @FXML
    private TextField lobbyName;

    @FXML
    private TextField numOfPlayers;

    @FXML
    private CheckBox expertModeBox;

    private int checkBoxCounter;

    private boolean expertMode;

    @FXML
    public void initialize(Event event){
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,this::initialize);
        createBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,this::initialize);
        expertModeBox.addEventHandler(MouseEvent.MOUSE_CLICKED,this::initialize);
        checkBoxCounter = 0;
        expertMode = false;
    }
    @FXML
    public void onJoinBtnClick(Event event){
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        SceneController.changeRootPane(observers,event,"joinScene.fxml");
        new Thread(() -> notifyObserver(obs -> obs.onSendLobbiesRequest())).start();
    }
    @FXML
    public void onCreateBtnClick(Event event){
        joinBtn.setDisable(true);
        createBtn.setDisable(true);
        SceneController.changeRootPane(observers,event,"CreateScene.fxml");
    }
    @FXML
    public void onCheckBoxClick(Event event){
        checkBoxCounter++;
        if(checkBoxCounter % 2 != 0 || checkBoxCounter == 1){
            expertMode = true;
        }
        else{
            expertMode = false;
        }
    }
    @FXML
    public void onCreateConfirmBtnClick(Event event){
        createConfirmBtn.setDisable(true);
        String lobby = lobbyName.getText();
        int players = Integer.parseInt(numOfPlayers.getText());
        new Thread(() -> notifyObserver(obs -> obs.onSendNewLobbyRequest(lobby,players,expertMode))).start();
        SceneController.changeRootPane(observers,event,"LobbyScene.fxml");
    }





}
