package it.polimi.ingsw.model;

import java.util.List;

public class Turn {
    //attributes
    private int stage;
    private Player currPlayer;
    private Character usedCharacter;

    public Turn() {
    }

    //methods
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Character getUsedCharacter() {
        return usedCharacter;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setCurrPlayer(Player name) {
        this.currPlayer = name;
    }

    public void updateProfessorsLists(List<Player> playersList, List<Professor> tableProfessorsList){
        PawnColor[] colors = {PawnColor.RED,PawnColor.GREEN,PawnColor.BLUE,PawnColor.YELLOW, PawnColor.PINK};

        for (PawnColor currColor : colors){ //scan of all colors
            Player tmpPlayer = playersList.get(0); //temp variable to store the player that has to recive the professor
            Player currentProfessorPlayer = null; //temp variable to store the player that hold the professor

            for(Player p : playersList){ //for each color we check every player dashboard
                if(p.getDashboard().getProfessorByColor(currColor)!=null){ //when we find the player which hold the professor we store in the temp variable
                    currentProfessorPlayer=p;
                }

                if(p.getDashboard().getHallStudentsListByColor(currColor).size() > tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()){ //if we find a player that has more students than tmp player we update the variable
                    tmpPlayer = p;
                }

                else if(p.getDashboard().getHallStudentsListByColor(currColor).size() == tmpPlayer.getDashboard().getHallStudentsListByColor(currColor).size()){ //if the players have the same number of student the professor must remain to the old holder
                    if(p.getDashboard().getProfessorByColor(currColor)!=null){
                        tmpPlayer = p;
                    }
                }
            }

            if(tmpPlayer.getDashboard().getProfessorByColor(currColor)==null){ //if the player that should have the professor doesnt have it we must give it
                Professor professorToMove=null;

                for(Professor tableProfessor : tableProfessorsList){ //check if the professor is on the table and remove it
                    if(tableProfessor.getColor().equals(currColor)){
                        professorToMove=tableProfessor;
                        tableProfessorsList.remove(tableProfessor);
                        break;
                    }
                }

                if (professorToMove==null){ //the professor is in currentProfessorPlayer, else professor is on the table
                    professorToMove = currentProfessorPlayer.getDashboard().getProfessorByColor(currColor);
                    currentProfessorPlayer.getDashboard().getProfessorsList().remove(professorToMove);
                }


                tmpPlayer.getDashboard().getProfessorsList().add(professorToMove);

            }

        }

    }

    public void updateIslandList(List<Island> islandsList){
        for (int i = 0; i<islandsList.size()-1; i++){
            if(islandsList.get(i).mergeIsland(islandsList.get(i+1)) == true){
                islandsList.remove(i+1);
            }
        }

        if(islandsList.get(0).mergeIsland(islandsList.get(islandsList.size())) == true){
            islandsList.remove(islandsList.size());
        }
    }

}
