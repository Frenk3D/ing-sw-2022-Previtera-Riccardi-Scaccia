package it.polimi.ingsw.model;

public class Factory {

    //methods
    public static Character newCharacter(int id,int initialCost){
        switch (id){
            case 1:
                return new Characters1and7and11(1,1);
        }
        return null;
    }

}
