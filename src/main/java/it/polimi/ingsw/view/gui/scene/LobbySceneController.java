package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.PlayerJoinMessage;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.List;
//
///**
// * This class implements the scene where players wait for other players to join the game.
// */
public class LobbySceneController extends ViewObservable implements GenericSceneController {

    @FXML private Label playersLobbyLabel;


    public void setPlayersLabel(List<String> playersList){
        playersLobbyLabel.setText("");
        for (String name : playersList){
            playersLobbyLabel.setText(playersLobbyLabel.getText()+"\n"+name);
        }

    }
}
