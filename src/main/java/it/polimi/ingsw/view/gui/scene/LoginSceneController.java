package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.Map;

/**
 * This class implements the scene where users choose their nicknames.
 */
public class LoginSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private TextField serverAddressField;
    @FXML
    private TextField serverPortField;
    @FXML
    private Button connectBtn;


    private final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    @FXML
    public void initialize() {
        connectBtn.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onConnectBtnClick);
    }



    @FXML
    private void onConnectBtnClick(Event event){
        String address = serverAddressField.getText();
        String port = serverPortField.getText();

        boolean isValidIpAddress = ClientController.isValidIpAddress(address);
        boolean isValidPort = ClientController.isValidPort(port);

        serverAddressField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidIpAddress);
        serverPortField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidPort);

        if (isValidIpAddress && isValidPort) {
            connectBtn.setDisable(true);
            serverAddressField.setDisable(true);
            serverPortField.setDisable(true);
            Map<String, String> serverInfo = Map.of("address", address, "port", port);
            new Thread(() -> notifyObserver(obs -> obs.onAskServerInfo(serverInfo))).start();
        }
    }

    @FXML
    public void errorInServer(){
        connectBtn.setDisable(false);
        serverAddressField.setDisable(false);
        serverPortField.setDisable(false);
        serverAddressField.setText("localhost");
        serverPortField.setText("3333");
    }
}

