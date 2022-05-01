package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class Characters3and4and5 extends Character{
    //attributes
    private int forbidCards;

    //constructor
    public Characters3and4and5(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    private void initForbidCards5(){
        forbidCards=4;
    }

    //methods
    private boolean updateIslandDomain3(Island island, List<Player> playersList, Characters3and4and5 forbidCharacter){
        try {
            island.updateIslandDomainExpert(playersList,forbidCharacter);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

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

    private boolean moveForbidCard5(Island island) {
        if(forbidCards <= 0){
            System.out.println("Forbid cards finished!");
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

    public void addForbidCard5(){
        if(forbidCards>=4){
            System.out.println("Too many forbid cards");
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


    public int getForbidCards() {
        return forbidCards;
    }

    public void setForbidCards(int forbidCards) {
        this.forbidCards = forbidCards;
    }

}
