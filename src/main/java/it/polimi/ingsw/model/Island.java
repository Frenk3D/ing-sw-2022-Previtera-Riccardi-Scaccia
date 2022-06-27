package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Characters3and4and5;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * this class implements the game object island
 * there are 12 islands in the game
 * the islands can contain students,towers and mother nature
 * the island's weight is an integer that specifies if the island is a merge of different single islands
 */
public class Island {
    //attributes
    private int forbidCards;
    private int weight;
    private List<Tower> towersList;
    private List<Student> studentsList;

    //Methods
    //constructor

    /**
     * the island weight is set to 1
     * the numbers of forbid cards is set to 0
     * the rest is a default constructor
     */
    public Island(){
        weight = 1;
        forbidCards = 0;
        towersList= new ArrayList<>();
        studentsList= new ArrayList<Student>();
    }

    /**
     * Adds the first students on the islands
     * The students are not added on the island with mother nature on it,and the opposite island
     * @param islandsList
     * @param motherNaturePos
     * @param bag
     */
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

    /**
     *
     * @return the list of generated islands
     */
    public static ArrayList<Island> generateIslandsList(){
        ArrayList<Island> islandsList= new ArrayList<>();
        for(int i=0; i<12; i++){
            islandsList.add(new Island());
        }
        return islandsList;
    }

    /**
     *
     * @return the forbid cards on an island
     */
    public int getForbidCards() {
        return forbidCards;
    }

    /**
     * Sets the forbid cards
     * @param forbidCards
     */
    public void setForbidCards(int forbidCards) {
        this.forbidCards = forbidCards;
    }

    /**
     *
     * @return the list of towers of an island
     */
    public List<Tower> getTowersList() {
        return towersList;
    }

    /**
     *
     * @return the color of the tower(s) on the island
     */
    public TowerColor getTowerColor() {
        if(towersList.isEmpty()){
            return null;
        }
        return towersList.get(0).getColor();
    }

    /**
     * Adds a tower to the island
     * @param tower
     */
    public void addTower(Tower tower){
        towersList.add(tower);
    }

    /**
     *
     * @return the list of students on the island
     */
    public List<Student> getStudentsList() {
        return studentsList;
    }

    /**
     * Sets the list of students on the island
     * @param studentsList
     */
    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    /**
     * Adds a student to the island
     * @param student
     */
    public void addStudent(Student student){
        studentsList.add(student);
    }

    /**
     * Updates the player domain of the island
     * if the domain changes a tower of the player's tower color is added to the island
     * @param playersList
     */
    public void updateIslandDomain(List<Player> playersList) {
        Player oldDominatingPlayer = null;
        Player tmpPlayer = null ;
        for(int i = 0; i<playersList.size(); i++){
            if(playersList.get(i).hasTower()==true){ //it is impossible that tmpPlayer is not set here
                tmpPlayer = playersList.get(i); //tmpPlayer is the current dominating player, and allParity blocks if there is none that has to receive domain
            break;
            }
        }
        boolean allParity = true; //if there is a situation of parity in the domain


            for (Player p : playersList) {
                if(getTowerColor()!=null && getTowerColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                    oldDominatingPlayer = p;
                }

                if (calculateInfluence(p,playersList) > calculateInfluence(tmpPlayer,playersList) && p.hasTower()) {
                    tmpPlayer = p;
                    allParity=false;
                }
                if(calculateInfluence(p,playersList)<calculateInfluence(tmpPlayer,playersList)){
                    allParity=false;
                }
            }

            for(Player p : playersList){
                if(p!=tmpPlayer && p.hasTower() && calculateInfluence(p,playersList) == calculateInfluence(tmpPlayer,playersList))
                    allParity = true;
            }


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

    /**
     * Expert mode version of the update island domain method
     * the domain depends on forbid cards as well
     * @param playersList
     * @param forbidCharacter
     */
    public void updateIslandDomainExpert(List<Player> playersList, Characters3and4and5 forbidCharacter){
        if(forbidCards > 0 && forbidCharacter!=null){
            forbidCards--;
            forbidCharacter.addForbidCard5();
            return;
        }
        updateIslandDomain(playersList);
    }

    /**
     * Calculates the influence of a player on the island,
     * the island's domain is updated depending on the influence of the player
     * @param player
     * @param playersList
     * @return the influence of a given player on the island
     */
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
            influence = influence + weight;
        }
        return influence;
    }

    /**
     * If two adjacent islands share the same players domain they are merged into a single island
     * The number of islands merged defines the island's weight
     * @param nextIsland
     * @return the merged island
     */
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

    /**
     * Sets the island's weight
     * @param weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     *
     * @return the island's weight
     */
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


