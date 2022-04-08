package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    //attributes
    private final String name;
    private final int id;
    private int team;
    private Integer numOfMoney;
    private AssistantDeck assistantDeck;
    private Assistant selectedAssistant;
    private TowerColor playerTowerColor;
    private Dashboard dashboard;
    private boolean hasTower;

    //constructor
    public Player(String name, int id, int team, TowerColor playerTowercolor){
        numOfMoney=null; //if numOfMoney is null we are in normal mode
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

    public void modifyMoney(int num, AtomicInteger tableMoney){
        if(numOfMoney == null){ //initialize the variable at first call
            numOfMoney=0;
        }

        if((tableMoney.get() - num) < 0 || (numOfMoney + num) < 0){
            System.out.println("");
            return;
        }

        if (num>0) {
            numOfMoney = numOfMoney + num;
            tableMoney.set(tableMoney.get() - num);
        }
        else if (num <0) {
            numOfMoney = numOfMoney + num; //this is for the use of the character
            tableMoney.set(tableMoney.get() - (num -1));
        }
    }

    //methods
    public boolean hasTower(){
        return hasTower;
    } //to check which player of a givn team has the towers in his dashboard


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
