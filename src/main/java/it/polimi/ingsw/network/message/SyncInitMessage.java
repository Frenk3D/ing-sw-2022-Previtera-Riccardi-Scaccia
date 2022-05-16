package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;

import java.util.List;
import java.util.Map;

public class SyncInitMessage extends Message {

    Map<String, Integer> availableTeamPlayers;
    List<TowerColor> availableTowerColors;
    List<Wizard> availableWizards;

    public SyncInitMessage(MessageType type, int senderId, Map<String, Integer> availableTeamPlayers, List<TowerColor> availableTowerColors, List<Wizard> availableWizards){
        super(type,senderId,false);
        this.availableTeamPlayers = availableTeamPlayers;
        this.availableTowerColors = availableTowerColors;
        this.availableWizards = availableWizards;
    }

    public Map<String, Integer> getAvailableTeamPlayers() {
        return availableTeamPlayers;
    }

    public List<TowerColor> getAvailableTowerColors() {
        return availableTowerColors;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
