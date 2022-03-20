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

    public void generateTower(Game game){ //serve sapere quanti giocatori stanno giocando
        if(game.numOfPlayers==2 && game.numOfPlayers==4){
            dashboard.setTowersList(8); //parametro lista di torri o int?
        }
        if(game.numOfPlayers==3){
            dashboard.setTowersList(6);
        }

    }

    public void generateDeck(){ //da attrib wizard di assistantdeck

    }

    public void setMoney(int num){
        numOfMoney= num;
    }

    public int getMoney() {
        return numOfMoney;
    }

}
