package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class implements the Characters 3,4 and 5
 */

public class Characters3and4and5 extends Character{
    private final Logger logger = Logger.getLogger(getClass().getName());
    //attributes
    private int forbidCards;

    //constructor

    /**
     * default constructor
     * @param id
     * @param initialCost
     */
    public Characters3and4and5(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    /**
     * initializes the forbid cards for Character 5
     */
    private void initForbidCards5(){
        forbidCards=4;
    }

    //methods

    /**
     * modified version of classic updateIslandDomain method for character 3
     * @param island
     * @param playersList
     * @param forbidCharacter
     * @return false if an exception is detected
     * @return true otherwise
     */
    private boolean updateIslandDomain3(Island island, List<Player> playersList, Characters3and4and5 forbidCharacter){
        try {
            island.updateIslandDomainExpert(playersList,forbidCharacter);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Modifies the number of positions mother nature can move,using the 4th character effect
     * @param cardPlayer
     * @return false if an exception is detected
     * @return true otherwise
     */
    private boolean modifyMotherNaturePosShift4(Player cardPlayer){
        try {
            int prevPosShift=cardPlayer.getSelectedAssistant().getMotherNaturePosShift();
            cardPlayer.getSelectedAssistant().setMotherNaturePosShift(prevPosShift+2);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Moves a forbid card from character 5 to an island
     * @param island
     * @return false if an exception is detected or if there aren't available forbid cards
     * @return true otherwise
     */
    private boolean moveForbidCard5(Island island) {
        if(forbidCards <= 0){
            logger.log(Level.SEVERE,"Forbid cards finished!");
            return false;
        }

        try {
            forbidCards--;
            island.setForbidCards(island.getForbidCards()+1);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    /**
     * Adds a forbid card to character 5
     */
    public void addForbidCard5(){
        if(forbidCards>=4){
            logger.log(Level.SEVERE,"Too many forbid cards on the card");
            return;
        }
        forbidCards++;
    }

    @Override
    public boolean applyEffect(CharacterParameters params) {

        switch (id){
            case 3:
                return updateIslandDomain3(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
            case 4:
                return modifyMotherNaturePosShift4(params.getPlayer());
            case 5:
                return moveForbidCard5(params.getIsland());
            default:
                return false; //impossible because id is "final"
        }
    }

    @Override
    public void initCharacter(CharacterParameters params) {
        if(id==5){
            initForbidCards5();
        }
    }

    //for test purposes

    /**
     *
     * @return the character's forbid cards
     */
    public int getForbidCards() {
        return forbidCards;
    }

    /**
     * sets the character's forbid cards
     * @param forbidCards
     */
    public void setForbidCards(int forbidCards) {
        this.forbidCards = forbidCards;
    }

}
