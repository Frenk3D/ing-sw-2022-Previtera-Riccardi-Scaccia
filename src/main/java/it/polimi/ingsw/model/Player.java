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
    public Player(String name, int id){
        numOfMoney=1;
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        team = 0;
        hasTower = true;
        this.id = id;
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
    public void setTeam(int team){
        this.team = team;
    }
    public void setHasTower(boolean tower){
        this.hasTower = tower;
    }


    public void setSelectedAssistant(int selAssistantId){
        selectedAssistant = assistantDeck.getAssistantById(selAssistantId);
        assistantDeck.getAssistantsList().remove(assistantDeck.getAssistantById(selAssistantId));
    }

    public void setTowerColor(TowerColor color){
        playerTowerColor= color;
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





}
