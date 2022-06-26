package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * The manager of the scene where the player have to choose a lobby from the availables.
 */
public class JoinSceneController extends ViewObservable implements GenericSceneController{
    private static final int LIST_CELL_HEIGHT = 60;

    List<Lobby> lobbiesList;

    @FXML
    ListView<String> lobbiesListView;

    @FXML
    public void initialize() {
        lobbiesListView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickListView);
    }

    /**
     * Update the list of lobbies.
     * @param lobbyList the lobbies to show
     */
    public void updateList(List<Lobby> lobbyList){
        int counter = 1;
        lobbiesList = lobbyList;
        ObservableList<String> lobbiesNames = FXCollections.observableArrayList();
        for (Lobby lobby : lobbyList){
            lobbiesNames.add("#"+counter + " Lobby name: "+lobby.getName() + "\nNumber of players: "+lobby.getActualNumOfPlayers()+"/"+lobby.getNumOfPlayers()+"\nMode: "+( lobby.isExpertMode()? "Expert" : "Normal"));
            counter++;
        }
        lobbiesListView.setItems(lobbiesNames);
        lobbiesListView.setPrefHeight(lobbiesListView.getItems().size() * LIST_CELL_HEIGHT);
    }

    /**
     * Handles the click on the lobby chosen.
     * @param event click event.
     */
    private void onClickListView(Event event){
        int index = lobbiesListView.getSelectionModel().getSelectedIndex();
        if(lobbiesList!=null&&!lobbiesList.isEmpty()){
            String name = lobbiesList.get(index).getName();
            new Thread(() -> notifyObserver(obs -> obs.onSendChooseLobby(name))).start();
        }
    }
}
