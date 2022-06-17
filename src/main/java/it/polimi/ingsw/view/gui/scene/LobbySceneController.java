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

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Label player3Label;

    @FXML
    private Label player4Label;

    public void setPlayersLabel(List<Player> playersList){
        if(playersList.size() == 2){
            player1Label.setText(playersList.get(0).getName());
            player2Label.setText(playersList.get(1).getName());
            player3Label.setVisible(false);
            player4Label.setVisible(false);
        }
        if(playersList.size() == 3){
            player1Label.setText(playersList.get(0).getName());
            player2Label.setText(playersList.get(1).getName());
            player3Label.setText(playersList.get(2).getName());
            player4Label.setVisible(false);
        }
        if(playersList.size() == 4){
            player1Label.setText(playersList.get(0).getName());
            player2Label.setText(playersList.get(1).getName());
            player3Label.setText(playersList.get(2).getName());
            player4Label.setText(playersList.get(3).getName());
        }

    }
}
