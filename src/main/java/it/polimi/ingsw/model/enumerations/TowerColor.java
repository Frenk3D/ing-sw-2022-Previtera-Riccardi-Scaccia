package it.polimi.ingsw.model.enumerations;

/**
 * This enum contains the colors of the Towers.
 */
public enum TowerColor {
    BLACK,
    WHITE,
    GRAY;





   /*
    ------------------------------------------------------------------------------------------------
    For some tests

    private int colorIndex;

    TowerColor(int index){
        colorIndex = index;
    }

    //a contract...
    public static TowerColor getTowerColor(int index){
        for (TowerColor towerColor : TowerColor.values()){
            if(towerColor.colorIndex==index) return towerColor;
        }
        return null;
    } */
}
