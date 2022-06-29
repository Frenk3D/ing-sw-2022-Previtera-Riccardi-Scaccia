package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;


/**
 * This class implements the scene where players wait for other players to join the game.
 */
public class LobbySceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private ListView<String> listViewJoin;


    /**
     * This method will add in the list view the players inside the lobby
     *
     * @param playersList the players that are in the lobby
     */
    public void setPlayersLabel(List<String> playersList) {
        ObservableList<String> names = FXCollections.observableArrayList();
        names.addAll(playersList);
        listViewJoin.setItems(names);

    }
}
