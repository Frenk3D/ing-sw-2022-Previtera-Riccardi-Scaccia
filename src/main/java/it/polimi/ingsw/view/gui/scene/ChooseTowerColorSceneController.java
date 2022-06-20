package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChooseTowerColorSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private ImageView whiteTowerImgView;

    @FXML
    private ImageView blackTowerImgView;

    @FXML
    private ImageView greyTowerImgView;

    public void initialize(){
        whiteTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickWhiteTowerImgView);
        blackTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickBlackTowerImgView);
        greyTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickGreyTowerImgView);
    }
    public void onClickWhiteTowerImgView(Event event){
        whiteTowerImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(TowerColor.WHITE))).start();
    }
    public void onClickBlackTowerImgView(Event event){
        blackTowerImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(TowerColor.BLACK))).start();
    }
    public void onClickGreyTowerImgView(Event event){
        greyTowerImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(TowerColor.GRAY))).start();
    }
}
