package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class implements the controller of the scene where users choose a team player (in 4 players games)
 */
public class ChooseTeamSceneController extends ViewObservable implements GenericSceneController {
    private static final int LIST_CELL_HEIGHT = 60;

    List<Integer> availablePlayersId;

    @FXML
    ListView<String> playersListView;

    @FXML
    Label chosenMessageLabel;

    @FXML
    public void initialize() {
        playersListView.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onClickListView);
    }

    /**
     * Update the list of players without a team.
     *
     * @param availablePlayers the available players
     */
    public void updateList(Map<String, Integer> availablePlayers) {
        availablePlayersId = new ArrayList<>();
        ObservableList<String> playersNames = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : availablePlayers.entrySet()) {
            playersNames.add(entry.getKey());
            availablePlayersId.add(entry.getValue());
        }
        playersListView.setItems(playersNames);
        playersListView.setPrefHeight(playersListView.getItems().size() * LIST_CELL_HEIGHT);
    }

    /**
     * Handle the choose event
     *
     * @param event click.
     */
    public void onClickListView(Event event) {
        int index = playersListView.getSelectionModel().getSelectedIndex();
        if (availablePlayersId != null && !availablePlayersId.isEmpty()) {
            int id = availablePlayersId.get(index);
            new Thread(() -> notifyObserver(obs -> obs.onSendChooseTeam(id))).start();
        }

    }

    /**
     * After chosen or being chosen.
     *
     * @param toShow the message to show
     */
    public void disableChoice(String toShow) {
        playersListView.setVisible(false);
        chosenMessageLabel.setText(toShow);

    }

}
