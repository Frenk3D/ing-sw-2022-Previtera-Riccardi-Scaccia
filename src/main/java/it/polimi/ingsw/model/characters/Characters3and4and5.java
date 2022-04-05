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

        if(id==5){
            initForbidCards5();
        }
    }


    //methods
    private void updateIslandDomain3(Island island, List<Player> playersList, Characters3and4and5 forbidCharacter){
        island.updateIslandDomainExpert(playersList,forbidCharacter);
    }

    private void modifyMotherNaturePosShift4(Player cardPlayer){
        int prevPosShift=cardPlayer.getSelectedAssistant().getMotherNaturePosShift();
        cardPlayer.getSelectedAssistant().setMotherNaturePosShift(prevPosShift+2);
    }

    private void initForbidCards5(){
        forbidCards=4;
    }

    private void moveForbidCard5(Island island){
        forbidCards--;
        island.setForbidCard(island.getForbidCard()+1);
    }

    public void addForbidCard5(){
        forbidCards++;
    }

    @Override
    public void applyEffect(CharacterParameters params) {

        switch (id){
            case 3:
                updateIslandDomain3(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;
            case 4:
                modifyMotherNaturePosShift4(params.getPlayer());
                break;
            case 5:
                moveForbidCard5(params.getIsland());
                break;
            default:
                break;
        }
    }
}
