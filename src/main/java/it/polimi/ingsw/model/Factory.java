package it.polimi.ingsw.model;

public class Factory {

    //methods
    public static Character newCharacter(int id){
        switch (id){
            case 1:
                return new Characters1and7and11(id,1);
            case 2:
                return new Characters2and6and8and9(id,2);
            case 3:
                return new Characters3and4and5(id,3);
            case 4:
                return new Characters3and4and5(id,1);
            case 5:
                return new Characters3and4and5(id,2);
            case 6:
                return new Characters2and6and8and9(id,3);
            case 7:
                return new Characters1and7and11(id,1);
            case 8:
                return new Characters2and6and8and9(id,2);
            case 9:
                return new Characters2and6and8and9(id,3);
            case 10:
                return new Characters10and12(id,1);
            case 11:
                return new Characters1and7and11(id,2);
            case 12:
                return new Characters10and12(id,3);
        }
        return null;
    }

}
