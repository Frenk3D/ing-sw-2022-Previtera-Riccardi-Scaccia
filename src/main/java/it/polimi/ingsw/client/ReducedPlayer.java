package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;

import java.io.Serializable;

/**
 * This class implements the {@link it.polimi.ingsw.client.ClientGameModel} object Reduced Player
 */
public class ReducedPlayer implements Serializable {
    private String name;
    private int id;
    private int team;
    private ReducedAssistant selectedAssistant;
    private TowerColor playerTowerColor;
    private ReducedDashboard dashboard;
    private int numOfMoney;
    private Wizard wizard;

    /**
     * Creates a reduced player taking the info from the existing game model's player
     * @param player the game model's player
     */
    public ReducedPlayer(Player player){
        name = player.getName();
        team = player.getTeam();
        id = player.getId();
        selectedAssistant = new ReducedAssistant(player.getSelectedAssistant());
        playerTowerColor = player.getTowerColor();
        dashboard = new ReducedDashboard(player.getDashboard());
        numOfMoney = player.getMoney();
        wizard = player.getAssistantDeck().getWizard();
    }

    /**
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the player's id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the player's assistant
     */
    public ReducedAssistant getSelectedAssistant() {
        return selectedAssistant;
    }

    /**
     *
     * @return the player's tower color
     */
    public TowerColor getPlayerTowerColor() {
        return playerTowerColor;
    }

    /**
     *
     * @return the player's dashboard
     */
    public ReducedDashboard getDashboard() {
        return dashboard;
    }

    /**
     *
     * @return the player's number of money
     */
    public int getNumOfMoney() {
        return numOfMoney;
    }

    /**
     *
     * @return the player's wizard
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     *
     * @return the player's team
     */
    public int getTeam() {
        return team;
    }

    /**
     * Sets the player's number of money
     * @param numOfMoney the player's number of money
     */
    public void setNumOfMoney(int numOfMoney) {
        this.numOfMoney = numOfMoney;
    }

    /**
     * Sets the player's assistant
     * @param selectedAssistant the assistant selected by the player
     */
    public void setSelectedAssistant(ReducedAssistant selectedAssistant) {
        this.selectedAssistant = selectedAssistant;
    }

    /**
     * Sets the player's dashboard
     * @param dashboard the player's dashboard
     */
    public void setDashboard(ReducedDashboard dashboard) {
        this.dashboard = dashboard;
    }
}

