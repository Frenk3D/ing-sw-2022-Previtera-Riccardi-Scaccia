package it.polimi.ingsw.model;

import java.util.List;

public class Characters1and7and11 extends Character {
    //attributes
    private List<Student> studentsList;

    //constructor

    public Characters1and7and11(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
        initStudents();
    }

    //methods

    private void initStudents(){
        Bag bag = Bag.getInstance();
        switch (id){
            case 1:
            case 11:
                studentsList=bag.extractStudents(4);
                break;
            case 7:
                studentsList=bag.extractStudents(6);
                break;
            default:
                break;
        }
    }

    private void moveStudent1(Island island){

    }

    private void moveStudent7(Player currPlayer, List<Integer> studentsIndexList){

    }

    private void moveStudent11(Player currPlayer, List<Integer> studentsIndexList){

    }

    @Override
    public void applyEffect(CharacterParameters params) {
        switch (id){

            case 1:
                moveStudent1(params.getIsland());
                break;
            case 7:
                moveStudent7(params.getPlayer(),params.getStudentsIndexList());
                break;
            case 11:
                moveStudent11(params.getPlayer(),params.getStudentsIndexList());
                break;
            default:
                break;
        }
    }

}
