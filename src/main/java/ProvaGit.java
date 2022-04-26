import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.message.LoginRequestMessage;
import it.polimi.ingsw.network.message.Message;

/** Deselezionare sempre .idea e pom.xml sia da push che da pull per evitare problemi!!!
 * Hello world!
 * ok Fra, ok Nic, ok marco, sometimes gives problem because we force playerId, and check the random problems with random features
 */
public class ProvaGit {

    public static void main( String[] args ){
        Controller controller = new Controller();
        GameModel game = controller.getGame();

        game.setNumOfPlayers(2);
        game.setExpertMode(false);
        Player p1 = new Player("Pippo", 1);
        Player p2 = new Player("Topolino", 2);
        p1.setPlayerTowerColor(TowerColor.WHITE);
        p2.setPlayerTowerColor(TowerColor.BLACK);
        p1.setTeam(1);
        p2.setTeam(2);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.start();

        printIslands(game);
        printDashboards(game);

        controller.selectAssistant(2);
        controller.selectAssistant(4);

        printClouds(game);

        if(game.getCurrRound().getStage()== RoundState.ACTION_STATE){
            System.out.println("The turn is of " + game.getCurrPlayer().getName());
        }
        else return;

        System.out.println("------------------------Started phase:"+ game.getCurrRound().getCurrTurn().getStage()+"----------------------------------------------");
        controller.moveStudentDashboard(1);
        controller.moveStudentDashboard(4);
        controller.moveStudentIsland(1,7);

        printDashboards(game);
        printIslands(game);

        System.out.println("--------------------------Started phase:"+ game.getCurrRound().getCurrTurn().getStage()+"---------------------------------------------");
        controller.moveMotherNature(7);

        System.out.println("--------------------------Started phase:"+ game.getCurrRound().getCurrTurn().getStage()+"----------------------------------------------");
        printDashboards(game);
        printIslands(game);

        controller.takeFromCloud(10);

        printClouds(game);
        printDashboards(game);
    }

    private static void printIslands(GameModel game){
        for (int i=0; i<12;i++){
            Island island = game.getIslandByIndex(i);
            System.out.print("----------ISOLA "+i+"---------- ");
            System.out.println("Dimensione: " +island.getWeight()+" Forbid card: "+island.getForbidCard());
            if(game.getMotherNaturePos()==i){
                System.out.println("****MOTHER NATURE");
            }
            for(Student s : island.getStudentsList()){
                System.out.println("Studente: "+s.getColor());
            }
            for (Tower t : island.getTowersList()){
                System.out.println("Torre "+t.getColor());
            }
        }
    }

    private static void printDashboards(GameModel game){
        for (Player p : game.getPlayersList()){
            System.out.println("\n-----------Player "+p.getName()+"----------");

            for(Student s : p.getDashboard().getEntranceList()){
                System.out.println("Studente: "+s.getColor());
            }

            for(PawnColor c: PawnColor.values()){
                System.out.println("Colore "+c+ " "+p.getDashboard().getHallStudentsListByColor(c).size()+" Professore: "+p.getDashboard().getProfessorByColor(c));
            }
        }
    }

    private static void printClouds(GameModel game){
        for (int i=0; i<2; i++){
            Cloud c = game.getCloudByIndex(i);
            System.out.println("----------NUVOLA "+i+"---------- ");
            for (Student student : c.getStudents()){
                System.out.println("Studente "+student.getColor());
            }
        }
    }
}
