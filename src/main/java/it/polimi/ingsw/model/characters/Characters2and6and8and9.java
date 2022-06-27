package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.List;

/**
 * As the name suggests,this class implements characters 2,6,8 and 9
 */
public class Characters2and6and8and9 extends Character {

    //constructor

    private PawnColor selectedColor9;

    /**
     * Default constructor
     * selected color 9 is set to null
     * @param id
     * @param initialCost
     */
    public Characters2and6and8and9(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
        selectedColor9=null;
    }

    //methods
   /* private void updateIslandDomain2(Player cardPlayer, Island island, List<Player> playersList, Characters3and4and5 forbidCharacter){
        if(island.getForbidCard() > 0){
            island.setForbidCard(island.getForbidCard()-1);
            forbidCharacter.addForbidCard5();
            return;
        }

        Player oldDominatingPlayer = null;
        Player tmpPlayer = playersList.get(0); //tmpPlayer is the current dominating player
        boolean allParity = true;
        for (Player p : playersList) {
            if(island.getTower().getColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                oldDominatingPlayer = p;
            }

            if (modifiedCalculateInfluence2(island, p, cardPlayer, playersList) > modifiedCalculateInfluence2(island,tmpPlayer, cardPlayer,playersList) && p.hasTower()) {
                tmpPlayer = p;
                allParity=false;
            }
        }
        if (allParity == false) { //if the var is false there is a change of domain, or else we do nothing
            if (tmpPlayer != oldDominatingPlayer){ //if we have to change the tower
                for (Tower t: island.getTowersList()) {
                    oldDominatingPlayer.getDashboard().getTowersList().add(t);
                    island.getTowersList().remove(t);
                }
                for(int i = 0; i < island.getWeight(); i++){
                    try{
                        Tower movedTower = tmpPlayer.getDashboard().getTowersList().remove(0);
                        island.getTowersList().add(movedTower);
                    }
                    catch (IndexOutOfBoundsException e){
                        //win to do
                    }
                }
            }
        }
    }

    private int modifiedCalculateInfluence2(Island island, Player currPlayer, Player cardPlayer, List<Player> playersList){
        int influence = 0;
        Player teamPlayer = currPlayer.getTeamPlayer(playersList);
        Player professorHolderPlayer;
        PawnColor[] colors = {PawnColor.RED,PawnColor.GREEN,PawnColor.BLUE,PawnColor.YELLOW, PawnColor.PINK};

        for(PawnColor c : colors){
            professorHolderPlayer = null;
            int numOfThatColor = 0;
            for(Student s : island.getStudentsList()){
                if(s.getColor() == c){
                    numOfThatColor++;
                }
            }

            for (Player p: playersList){ //get the player which holds the professor of the color c
                if(p.getDashboard().getProfessorByColor(c)!=null){
                    professorHolderPlayer = p;
                    break;
                }
            }

            //i am the card player and i have the same number of students on the island of the current professor holder
            if(currPlayer == cardPlayer && professorHolderPlayer != null && professorHolderPlayer.getDashboard().getNumOfHallStudents(c) == currPlayer.getDashboard().getNumOfHallStudents(c)){
                influence = influence + numOfThatColor;
            }
            //i am the player that loses the professor
            else if(currPlayer != cardPlayer && professorHolderPlayer == currPlayer && cardPlayer.getDashboard().getNumOfHallStudents(c) == currPlayer.getDashboard().getNumOfHallStudents(c)) {
                //we do not increment the influence
            }
            //i already have the professor of that color
            else if((currPlayer.getDashboard().getProfessorByColor(c) != null)){
                influence = influence + numOfThatColor;
            }
            //my team player or myself have the professor of that color
            else if(teamPlayer.getDashboard().getProfessorByColor(c) != null){
                influence = influence + numOfThatColor;
            }
        }

        if (island.getTower().getColor().equals(currPlayer.getTowerColor())){
            influence = influence + island.getWeight();
        }
        return influence;
    } */ //old effect of second character

