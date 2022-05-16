package it.polimi.ingsw.model.client;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;

public class ReducedPlayer {
    private String name;
    private int id;
    private ReducedAssistant selectedAssistant;
    private TowerColor playerTowerColor;
    private ReducedDashboard dashboard;
    private int numOfMoney;
    private Wizard wizard;

    public ReducedPlayer(Player player){
        name = player.getName();
        id = player.getId();
        selectedAssistant = new ReducedAssistant(player.getSelectedAssistant());
        playerTowerColor = player.getTowerColor();
        dashboard = new ReducedDashboard(player.getDashboard());
        numOfMoney = player.getMoney();
        wizard = player.getAssistantDeck().getWizard();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ReducedAssistant getSelectedAssistant() {
        return selectedAssistant;
    }

    public TowerColor getPlayerTowerColor() {
        return playerTowerColor;
    }

    public ReducedDashboard getDashboard() {
        return dashboard;
    }

    public int getNumOfMoney() {
        return numOfMoney;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
