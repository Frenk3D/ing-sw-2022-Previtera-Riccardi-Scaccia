import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.TowerColor;

/** Deselezionare sempre .idea e pom.xml sia da push che da pull per evitare problemi!!!
 * Hello world!
 * ok Fra, ok Nic, ok marco, sometimes gives problem because we force playerId, and check the random problems with random features
 */
public class ProvaGit
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );
        Controller controller = new Controller();
        GameModel game = controller.getGame();

        game.setNumOfPlayers(2);
        game.setExpertMode(false);
        Player p1 = new Player("Pippo", 1);
        Player p2 = new Player("Topolino", 2);
        p1.setPlayerTowerColor(TowerColor.WHITE);
        p2.setPlayerTowerColor(TowerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.start();

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

        for (int i=0; i<2; i++){
            Cloud c = game.getCloudByIndex(i);
            System.out.println("----------NUVOLA "+i+"---------- ");
            for (Student student : c.getStudents()){
                System.out.println("Studente "+student.getColor());
            }
        }

        for (Player p : game.getPlayersList()){
            System.out.println("\n-----------Player "+p.getName()+"----------");
            for(Student s : p.getDashboard().getEntranceList()){
                System.out.println("Studente: "+s.getColor());
            }
        }

        /*
        game.getPlayerById(1).setSelectedAssistant(2); //pippo
        game.getPlayerById(2).setSelectedAssistant(7); //topolino

        System.out.println("\n\nSTART NEW ROUND!");
        System.out.println("round stage: "+game.getCurrRound().getStage());
        game.getCurrRound().initRound(game.getPlayersList(),game.getCloudsList(),game.getBag());
        System.out.println("The turn is of "+game.getCurrPlayer().getName());
        */

        for (int i=0; i<2; i++){
            Cloud c = game.getCloudByIndex(i);
            System.out.println("----------NUVOLA "+i+"---------- ");
            for (Student student : c.getStudents()){
                System.out.println("Studente "+student.getColor());
            }
        }


        System.out.println("init " + game.getCurrRound().getStage());
        controller.selectAssistant(1,5);
        System.out.println("butto assistente 1 "+ game.getCurrRound().getStage());
        controller.selectAssistant(2,4);
        System.out.println("butto assistente 2 "+ game.getCurrRound().getStage());


        for (int i=0; i<2; i++){
            Cloud c = game.getCloudByIndex(i);
            System.out.println("----------NUVOLA "+i+"---------- ");
            for (Student student : c.getStudents()){
                System.out.println("Studente "+student.getColor());
            }
        }

        System.out.println("The turn is of " + game.getCurrPlayer().getName()); }
}
