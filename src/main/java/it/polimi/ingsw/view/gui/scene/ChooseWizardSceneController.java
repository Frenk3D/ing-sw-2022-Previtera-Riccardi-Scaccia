package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ChooseWizardSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private ImageView oldWizardImgView;

    @FXML
    private ImageView kingWizardImgView;

    @FXML
    private ImageView girlWizardImgView;

    @FXML
    private ImageView asiaticWizardImgView;

    public void initialize(){
        oldWizardImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickOldWizardImgView);
        kingWizardImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickKingWizardImgView);
        girlWizardImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickGirlWizardImgView);
        asiaticWizardImgView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onClickAsiaticWizardImgView);

        oldWizardImgView.setOnMouseEntered((e)->{
            oldWizardImgView.setScaleX(1.04);
            oldWizardImgView.setScaleY(1.04);
        });
        oldWizardImgView.setOnMouseExited((e)->{
            oldWizardImgView.setScaleX(1);
            oldWizardImgView.setScaleY(1);
        });
        kingWizardImgView.setOnMouseEntered((e)->{
            kingWizardImgView.setScaleX(1.04);
            kingWizardImgView.setScaleY(1.04);
        });
        kingWizardImgView.setOnMouseExited((e)->{
            kingWizardImgView.setScaleX(1);
            kingWizardImgView.setScaleY(1);
        });
        girlWizardImgView.setOnMouseEntered((e)->{
            girlWizardImgView.setScaleX(1.04);
            girlWizardImgView.setScaleY(1.04);
        });
        girlWizardImgView.setOnMouseExited((e)->{
            girlWizardImgView.setScaleX(1);
            girlWizardImgView.setScaleY(1);
        });

        asiaticWizardImgView.setOnMouseEntered((e)->{
            asiaticWizardImgView.setScaleX(1.04);
            asiaticWizardImgView.setScaleY(1.04);
        });

        asiaticWizardImgView.setOnMouseExited((e)->{
            asiaticWizardImgView.setScaleX(1);
            asiaticWizardImgView.setScaleY(1);
        });

    }
    public void onClickOldWizardImgView(Event event){
        setAllDisable(true);
        setAllFaded(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.SAGE))).start();
    }
    public void onClickKingWizardImgView(Event event){
        setAllDisable(true);
        setAllFaded(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.KING))).start();
    }
    public void onClickGirlWizardImgView(Event event){
        setAllDisable(true);
        setAllFaded(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.WITCH))).start();
    }
    public void onClickAsiaticWizardImgView(Event event){
        setAllDisable(true);
        setAllFaded(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.ASIATIC))).start();
    }

    public void updateList(List<Wizard> availableWizards){
        setAllDisable(false);
        setAllFaded(false);
        if (!availableWizards.contains(Wizard.SAGE)) {
            oldWizardImgView.setDisable(true);
            oldWizardImgView.setOpacity(0.2);
        }
        if (!availableWizards.contains(Wizard.KING)) {
            kingWizardImgView.setDisable(true);
            kingWizardImgView.setOpacity(0.2);
        }
        if (!availableWizards.contains(Wizard.WITCH)) {
            girlWizardImgView.setDisable(true);
            girlWizardImgView.setOpacity(0.2);
        }
        if (!availableWizards.contains(Wizard.ASIATIC)) {
            asiaticWizardImgView.setDisable(true);
            asiaticWizardImgView.setOpacity(0.2);
        }
    }
    
    private void setAllDisable(boolean state){
        oldWizardImgView.setDisable(state);
        kingWizardImgView.setDisable(state);
        girlWizardImgView.setDisable(state);
        asiaticWizardImgView.setDisable(state);
    }
    
    private void setAllFaded(boolean state){
        if(state) {
            oldWizardImgView.setOpacity(0.2);
            kingWizardImgView.setOpacity(0.2);
            girlWizardImgView.setOpacity(0.2);
            asiaticWizardImgView.setOpacity(0.2);
        }
        else {
            oldWizardImgView.setOpacity(1);
            kingWizardImgView.setOpacity(1);
            girlWizardImgView.setOpacity(1);
            asiaticWizardImgView.setOpacity(1);
        }
    }
}
