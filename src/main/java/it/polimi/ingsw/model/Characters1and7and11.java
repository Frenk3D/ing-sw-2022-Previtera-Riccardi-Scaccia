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

    private void moveStudent(Island island){

    }

    private void moveStudent(Player currPlayer){

    }

    @Override
    public void applyEffect(Object object) {
        if(object instanceof Island){
            moveStudent((Island) object);
        }

        else if(object instanceof Player){
            moveStudent((Player) object);
        }
    }


    @Override
    public void applyEffect(Object object1, Object object2) {
    //errore passaggio parametri
    }

    @Override
    public void applyEffect(Object object1, Object object2, Object object3) {

    }

}
