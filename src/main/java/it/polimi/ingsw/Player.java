package it.polimi.ingsw;

public class Player {
    private String name;
    private int numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;


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

    }

    public void setTowerColor(TowerColor color){

    }

    public void generateTower(){

    }

    public void generateDeck(){

    }

    public void setMoney(int num){

    }

    public int getMoney() {
        return numOfMoney;
    }

}
