package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.TowerColor;

public class ChooseTowerColorMessage extends Message{
    private TowerColor selectedColor;

    public ChooseTowerColorMessage(int senderId, TowerColor selectedColor){
        super(MessageType.CHOOSE_TOWER_COLOR,senderId,false);
        this.selectedColor = selectedColor;
    }

    public TowerColor getSelectedColor() {
        return selectedColor;
    }
}
