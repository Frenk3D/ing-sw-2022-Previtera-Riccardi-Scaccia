package it.polimi.ingsw.model.enumerations;

public enum TowerColor {
    BLACK(0),
    WHITE(1),
    GRAY(2);

    private int colorIndex;

    TowerColor(int index){
        colorIndex = index;
    }

    public static TowerColor getTowerColor(int index){
        for (TowerColor towerColor : TowerColor.values()){
            if(towerColor.colorIndex==index) return towerColor;
        }
        return null;
    }
}
