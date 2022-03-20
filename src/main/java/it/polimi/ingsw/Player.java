package it.polimi.ingsw;

public class Player {
    private final String name;
    private int numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;

    public Player(String name){
        numOfMoney=1;
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
    }

    public String getName() {
        return name;
    } //serve veramente name?

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

    public void generateTower(){

    }

    public void generateDeck(){

    }

    public void setMoney(int num){
        numOfMoney= num;
    }

    public int getMoney() {
        return numOfMoney;
    }

}
