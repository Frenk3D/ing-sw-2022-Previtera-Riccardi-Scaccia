package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.Wizard;
/**
 * This class implements the ChooseWizardMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message handles the selection of a wizard
 */
public class ChooseWizardMessage extends Message{
    Wizard selectedWizard;

    /**
     * Default constructor
     * @param senderId the id of the message sender
     * @param selectedWizard the selected wizard
     */
    public ChooseWizardMessage(int senderId, Wizard selectedWizard){
        super(MessageType.CHOOSE_WIZARD,senderId,false);
        this.selectedWizard = selectedWizard;
    }

    /**
     *
     * @return the selected wizard
     */
    public Wizard getSelectedWizard() {
        return selectedWizard;
    }
}
