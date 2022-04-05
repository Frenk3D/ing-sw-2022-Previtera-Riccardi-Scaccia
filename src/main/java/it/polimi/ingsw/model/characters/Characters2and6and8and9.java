package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.Tower;
import it.polimi.ingsw.model.enumerations.PawnColor;

import java.util.List;

public class Characters2and6and8and9 extends Character {

    //constructor

    public Characters2and6and8and9(int id, int initialCost) {
        this.id=id;
        this.initialCost=initialCost;
    }

    //methods
    private void updateIslandDomain2(Player cardPlayer, Island island, List<Player> playersList, Characters3and4and5 forbidCharacter){
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
                        //win todo
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
    }

    private void updateIslandDomain6(Island island,List<Player> playersList, Characters3and4and5 forbidCharacter){
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

            if (modifiedCalculateInfluence6(island, p, playersList) > modifiedCalculateInfluence6(island,tmpPlayer,playersList) && p.hasTower()) {
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
                        //win todo
                    }
                }
            }
        }

    }

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
    private void updateIslandDomain8(Player cardPlayer,Island island,List<Player> playersList, Characters3and4and5 forbidCharacter){
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

            if (modifiedCalculateInfluence8(island, p, cardPlayer, playersList) > modifiedCalculateInfluence8(island,tmpPlayer, cardPlayer,playersList) && p.hasTower()) {
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
                        //win todo
                    }
                }
            }
        }
    }

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
        if (island.getTower().getColor().equals(currPlayer.getTowerColor())){
            influence = influence + island.getWeight();
        }
        if(currPlayer == cardPlayer){
            influence = influence + 2;
        }
        return influence;
    }

    private void updateIslandDomain9(Island island,List<Player> playersList,PawnColor selectedColor, Characters3and4and5 forbidCharacter){
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

            if (modifiedCalculateInfluence9(island, p, selectedColor, playersList) > modifiedCalculateInfluence9(island,tmpPlayer,selectedColor,playersList) && p.hasTower()) {
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
                        //win todo
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
        if (island.getTower().getColor().equals(currPlayer.getTowerColor())){
            influence = influence + island.getWeight();
        }
        return influence;
    }

    @Override
    public void applyEffect(CharacterParameters params) {
        switch (id){
            case 2:
                updateIslandDomain2(params.getPlayer(), params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 6:
                updateIslandDomain6(params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 8:
                updateIslandDomain8(params.getPlayer(),params.getIsland(),params.getPlayersList(), params.getForbidCharacter());
                break;

            case 9:
                updateIslandDomain9(params.getIsland(),params.getPlayersList(),params.getSelectedColor(), params.getForbidCharacter());
                break;

            default:
                break;
        }


    }
}