package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //attributes
    private final String name;
    private final int id;
    private int numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;
    private boolean hasTower;
    private int team;

    //constructor
    public Player(String name, int id, int team, TowerColor playerTowercolor){
        numOfMoney=-1; //if numOfMoney is -1 we are in normal mode
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        this.team = team;
        hasTower = true;
        this.id = id;
        this.playerTowerColor = playerTowercolor;
    }

    //getter
    public int getTeam(){
        return team;
    }
    public String getName() {
        return name;
    }
    public Dashboard getDashboard() {
        return dashboard;
    }
    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }
    public Assistant getSelectedAssistant(){
        return selectedAssistant;
    }
    public TowerColor getTowerColor(){
        return playerTowerColor;
    }
    public int getMoney() {
        return numOfMoney;
    }

    public int getId() {
        return id;
    }

    //setter
    public void setHasTower(boolean tower){
        this.hasTower = tower;
    }


    public void setSelectedAssistant(int selAssistantId){
        selectedAssistant = assistantDeck.getAssistantById(selAssistantId);
        assistantDeck.getAssistantsList().remove(assistantDeck.getAssistantById(selAssistantId));
    }

    public void setMoney(int num){
        numOfMoney= num;
    }

    //methods
    public boolean hasTower(){
        return hasTower;
    }


    public void selectWizard(List<Integer> wizardsList, int type){
        getAssistantDeck().setWizard(type);
        wizardsList.remove((Integer) type);
    }


    public Player getTeamPlayer(List<Player> playersList){
        for(Player p : playersList){
            if(p.getTeam()==team && p != this){
                return p;
            }
        }
        return this; //player is alone
    }



}