    /**
     * This method implements the effect of the character 2,which consist in a variation of the updateProfessorsLists method
     * @param playersList
     * @param cardPlayer
     * @param tableProfessorsList
     * @return false if an exception is detected
     * @return true if the effect is applied
     */
    public boolean modifiedUpdateProfessorsLists2(List<Player> playersList, Player cardPlayer, List<Professor> tableProfessorsList) {
        try {
            for (PawnColor currColor : PawnColor.values()) { //scan of all colors
                Player tmpPlayer = null; //temp variable to store the player that has to receive the professor
                Player currentProfessorPlayer = null; //temp variable to store the player that hold the professor

                for (Player p : playersList) { //we have to check if someone have to receive the currColorProfessor
                    if (p.getDashboard().getHallStudentsListByColor(currColor).size() > 0) {
                        tmpPlayer = p;
                        break;
                    }
                }
                if (tmpPlayer != null) {
                    for (Player p : playersList) { //for each color we check every player dashboard
                        if (p.getDashboard().getProfessorByColor(currColor) != null) { //when we find the player which hold the professor we store in the temp variable
                            currentProfessorPlayer = p;
                        }
                        if (p.getDashboard().getHallStudentsListByColor(currColor).size() > tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()) { //if we find a player that has more students than tmp player we update the variable
                            tmpPlayer = p;
                        } else if (p.getDashboard().getHallStudentsListByColor(currColor).size() == tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()) { //if the players have the same number of student the professor must remain to the old holder, except for the cardPlayer after the loop
                            if (p.getDashboard().getProfessorByColor(currColor) != null) {
                                tmpPlayer = p;
                            }

                        }
                    }

                    if (cardPlayer.getDashboard().getHallStudentsListByColor(currColor).size()==tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()){
                        tmpPlayer = cardPlayer;
                    }

                    if (tmpPlayer.getDashboard().getProfessorByColor(currColor) == null) { //if the player that should have the professor doesn't have it we must give it
                        Professor professorToMove = null;

                        for (Professor tableProfessor : tableProfessorsList) { //check if the professor is on the table and remove it
                            if (tableProfessor.getColor().equals(currColor)) {
                                professorToMove = tableProfessor;
                                tableProfessorsList.remove(tableProfessor);
                                break;
                            }
                        }  //it is not only for the beginning of the game, characters are used afterwards

                        if (professorToMove == null) { //the professor is in currentProfessorPlayer, else professor is on the table
                            professorToMove = currentProfessorPlayer.getDashboard().getProfessorByColor(currColor);
                            currentProfessorPlayer.getDashboard().getProfessorsList().remove(professorToMove);
                        }


                        tmpPlayer.getDashboard().getProfessorsList().add(professorToMove);

                    }

                }

            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    /**
     * This method implements the effect of the 6th character,which is a variation of the updateIslandDomain method
     * @param island
     * @param playersList
     * @param forbidCharacter
     */
    private void updateIslandDomain6(Island island,List<Player> playersList, Characters3and4and5 forbidCharacter) {
        if (island.getForbidCards() > 0) {
            island.setForbidCards(island.getForbidCards() - 1);
            forbidCharacter.addForbidCard5();
            return;
        }

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
                if (island.getTowerColor()!= null && island.getTowerColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                    oldDominatingPlayer = p;
                }

                if (modifiedCalculateInfluence6(island, p, playersList) > modifiedCalculateInfluence6(island, tmpPlayer, playersList) && p.hasTower()) {
                    tmpPlayer = p;
                    allParity = false;
                }
                if (modifiedCalculateInfluence6(island, p, playersList) < modifiedCalculateInfluence6(island, tmpPlayer, playersList) && p.hasTower()) {
                    allParity=false;
                }
            }

        for(Player p : playersList){
            if(p!=tmpPlayer && p.hasTower() && modifiedCalculateInfluence6(island, p, playersList) == modifiedCalculateInfluence6(island, tmpPlayer, playersList))
                allParity = true;
        }

            if (allParity == false) { //if the var is false there is a change of domain, or else we do nothing
                if (tmpPlayer != oldDominatingPlayer) { //if we have to change the tower
                    for (Tower t: island.getTowersList()) {
                        oldDominatingPlayer.getDashboard().getTowersList().add(t);
                    }
                    int towersListSize = island.getTowersList().size();
                    for (int i =0; i<towersListSize; i++ ) {
                        island.getTowersList().remove(0);
                    }
                    for (int i = 0; i < island.getWeight(); i++) {
                        try {
                            Tower movedTower = tmpPlayer.getDashboard().getTowersList().remove(0);
                            island.getTowersList().add(movedTower);
                        } catch (IndexOutOfBoundsException e) {
                            //win I can't add other towers, the controller will catch this todo
                        }
                    }
                }
            }

        }

    /**
     * this method is supplementary to the updateIslandDomain6 method
     * @param island
     * @param currPlayer
     * @param playersList
     * @return the player's influence
     */
    private int modifiedCalculateInfluence6(Island island, Player currPlayer, List<Player> playersList) {

        int influence = 0;
        Player teamPlayer = currPlayer.getTeamPlayer(playersList);
        PawnColor[] colors = {PawnColor.RED, PawnColor.GREEN, PawnColor.BLUE, PawnColor.YELLOW, PawnColor.PINK};

        for (PawnColor c : colors) {
            int numOfThatColor = 0;
            for (Student s : island.getStudentsList()) {
                if (s.getColor() == c) {
                    numOfThatColor++;
                }
            }
            if (currPlayer.getDashboard().getProfessorByColor(c) != null || teamPlayer.getDashboard().getProfessorByColor(c) != null) {
                influence = influence + numOfThatColor;
            }
        }
        return influence;
    }

    /**
     *This method implements the effect of the 8th character,which is a variation of the updateIslandDomain method
     * @param cardPlayer
     * @param island
     * @param playersList
     * @param forbidCharacter
     */
    private void updateIslandDomain8(Player cardPlayer,Island island,List<Player> playersList, Characters3and4and5 forbidCharacter) {
        if (island.getForbidCards() > 0) {
            island.setForbidCards(island.getForbidCards() - 1);
            forbidCharacter.addForbidCard5();
            return;
        }

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
                if (island.getTowerColor()!=null && island.getTowerColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                    oldDominatingPlayer = p;
                }

                if (modifiedCalculateInfluence8(island, p, cardPlayer, playersList) > modifiedCalculateInfluence8(island, tmpPlayer, cardPlayer, playersList) && p.hasTower()) {
                    tmpPlayer = p;
                    allParity = false;
                }
                if (modifiedCalculateInfluence8(island, p, cardPlayer, playersList) < modifiedCalculateInfluence8(island, tmpPlayer, cardPlayer, playersList) && p.hasTower()) {
                    allParity=false;
                }
            }
        for(Player p : playersList){
            if(p!=tmpPlayer && p.hasTower() && modifiedCalculateInfluence8(island, p,cardPlayer, playersList) == modifiedCalculateInfluence8(island, tmpPlayer,cardPlayer, playersList))
                allParity = true;
        }

            if (allParity == false) { //if the var is false there is a change of domain, or else we do nothing
                if (tmpPlayer != oldDominatingPlayer) { //if we have to change the tower
                    for (Tower t: island.getTowersList()) {
                        oldDominatingPlayer.getDashboard().getTowersList().add(t);
                    }
                    int towersListSize = island.getTowersList().size();
                    for (int i =0; i<towersListSize; i++ ) {
                        island.getTowersList().remove(0);
                    }
                    for (int i = 0; i < island.getWeight(); i++) {
                        try {
                            Tower movedTower = tmpPlayer.getDashboard().getTowersList().remove(0);
                            island.getTowersList().add(movedTower);
                        } catch (IndexOutOfBoundsException e) {
                            //win I can't add other towers, the controller will catch this todo
                        }
                    }
                }
            }
        }

    /**
     * this method is supplementary to the updateIslandDomain8 method
     * @param island
     * @param currPlayer
     * @param cardPlayer
     * @param playersList
     * @return
     */
    private int modifiedCalculateInfluence8(Island island, Player currPlayer, Player cardPlayer, List<Player> playersList){
        int influence = 0;
        Player teamPlayer = currPlayer.getTeamPlayer(playersList);
        PawnColor[] colors = {PawnColor.RED,PawnColor.GREEN,PawnColor.BLUE,PawnColor.YELLOW, PawnColor.PINK};

        for(PawnColor c : colors){
            int numOfThatColor = 0;
            for(Student s : island.getStudentsList()){
                if(s.getColor() == c){
                    numOfThatColor++;
                }
            }
            if(currPlayer.getDashboard().getProfessorByColor(c) != null || teamPlayer.getDashboard().getProfessorByColor(c) != null){
                influence = influence + numOfThatColor;
            }
        }
        if (island.getTowerColor()!=null && island.getTowerColor().equals(currPlayer.getTowerColor())){
            influence = influence + island.getWeight();
        }
        if(currPlayer == cardPlayer){
            influence = influence + 2;
        }
        return influence;
    }

    /**
     * 
     * @param island
     * @param playersList
     * @param selectedColor
     * @param forbidCharacter
     */
    private void updateIslandDomain9(Island island,List<Player> playersList,PawnColor selectedColor, Characters3and4and5 forbidCharacter) {
        if (island.getForbidCards() > 0) {
            island.setForbidCards(island.getForbidCards() - 1);
            forbidCharacter.addForbidCard5();
            return;
        }

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
                if (island.getTowerColor()!=null && island.getTowerColor().equals(p.getTowerColor()) && p.hasTower()) { //check on hasTower for 4 players
                    oldDominatingPlayer = p;
                }

                if (modifiedCalculateInfluence9(island, p, selectedColor, playersList) > modifiedCalculateInfluence9(island, tmpPlayer, selectedColor, playersList) && p.hasTower()) {
                    tmpPlayer = p;
                    allParity = false;
                }
                if (modifiedCalculateInfluence9(island, p, selectedColor, playersList) < modifiedCalculateInfluence9(island, tmpPlayer, selectedColor, playersList) && p.hasTower()) {
                    allParity = false;
                }
            }

        for(Player p : playersList){
            if(p!=tmpPlayer && p.hasTower() && modifiedCalculateInfluence9(island, p,selectedColor, playersList) == modifiedCalculateInfluence9(island, tmpPlayer,selectedColor, playersList))
                allParity = true;
        }

            if (allParity == false) { //if the var is false there is a change of domain, or else we do nothing
                if (tmpPlayer != oldDominatingPlayer) { //if we have to change the tower
                    for (Tower t: island.getTowersList()) {
                        oldDominatingPlayer.getDashboard().getTowersList().add(t);
                    }
                    int towersListSize = island.getTowersList().size();
                    for (int i =0; i<towersListSize; i++ ) {
                        island.getTowersList().remove(0);
                    }
                    for (int i = 0; i < island.getWeight(); i++) {
                        try {
                            Tower movedTower = tmpPlayer.getDashboard().getTowersList().remove(0);
                            island.getTowersList().add(movedTower);
                        } catch (IndexOutOfBoundsException e) {
                            //win I can't add other towers, the controller will catch this todo
                        }
                    }
                }
            }
        }



