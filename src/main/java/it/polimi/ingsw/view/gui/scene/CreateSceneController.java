package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class CreateSceneController extends ViewObservable implements GenericSceneController{


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
    public void initialize(){
        expertModeBox.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onCheckBoxClick);
        checkBoxCounter = 0;
        expertMode = false;
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
        //createConfirmBtn.setDisable(true);
        String lobby = lobbyName.getText();
        int players = Integer.parseInt(numOfPlayers.getText());
        new Thread(() -> notifyObserver(obs -> obs.onSendNewLobbyRequest(lobby,players,expertMode))).start();
        //SceneController.changeRootPane(observers,"LobbyScene.fxml");
    }




}
