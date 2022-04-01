package it.polimi.ingsw.model;

import java.util.List;

public class Characters2and6and8and9 extends Character {

    //constructor

    public Characters2and6and8and9(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    //methods
    private void updateIslandDomain2(Island island,List<Player> playersList, Characters3and4and5 forbidCharater){

    }

    private int modifiedCalculateInfluence2(Island island, Player player){
        return 0;
    }

    private void updateIslandDomain6(Island island,List<Player> playersList, Characters3and4and5 forbidCharater){

    }

    private int modifiedCalculateInfluence6(Island island, Player player){
        return 0;
    }

    private void updateIslandDomain8(Island island,List<Player> playersList, Characters3and4and5 forbidCharater){

    }

    private int modifiedCalculateInfluence8(Island island, Player player){
        return 0;
    }

    private void updateIslandDomain9(Island island,List<Player> playersList,PawnColor selectedColor, Characters3and4and5 forbidCharater){

    }


    private int modifiedCalculateInfluence9(Island island, Player player, PawnColor selectedColor){
        return 0;
    }

    @Override
    public void applyEffect(CharacterParameters params) {
        switch (id){
            case 2:
                updateIslandDomain2(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 6:
                updateIslandDomain6(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 8:
                updateIslandDomain8(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 9:
                updateIslandDomain9(params.getIsland(),params.getPlayersList(),params.getSelectedColor(), params.getForbidCharacter());
                break;

            default:
                break;
        }


    }
}
