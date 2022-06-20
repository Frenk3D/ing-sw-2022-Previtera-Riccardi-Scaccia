package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ChooseTowerColorSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private ImageView whiteTowerImgView;

    @FXML
    private ImageView blackTowerImgView;

    @FXML
    private ImageView greyTowerImgView; //gray

    public void initialize(){
        whiteTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickWhiteTowerImgView);
        blackTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickBlackTowerImgView);
        greyTowerImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickGreyTowerImgView);
        greyTowerImgView.setDisable(true);
        blackTowerImgView.setDisable(true);
        whiteTowerImgView.setDisable(true);

    }
    public void onClickWhiteTowerImgView(Event event){
        greyTowerImgView.setDisable(true);
        blackTowerImgView.setDisable(true);
        whiteTowerImgView.setDisable(true);
        TowerColor tc = TowerColor.WHITE;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }
    public void onClickBlackTowerImgView(Event event){
        greyTowerImgView.setDisable(true);
        blackTowerImgView.setDisable(true);
        whiteTowerImgView.setDisable(true);
        TowerColor tc = TowerColor.BLACK;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }
    public void onClickGreyTowerImgView(Event event){
        greyTowerImgView.setDisable(true);
        blackTowerImgView.setDisable(true);
        whiteTowerImgView.setDisable(true);
        TowerColor tc = TowerColor.GRAY;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }

    public void updateList(List<TowerColor> availableTowerColors){
        if (availableTowerColors.contains(TowerColor.BLACK)) {
            blackTowerImgView.setDisable(false);
        } else{blackTowerImgView.setDisable(false);}

        if (availableTowerColors.contains(TowerColor.WHITE)) {
            whiteTowerImgView.setDisable(false);
        }else{whiteTowerImgView.setDisable(false);}

        if (availableTowerColors.contains(TowerColor.GRAY)) {
            greyTowerImgView.setDisable(false);
        }
        else{greyTowerImgView.setDisable(false);}


    }
}
