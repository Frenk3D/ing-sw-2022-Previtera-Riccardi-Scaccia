package it.polimi.ingsw.model.enumerations;

public enum Wizard {
    SAGE(0),
    KING(1),
    WITCH(2),
    ASIATIC(3);

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
