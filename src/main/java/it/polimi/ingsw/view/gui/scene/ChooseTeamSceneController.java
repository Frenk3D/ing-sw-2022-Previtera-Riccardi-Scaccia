package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.server.Lobby;
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

public class ChooseTeamSceneController extends ViewObservable implements GenericSceneController {
    private static final int LIST_CELL_HEIGHT = 60;

    List<Integer> availablePlayersId;

    @FXML
    ListView<String> playersListView;

    @FXML
    Label chosenMessageLabel;

    @FXML
    public void initialize() {
        playersListView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickListView);
    }

    public void updateList(Map<String,Integer> availablePlayers){
            availablePlayersId = new ArrayList<>();
            ObservableList<String> playersNames = FXCollections.observableArrayList();
            for (Map.Entry<String,Integer> entry : availablePlayers.entrySet()){
                playersNames.add("Name: " + entry.getKey() + ", Id: " + entry.getValue());
                availablePlayersId.add(entry.getValue());
            }
            playersListView.setItems(playersNames);
            playersListView.setPrefHeight(playersListView.getItems().size() * LIST_CELL_HEIGHT);
    }

    public void onClickListView(Event event){
        int index = playersListView.getSelectionModel().getSelectedIndex();
        if(availablePlayersId!=null&&!availablePlayersId.isEmpty()){
            int id = availablePlayersId.get(index);
            new Thread(() -> notifyObserver(obs -> obs.onSendChooseTeam(id))).start();
        }

    }

    public void disableChoice(String toShow){
        playersListView.setVisible(false);
        chosenMessageLabel.setText(toShow);

    }

}
