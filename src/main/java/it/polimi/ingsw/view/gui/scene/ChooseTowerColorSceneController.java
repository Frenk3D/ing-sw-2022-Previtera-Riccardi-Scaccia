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

        whiteTowerImgView.setOnMouseEntered((e)->{
            whiteTowerImgView.setScaleX(1.04);
            whiteTowerImgView.setScaleY(1.04);
        });
        whiteTowerImgView.setOnMouseExited((e)->{
            whiteTowerImgView.setScaleX(1);
            whiteTowerImgView.setScaleY(1);
        });
        blackTowerImgView.setOnMouseEntered((e)->{
            blackTowerImgView.setScaleX(1.04);
            blackTowerImgView.setScaleY(1.04);
        });
        blackTowerImgView.setOnMouseExited((e)->{
            blackTowerImgView.setScaleX(1);
            blackTowerImgView.setScaleY(1);
        });
        greyTowerImgView.setOnMouseEntered((e)->{
            greyTowerImgView.setScaleX(1.04);
            greyTowerImgView.setScaleY(1.04);
        });
        greyTowerImgView.setOnMouseExited((e)->{
            greyTowerImgView.setScaleX(1);
            greyTowerImgView.setScaleY(1);
        });
        
    }
    public void onClickWhiteTowerImgView(Event event){
        setAllDisabled(true);
        setAllFaded(true);
        TowerColor tc = TowerColor.WHITE;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }
    public void onClickBlackTowerImgView(Event event){
        setAllDisabled(true);
        setAllFaded(true);
        TowerColor tc = TowerColor.BLACK;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }
    public void onClickGreyTowerImgView(Event event){
        setAllDisabled(true);
        setAllFaded(true);
        TowerColor tc = TowerColor.GRAY;
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseTowerColor(tc))).start();
    }

    public void updateList(List<TowerColor> availableTowerColors){
        setAllDisabled(false);
        setAllFaded(false);
        if (!availableTowerColors.contains(TowerColor.BLACK)) {
            blackTowerImgView.setDisable(true);
            blackTowerImgView.setOpacity(0.2);
        }
        if (!availableTowerColors.contains(TowerColor.WHITE)) {
            whiteTowerImgView.setDisable(true);
            whiteTowerImgView.setOpacity(0.2);
        }
        if (!availableTowerColors.contains(TowerColor.GRAY)) {
            greyTowerImgView.setDisable(true);
            greyTowerImgView.setOpacity(0.2);
        }
    }


    private void setAllDisabled(boolean state){
        greyTowerImgView.setDisable(state);
        blackTowerImgView.setDisable(state);
        whiteTowerImgView.setDisable(state);
    }

    private void setAllFaded(boolean state){
        if(state) {
            greyTowerImgView.setOpacity(0.2);
            blackTowerImgView.setOpacity(0.2);
            whiteTowerImgView.setOpacity(0.2);
        }
        else {
            greyTowerImgView.setOpacity(1);
            blackTowerImgView.setOpacity(1);
            whiteTowerImgView.setOpacity(1);
        }
    }
}
