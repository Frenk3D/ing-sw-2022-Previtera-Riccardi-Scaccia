package it.polimi.ingsw.model.enumerations;

public enum Wizard {
    OLD_WIZARD(0),
    KING_WIZARD(1),
    GIRL_WIZARD(2),
    ASIATIC_WIZARD(3);

    int wizardIndex;

    Wizard(int index){
        wizardIndex = index;
    }

    public static Wizard getWizard(int index){
        for (Wizard wizard : Wizard.values()){
            if(wizard.wizardIndex==index) return wizard;
        }
        return null;
    }
}
