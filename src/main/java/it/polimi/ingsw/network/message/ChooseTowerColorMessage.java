package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.TowerColor;

/**
 * This class implements the ChooseTowerColorMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 * this message handles the selection of a tower color
 */
public class ChooseTowerColorMessage extends Message {
    private final TowerColor selectedColor;

    /**
     * Default constructor
     *
     * @param senderId      the id of the message sender
     * @param selectedColor the selecter tower color
     */
    public ChooseTowerColorMessage(int senderId, TowerColor selectedColor) {
        super(MessageType.CHOOSE_TOWER_COLOR, senderId, false);
        this.selectedColor = selectedColor;
    }

    /**
     * @return the selected tower color
     */
    public TowerColor getSelectedColor() {
        return selectedColor;
    }
}
