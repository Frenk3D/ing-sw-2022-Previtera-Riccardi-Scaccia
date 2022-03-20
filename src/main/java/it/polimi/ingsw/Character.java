package it.polimi.ingsw;

public class Character {
    private int numOfUse;
    private final int id;
    private final int initialCost;

    public Character(int id,int initialCost){
        this.id = id;
        this.initialCost = initialCost;
    }
    public int getNumOfUse() {
        return numOfUse;
    }

    public int getId() {
        return id;
    }
    public int getInitialCost(){
        return initialCost;
    }

    public void setNumOfUse(int numOfUse) {
        this.numOfUse = numOfUse;
    }

}
