package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.Wizard;

public class ChooseWizardMessage extends Message{
    Wizard selectedWizard;

    public ChooseWizardMessage(int senderId, Wizard selectedWizard){
        super(MessageType.CHOOSE_WIZARD,senderId,false);
        this.selectedWizard = selectedWizard;
    }

    public Wizard getSelectedWizard() {
        return selectedWizard;
    }
}
