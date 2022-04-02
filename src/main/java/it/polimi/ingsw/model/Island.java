package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Island {
    //attributes
    private boolean forbidCard;
    private int weight;
    private List<Tower> towersList;
    private List<Student> studentsList;

    //Methods
    //constructor
    public Island(){
        forbidCard= false;
        towersList= new ArrayList<>();
        studentsList= new ArrayList<Student>();
    }


    public static void initStudentIsland(List<Island> islandsList, int motherNaturePos){
        Bag bag = Bag.getInstance();

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

    public static ArrayList<Island> generateIslandsList(){
        ArrayList<Island> islandsList= new ArrayList<>();
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


    public void updateIslandDomain(List<Player> playersList) {
        Player tmpPlayer = playersList.get(0);
        int counter = 0;
        for (Player p : playersList) {
            if (calculateInfluence(tmpPlayer) < calculateInfluence(p)) {
                tmpPlayer = p;
            } else if (calculateInfluence(tmpPlayer) == calculateInfluence(p)) {
                counter++;
            }
        }
        if (counter < playersList.size()) { //if counter is less than the number of players we have to update domain,or else we do nothing
            this.towersList.add(tmpPlayer.getDashboard().getTowersList().remove(0));
        }

    }
    public void updateIslandDomainExpert(List<Player> playersList, Characters3and4and5 forbidCharacter){
        if(forbidCard == true){
            forbidCard = false;
            forbidCharacter.addForbidCard5();
        }
        else {
            Player domainingPlayer = null;
            Player tmpPlayer = playersList.get(0); //tmpPlayer is the current domaining player
            boolean allParity = true;
            for (Player p : playersList) {
                if(getTower().getColor().equals(p.getTowerColor()) && p.hasTower()) //check on hasTower for 4 players
                    domainingPlayer = p;

                if (calculateInfluence(p) > calculateInfluence(tmpPlayer)) {
                    tmpPlayer = p;
                    allParity=false;
                }
            }
            if (!allParity) { //if the var is false there is a change of domain, or else we do nothing
                if (!getTower().getColor().equals(tmpPlayer.getTowerColor())){

                    for (Tower t: towersList) {

                    }
                }
                towersList.add(tmpPlayer.getDashboard().getTowersList().remove(0));
                if(tmpPlayer.getDashboard().getTowersList().size()==0) ; //win
            }
           /* Island prevIsland = islandsList.get(0);
            for (Island i : islandsList) {
                if ()
            } */
        }
    }
    private int calculateInfluence(Player player){
        int influence = 0;
        PawnColor[] colors = {PawnColor.RED,PawnColor.GREEN,PawnColor.BLUE,PawnColor.YELLOW, PawnColor.PINK};
        for(PawnColor c : colors){
            int numOfThatColor = 0;
            for(Student s : studentsList){
                if(s.getColor() == c){
                    numOfThatColor++;
                }
            }
            if(player.getDashboard().getProfessorByColor(c) != null){
                influence = influence + numOfThatColor;
            }
        }
        if (this.getTower().getColor().equals(player.getTowerColor())){
            influence = influence + this.weight;
        }
        return influence;
    }

}
