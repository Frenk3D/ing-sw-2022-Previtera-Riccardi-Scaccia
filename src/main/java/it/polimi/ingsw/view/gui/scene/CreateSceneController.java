package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * This is the class that manage the creation of a lobby
 */
public class CreateSceneController extends ViewObservable implements GenericSceneController{


    @FXML
    private Button createConfirmBtn;

    @FXML
    private TextField lobbyNameTextField;

    @FXML
    private TextField numOfPlayersTextField;

    @FXML
    private CheckBox expertModeBox;


    @FXML
    public void initialize(){
        createConfirmBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onCreateConfirmBtnClick);
    }


    /**
     * Handle the click event of the confirm button, after insert lobby info.
     * @param event The mouse click event.
     */
    @FXML
    public void onCreateConfirmBtnClick(Event event){
        String lobby = lobbyNameTextField.getText();
        boolean expertMode = expertModeBox.isSelected();
        int players = -1;
        try {
            players = Integer.parseInt(numOfPlayersTextField.getText());
        }
        catch (Exception e){
        }

        if(!lobby.equals("") && players!=-1){
            int finalPlayers = players;
            new Thread(() -> notifyObserver(obs -> obs.onSendNewLobbyRequest(lobby, finalPlayers,expertMode))).start();
        }
        else {
            lobbyNameTextField.setText("");
            numOfPlayersTextField.setText("");
        }

    }




}
