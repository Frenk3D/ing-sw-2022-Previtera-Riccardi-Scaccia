package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Island {
    //attributes
    private int forbidCards;
    private int weight;
    private List<Tower> towersList;
    private List<Student> studentsList;

    //Methods
    //constructor
    public Island(){
        weight = 1;
        forbidCards = 0;
        towersList= new ArrayList<>();
        studentsList= new ArrayList<Student>();
    }


    public static void initStudentIsland(List<Island> islandsList, int motherNaturePos, Bag bag){
        int counter = 0;
        List<Student> l=bag.extractStudents(10);

        int emptyPos;
        if(motherNaturePos<6) emptyPos = motherNaturePos+6;
        else emptyPos = motherNaturePos-6;

        for (Island island: islandsList) {
            if(counter!=motherNaturePos && counter!=emptyPos) {
                island.addStudent(l.remove(0));
                //l.remove(0);
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


    public int getForbidCards() {
        return forbidCards;
    }

    public void setForbidCards(int forbidCards) {
        this.forbidCards = forbidCards;
    }

    public List<Tower> getTowersList() {
        return towersList;
    }

    public TowerColor getTowerColor() {
        if(towersList.isEmpty()){
            return null;
        }
        return towersList.get(0).getColor();
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
        Player oldDominatingPlayer = null;
        Player tmpPlayer = null ;
        for(int i = 0; i<playersList.size(); i++){
            if(playersList.get(i).hasTower()==true){ //it is impossible that tmpPlayer is not set here
                tmpPlayer = playersList.get(i); //tmpPlayer is the current dominating player, and allParity blocks if there is none that has to receive domain
            break;
            }
        }
        boolean allParity = true;


            for (Player p : playersList) {
                if(getTowerColor()!=null && getTowerColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                    oldDominatingPlayer = p;
                }

                System.out.println("influenza player "+p.getName()+": "+calculateInfluence(p,playersList));
                if (calculateInfluence(p,playersList) > calculateInfluence(tmpPlayer,playersList) && p.hasTower()) {
                    tmpPlayer = p;
                    allParity=false;
                }
                if(calculateInfluence(p,playersList)<calculateInfluence(tmpPlayer,playersList)){
                    allParity=false;
                }
            }
        //System.out.println("dominating player-------------- "+tmpPlayer.getName()+ " allpartiy="+allParity+" oldDom="+oldDominatingPlayer);
            if (allParity == false) {//if the var is false there is a change of domain, or else we do nothing
                if (tmpPlayer != oldDominatingPlayer){ //if we have to change the tower
                    if (oldDominatingPlayer!=null){
                    for (Tower t: towersList) {
                        oldDominatingPlayer.getDashboard().getTowersList().add(t);
                    }
                    }
                    int towersListSize = towersList.size();
                    for (int i =0; i<towersListSize; i++ ) {
                        towersList.remove(0);
                    }

                    for(int i = 0; i < weight; i++){
                        try{
                            Tower movedTower = tmpPlayer.getDashboard().getTowersList().remove(0);
                            towersList.add(movedTower);
                        }
                        catch (IndexOutOfBoundsException e){
                            //win todo
                        }
                    }
                }
            }


    }


    public void updateIslandDomainExpert(List<Player> playersList, Characters3and4and5 forbidCharacter){
        if(forbidCards > 0 && forbidCharacter!=null){
            forbidCards--;
            forbidCharacter.addForbidCard5();
            return;
        }
        updateIslandDomain(playersList);
    }


    private int calculateInfluence(Player player, List<Player> playersList){
        int influence = 0;
        Player teamPlayer = player.getTeamPlayer(playersList);
        for(PawnColor c : PawnColor.values()){
            int numOfThatColor = 0;
            for(Student s : studentsList){
                if(s.getColor() == c){
                    numOfThatColor++;
                }
            }
            if(player.getDashboard().getProfessorByColor(c) != null || teamPlayer.getDashboard().getProfessorByColor(c) != null){
                influence = influence + numOfThatColor;
            }
        }
        if (getTowerColor()!=null && getTowerColor().equals(player.getTowerColor())){
            System.out.println();
            System.out.println(getTowerColor());
            influence = influence + weight;
        }
        return influence;
    }

    public boolean mergeIsland(Island nextIsland){ //is to call more times
        if (getTowerColor()!=null && nextIsland.getTowerColor()!=null && getTowerColor() == nextIsland.getTowerColor()){
            weight = weight + nextIsland.weight;

            towersList.addAll(nextIsland.getTowersList());
            studentsList.addAll(nextIsland.getStudentsList());
            forbidCards = forbidCards + nextIsland.getForbidCards();

            return true;
        }
        return false;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }


    //for tests purposes
   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Island)) return false;
        Island island = (Island) o;
        return getForbidCards() == island.getForbidCards() && getWeight() == island.getWeight() && getTowersList().equals(island.getTowersList()) && getStudentsList().equals(island.getStudentsList());
    }

    /* @Override
    public int hashCode() {
        return Objects.hash(getForbidCard(), getWeight(), getTowersList(), getStudentsList());
    }

    public void setTower(Tower tower){
        towersList.add(tower);
    }

     */
}


