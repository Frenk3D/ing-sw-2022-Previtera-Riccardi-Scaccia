package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Island {
    //attributes
    private int nextIsland; //used to manage groups
    private int prevIsland;
    private boolean forbidCard;
    private List<Tower> towersList;
    private List<Student> studentsList;

    //Methods
    //constructor
    public Island(){
        nextIsland= -1;
        prevIsland= -1;
        forbidCard= false;
        towersList= new ArrayList<>();
        studentsList= new ArrayList<Student>();
    }

    //getter
    public int getNextIsland() {
        return nextIsland;
    }
    public int getPrevIsland() {
        return prevIsland;
    }



    //setter
    public void setNextIsland(int nextIsland) {
        this.nextIsland = nextIsland;
    }
    public void setPrevIsland(int prevIsland) {
        this.prevIsland = prevIsland;
    }
    public void setTower(Tower tower) {
        this.tower = tower;
    }


    public static void initStudentIsland(List<Island> islandsList, int motherNaturePos){
        Bag bag;

        int counter = 0;
        bag.initialBagFill();
        List<Student> l=bag.extractStudents(10);
        int emptyPos;
        if(motherNaturePos<6) emptyPos = motherNaturePos+6;
        else emptyPos = motherNaturePos-6;

        for (Island island: islandsList) {
            if(counter!=motherNaturePos && counter!=emptyPos) {
                island.addStudent(l.get(0));
                l.remove(0);
            }
            counter++;
        }
    }

    public static List<Island> generateIslandsList(){
        List<Island> islandsList= new ArrayList<>();
        for(int i=0; i<12; i++){
            islandsList.add(new Island());
        }
        return islandsList;
    }

    public boolean getForbidCard() {
        return forbidCard;
    }

    public void setForbidCard(boolean forbidCard) {
        this.forbidCard = forbidCard;
    }

    public List<Tower> getTowersList() {
        return towersList;
    }

    public Tower getTower() {
        return towersList.get(0);
    }

    public void addTower(Tower tower){
        towersList.add(tower);
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public void addStudent(Student student){
        studentsList.add(student);
    }


    public void updateIslandDomain(List<Player> playerList){

    }

    private void calculateInfluence(Player player){

    }

}
