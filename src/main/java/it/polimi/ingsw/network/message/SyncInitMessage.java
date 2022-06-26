package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;

import java.util.List;
import java.util.Map;

/**
 *  This class implements the SyncInitMessage,it extends {@link it.polimi.ingsw.network.message.Message}
 *  this message contains the available team players,the available tower colors and the available wizards
 */
public class SyncInitMessage extends Message {

    Map<String, Integer> availableTeamPlayers;
    List<TowerColor> availableTowerColors;
    List<Wizard> availableWizards;

    /**
     * Default constructor
     * @param type the message type
     * @param senderId the id of the message sender
     * @param availableTeamPlayers the available team players
     * @param availableTowerColors the available tower colors
     * @param availableWizards the available wizards
     */
    public SyncInitMessage(MessageType type, int senderId, Map<String, Integer> availableTeamPlayers, List<TowerColor> availableTowerColors, List<Wizard> availableWizards){
        super(type,senderId,false);
        this.availableTeamPlayers = availableTeamPlayers;
        this.availableTowerColors = availableTowerColors;
        this.availableWizards = availableWizards;
    }

    /**
     *
     * @return the map of available team players' names and ids
     */
    public Map<String, Integer> getAvailableTeamPlayers() {
        return availableTeamPlayers;
    }

    /**
     *
     * @return the available tower colors
     */
    public List<TowerColor> getAvailableTowerColors() {
        return availableTowerColors;
    }

    /**
     *
     * @return the list of wizards
     */
    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}