    private int modifiedCalculateInfluence9(Island island, Player currPlayer, PawnColor selectedColor, List<Player> playersList){
        int influence = 0;
        Player teamPlayer = currPlayer.getTeamPlayer(playersList);
        PawnColor[] colors = {PawnColor.RED, PawnColor.GREEN, PawnColor.BLUE, PawnColor.YELLOW, PawnColor.PINK};

        for (PawnColor c : colors) {
            if(c != selectedColor) {
                int numOfThatColor = 0;
                for (Student s : island.getStudentsList()) {
                    if (s.getColor() == c) {
                        numOfThatColor++;
                    }
                }
                if (currPlayer.getDashboard().getProfessorByColor(c) != null || teamPlayer.getDashboard().getProfessorByColor(c) != null) {
                    influence = influence + numOfThatColor;
                }
            }
        }
        if (island.getTowerColor()!=null && island.getTowerColor().equals(currPlayer.getTowerColor())){
            influence = influence + island.getWeight();
        }
        return influence;
    }


    public void updateIslandDomainCharacter(Player cardPlayer,Island island,List<Player> playersList, Characters3and4and5 forbidCharacter){
        switch (id){
            case 6:
                updateIslandDomain6(island,playersList,forbidCharacter);
                break;
            case 8:
                updateIslandDomain8(cardPlayer,island,playersList,forbidCharacter);
                break;
            case 9:
                updateIslandDomain9(island,playersList,selectedColor9,forbidCharacter);
                break;
        }
    }


    @Override
    public boolean applyEffect(CharacterParameters params) {
        switch (id){
            case 2:
                return modifiedUpdateProfessorsLists2(params.getPlayersList(), params.getPlayer(), params.getTableProfessorsList());
            case 6:
                return true;
            case 8:
                return true;
            case 9:
                selectedColor9=params.getSelectedColor();
                return true;
            default:
                return false;
        }


    }

    @Override
    public void initCharacter(CharacterParameters params) {
        //we don't need initialization
    }
}
