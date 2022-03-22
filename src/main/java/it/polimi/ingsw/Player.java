package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;
    private boolean hasTower;
    private int team;

    public Player(String name){
        numOfMoney=1;
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        team = 0;
        hasTower = true;
    }
    public void setTeam(int team){
        this.team = team;

    }
    public int getTeam(){
        return team;
    }
    public void setHasTower(boolean tower){
        this.hasTower = tower;
    }
    public boolean hasTower(){
        return hasTower;
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

    public void setSelectedAssistant(Assistant selAssistant){
        selectedAssistant= selAssistant;
    }

    public Assistant getSelectedAssistant(){
        return selectedAssistant;
    }

    public void setTowerColor(TowerColor color){
        playerTowerColor= color;
    }

    public TowerColor getTowerColor(){
        return playerTowerColor;
    }

    public void generateTower(Game game){ //number of players is needed
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


    public void setMoney(int num){
        numOfMoney= num;
    }

    public int getMoney() {
        return numOfMoney;
    }

}
