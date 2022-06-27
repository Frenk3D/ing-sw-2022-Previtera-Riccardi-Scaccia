package it.polimi.ingsw.view.gui.scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Scene that show a message to the user.
 */
public class AlertSceneController {

    private final Stage stage;
    @FXML private Label messageBoxLabel;
    @FXML private Button okBtnMessageBox;

    /**
     * Constructor of the controller and the stage
     */
    public AlertSceneController(){
        stage = new Stage();
        stage.setWidth(550);
        stage.setHeight(250);
        stage.setResizable(false); //maybe to resize for bigger message?
        stage.setTitle("Message");
        stage.initModality(Modality.APPLICATION_MODAL);
    }


    @FXML
    public void initialize(){
        okBtnMessageBox.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onOkClick);
    }

    /**
     * Closes the modal window if press ok.
     * @param e mouse click
     */
    private void onOkClick(Event e){
        stage.close();
    }

    /**
     * @param message the message to show
     */
    public void setMessage(String message){
        messageBoxLabel.setText(message);
    }

    /**
     * Show the pop-up with the message blocking the window below.
     */
    public void showAlert(){
        try{
        stage.showAndWait();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    /**
     * @param scene the alert scene.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
