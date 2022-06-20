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
        oldWizardImgView.setDisable(true);
        kingWizardImgView.setDisable(true);
        girlWizardImgView.setDisable(true);
        asiaticWizardImgView.setDisable(true);

    }
    public void onClickOldWizardImgView(Event event){
        oldWizardImgView.setDisable(true);
        kingWizardImgView.setDisable(true);
        girlWizardImgView.setDisable(true);
        asiaticWizardImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.SAGE))).start();
    }
    public void onClickKingWizardImgView(Event event){
        oldWizardImgView.setDisable(true);
        kingWizardImgView.setDisable(true);
        girlWizardImgView.setDisable(true);
        asiaticWizardImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.KING))).start();
    }
    public void onClickGirlWizardImgView(Event event){
        oldWizardImgView.setDisable(true);
        kingWizardImgView.setDisable(true);
        girlWizardImgView.setDisable(true);
        asiaticWizardImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.WITCH))).start();
    }
    public void onClickAsiaticWizardImgView(Event event){
        oldWizardImgView.setDisable(true);
        kingWizardImgView.setDisable(true);
        girlWizardImgView.setDisable(true);
        asiaticWizardImgView.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.onSendChooseWizard(Wizard.ASIATIC))).start();
    }

    public void updateList(List<Wizard> availableWizards){
        if (availableWizards.contains(Wizard.SAGE)) {
            oldWizardImgView.setDisable(false);
        } else{oldWizardImgView.setDisable(false);}

        if (availableWizards.contains(Wizard.KING)) {
            kingWizardImgView.setDisable(false);
        } else{kingWizardImgView.setDisable(false);}

        if (availableWizards.contains(Wizard.WITCH)) {
            girlWizardImgView.setDisable(false);
        } else{girlWizardImgView.setDisable(false);}

        if (availableWizards.contains(Wizard.ASIATIC)) {
            asiaticWizardImgView.setDisable(false);
        } else{asiaticWizardImgView.setDisable(false);}

    }
}
