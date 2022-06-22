package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the game object Player
 * A player is defined by name and id
 * If the game is 4 players game the player can choose a team
 * The player also chooses his tower color and his wizard
 *At the beginning of each round the player also chooses an assistant
 */
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
    private final Logger logger = Logger.getLogger(getClass().getName());

    //constructor

    /**
     * team is set to -1 by default
     * numOfMoney is set to null
     * the rest is a default constructor
     * @param name
     * @param id
     */
    public Player(String name, int id){
        numOfMoney=null; //if numOfMoney is null we are in normal mode
        this.name= name;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        hasTower = true;
        team = -1;
        this.id = id;
    }

    //getter

    /**
     *
     * @return the player's team
     */
    public int getTeam(){
        return team;
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
     * @return the player's dashboard
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     *
     * @return the player's assistant deck
     */
    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    /**
     *
     * @return the selected assistant
     */
    public Assistant getSelectedAssistant(){
        return selectedAssistant;
    }

    /**
     * Sets the selected assistant
     * @param selectedAssistant
     */
    public void setSelectedAssistant(Assistant selectedAssistant) {
        this.selectedAssistant = selectedAssistant;
    }

    /**
     *
     * @return the player's tower color
     */
    public TowerColor getTowerColor(){
        return playerTowerColor;
    }

    /**
     *
     * @return the player's money
     */
    public int getMoney() {
        if (numOfMoney==null){
            return -1;
        }
        return numOfMoney;
    }

    /**
     *
     * @return the player's id
     */
    public int getId() {
        return id;
    }

    //setter

    /**
     * Sets if the player has the tower (in a 4 players game)
     * @param tower
     */
    public void setHasTower(boolean tower){
        this.hasTower = tower;
    } //for 4 players game

    /**
     * Sets the player's team
     * @param team
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * sets the player's tower color
     * @param playerTowerColor
     */
    public void setPlayerTowerColor(TowerColor playerTowerColor) {
        this.playerTowerColor = playerTowerColor;
    }

    /**
     * Sets the selected assistant
     * @param selAssistantId
     */
    public void setSelectedAssistant(int selAssistantId){
        selectedAssistant = assistantDeck.getAssistantById(selAssistantId);
        assistantDeck.getAssistantsList().remove(assistantDeck.getAssistantById(selAssistantId));
    }

    /**
     * Modifies the player's money
     * overloaded for the use of characters
     * @param num
     * @param tableMoney
     * @param isUsed
     */
    public void modifyMoney(int num, AtomicInteger tableMoney, boolean isUsed){ //we have this overload for the use of character
        if((numOfMoney + num) < 0 || num > 0){
            logger.log(Level.SEVERE,"wrong money number");
            return;
        }

        if (num <0 && isUsed == false) { //we decrease players money
            numOfMoney = numOfMoney + num; //this is for the use of the character
            tableMoney.set(tableMoney.get() - (num + 1)); //we put one money on the character card
        }
        else if(num <0 && isUsed){
            numOfMoney = numOfMoney + num; //this is for the use of the character
            tableMoney.set(tableMoney.get() - (num));
        }
    }

    /**
     * standard modifyMoney method
     * @param num
     * @param tableMoney
     */
    public void modifyMoney(int num, AtomicInteger tableMoney){ //this function works both with positive and negative numbers, for now we call only with positive numbers
        if(numOfMoney == null){ //initialize the variable at first call
            numOfMoney=0;
        }

        if((tableMoney.get() - num) < 0 || (numOfMoney + num) < 0){
            logger.log(Level.SEVERE,"Out of money!");
            return;
        }

        if (num>0) { //we increase players money
            numOfMoney = numOfMoney + num;
            tableMoney.set(tableMoney.get() - num);
        }

        else if(num <0){
            numOfMoney = numOfMoney + num; //this is for the use of the character
            tableMoney.set(tableMoney.get() - (num));
        }
    }


    //methods

    /**
     *
     * @return if the player has the tower
     */
    public boolean hasTower(){
        return hasTower;
    } //to check which player of a given team has the towers in his dashboard

    /**
     *
     * @param playersList
     * @return the team player
     */
    public Player getTeamPlayer(List<Player> playersList){
        for(Player p : playersList){
            if(p.getTeam()==team && p != this){
                return p;
            }
        }
        return this; //player is alone
    }

    /**
     * called when the game has to be restarted (disconnection or game end)
     */
    public void reset(){
        playerTowerColor = null;
        selectedAssistant = null;
        numOfMoney=null;
        assistantDeck = new AssistantDeck();
        dashboard = new Dashboard();
        hasTower = true;
        team = -1;
    }

}
