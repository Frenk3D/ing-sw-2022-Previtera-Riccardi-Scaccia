package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.TowerColor;

public class ChooseTowerColorMessage extends Message{
    int playerId;
    TowerColor selectedColor;

    public ChooseTowerColorMessage(int senderId, int playerId, TowerColor selectedColor){
        super(MessageType.CHOOSE_TOWER_COLOR,senderId);
        this.playerId = playerId;
        this.selectedColor = selectedColor;
    }

    public int getPlayerId() {
        return playerId;
    }

    public TowerColor getSelectedColor() {
        return selectedColor;
    }
}
