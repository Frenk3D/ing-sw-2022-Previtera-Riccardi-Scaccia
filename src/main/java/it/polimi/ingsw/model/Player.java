package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //attributes
    private final String name;
    private int numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;
    private boolean hasTower;
    private int team;

    //constructor
    public Player(String name){
        numOfMoney=1;
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        team = 0;
        hasTower = true;
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

    //setter
    public void setTeam(int team){
        this.team = team;
    }
    public void setHasTower(boolean tower){
        this.hasTower = tower;
    }
    public void setSelectedAssistant(Assistant selAssistant){
        selectedAssistant= selAssistant;
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

    public void generateTower(GameModel game){ //number of players is needed
        int towersToGenerate;
        if(game.getNumOfPlayers()==2 || game.getNumOfPlayers()==4) {
            towersToGenerate = 8;
        }
        else {
            towersToGenerate = 6;
        }
            List<Tower> towers = new ArrayList<>();
        for(int i=0;i<towersToGenerate;i++){
                towers.add(new Tower(playerTowerColor));
        }
        dashboard.setTowersList(towers);
    }
    public void selectWizard(List<Integer> wizardsList){

    }




}
