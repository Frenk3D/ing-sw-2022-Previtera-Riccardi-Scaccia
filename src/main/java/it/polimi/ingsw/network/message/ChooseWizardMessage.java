package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.Wizard;

public class ChooseWizardMessage extends Message{
    int playerId;
    Wizard selectedWizard;

    public ChooseWizardMessage(int senderId, int playerId, Wizard selectedWizard){
        super(MessageType.CHOOSE_WIZARD,senderId);
        this.playerId = playerId;
        this.selectedWizard = selectedWizard;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Wizard getSelectedWizard() {
        return selectedWizard;
    }
}
