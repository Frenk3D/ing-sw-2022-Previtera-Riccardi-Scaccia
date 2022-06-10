package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.event.DocumentEvent;
import java.util.Map;

/**
 * This class implements the scene where users choose their nicknames.
 */
public class LoginSceneController extends ViewObservable implements GenericSceneController {
//
    @FXML
    private TextField serverAddressField;
    @FXML
    private TextField serverPortField;
    @FXML
    private Button connectBtn;
    @FXML
    private Button joinBtn;
    @FXML
    private Button createBtn;

    private final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    @FXML
    public void initialize() {
        connectBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onConnectBtnClick);
    }

//    }
//
   /**
     * Handle click on Join button.
     *
     * @param event the mouse click event.
     */
    private void onConnectBtnClick(Event event){
        String address = serverAddressField.getText();
        String port = serverPortField.getText();

        boolean isValidIpAddress = ClientController.isValidIpAddress(address);
        boolean isValidPort = ClientController.isValidPort(port);

        serverAddressField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidIpAddress);
        serverPortField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidPort);

        if (isValidIpAddress && isValidPort) {
            connectBtn.setDisable(true);

            Map<String, String> serverInfo = Map.of("address", address, "port", port);
            new Thread(() -> notifyObserver(obs -> obs.onAskServerInfo(serverInfo))).start();
        }
    }

//    private void onJoinBtnClick(Event event) {
//        joinBtn.setDisable(true);
//
//        String nickname = nicknameField.getText();
//
//        new Thread(() -> notifyObserver(obs -> obs.onUpdateNickname(nickname))).start();
//        }
//
//    /**
//     * Handle click on back to menu button.
//     *
//     * @param event the mouse click event.
//     */
//    private void onBackToMenuBtnClick(Event event) {
//        joinBtn.setDisable(true);
//        backToMenuBtn.setDisable(true);
//
//        new Thread(() -> notifyObserver(ViewObserver::onDisconnection)).start();
//        SceneController.changeRootPane(observers, event, "menu_scene.fxml");
//    }
}

