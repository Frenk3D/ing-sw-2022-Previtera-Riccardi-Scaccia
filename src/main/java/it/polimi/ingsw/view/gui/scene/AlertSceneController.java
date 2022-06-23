package it.polimi.ingsw.view.gui.scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertSceneController {

    private final Stage stage;
    @FXML private Label messageBoxLabel;
    @FXML private Button okBtnMessageBox;

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

    private void onOkClick(Event e){
        stage.close();
    }

    public void setMessage(String message){
        messageBoxLabel.setText(message);
    }

    public void showAlert(){
        stage.showAndWait();
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
