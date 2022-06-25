package it.polimi.ingsw;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.controllers.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Factory;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.RoundState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.view.cli.GamePrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * It is a main only to test something
 * ok Fra, ok Nic, ok marco, check the random problems with random features
 */
public class ProvaGit {

    public static void main( String[] args ) throws IOException {
        runPrintTest();
        //runControllerTest();
    }

    private static void runPrintTest(){
        ClientGameModel gameModel = generateClientGameModel();
        GamePrinter printer = new GamePrinter();
        printer.print(gameModel);
    }

    public static ClientGameModel generateClientGameModel(){
        ClientGameModel clientGameModel = new ClientGameModel();

        clientGameModel.setExpertMode(true);
        Island island = new Island();
        List<Student> studentsList= island.getStudentsList();
        studentsList.add(new Student(PawnColor.RED));
        studentsList.add(new Student(PawnColor.RED));
        studentsList.add(new Student(PawnColor.BLUE));
        studentsList.add(new Student(PawnColor.GREEN));
        studentsList.add(new Student(PawnColor.PINK));
        studentsList.add(new Student(PawnColor.YELLOW));
        island.setWeight(2);
        island.getTowersList().add(new Tower(TowerColor.BLACK));
        island.setForbidCards(4);


        List<ReducedIsland> islands = new ArrayList<>();
        ReducedIsland reducedIsland1 = new ReducedIsland(island);
        islands.add(reducedIsland1);
        island.setWeight(1);

        for(int i = 2; i<12;i++){
            ReducedIsland reducedIsland = new ReducedIsland(island);
            islands.add(reducedIsland);
        }
        clientGameModel.setIslandList(islands);


        Cloud cloud = new Cloud();
        List<Student> studentList = cloud.getStudents();
        studentList.add(new Student(PawnColor.RED));
        studentList.add(new Student(PawnColor.BLUE));
        studentList.add(new Student(PawnColor.YELLOW));

        List<ReducedCloud> clouds = new ArrayList<>();
        for(int i = 0; i<4;i++){
            ReducedCloud reducedCloud = new ReducedCloud(cloud);
            clouds.add(reducedCloud);
        }
        clientGameModel.setCloudList(clouds);


        Player p1 = new Player("Marco", 2);
        p1.setPlayerTowerColor(TowerColor.BLACK);
        p1.getAssistantDeck().setWizard(Wizard.ASIATIC);
        p1.setSelectedAssistant(5);
        p1.modifyMoney(6,new AtomicInteger(10));

        Dashboard dashboard = p1.getDashboard();
        List<Student> redList = dashboard.getHallStudentsListByColor(PawnColor.RED);
        List<Student> greenList = dashboard.getHallStudentsListByColor(PawnColor.GREEN);
        List<Student> blueList = dashboard.getHallStudentsListByColor(PawnColor.BLUE);

        redList.add(new Student(PawnColor.RED));
        redList.add(new Student(PawnColor.RED));
        greenList.add(new Student(PawnColor.GREEN));
        blueList.add(new Student(PawnColor.BLUE));

        List<Student> entranceList = dashboard.getEntranceList();
        entranceList.add(new Student(PawnColor.RED));
        entranceList.add(new Student(PawnColor.YELLOW));
        entranceList.add(new Student(PawnColor.GREEN));
        entranceList.add(new Student(PawnColor.BLUE));
        entranceList.add(new Student(PawnColor.YELLOW));

        List<Professor> professorList = dashboard.getProfessorsList();
        professorList.add(new Professor(PawnColor.BLUE));
        professorList.add(new Professor(PawnColor.RED));

        List<Tower> towerList = dashboard.getTowersList();
        towerList.add(new Tower(TowerColor.BLACK));
        towerList.add(new Tower(TowerColor.BLACK));
        towerList.add(new Tower(TowerColor.BLACK));
        towerList.add(new Tower(TowerColor.BLACK));

        List<ReducedPlayer> players = new ArrayList<>();
        for (int i=0;i<2;i++){
            ReducedPlayer reducedPlayer = new ReducedPlayer(p1);
            players.add(reducedPlayer);
        }
        clientGameModel.setPlayersList(players);

        clientGameModel.setAssistantList(p1.getAssistantDeck().getReducedAssistantsList());

        Character character = Factory.newCharacter(5);
        character.setUsed();

        List<ReducedCharacter> characters = new ArrayList<>();
        for (int i=0;i<3;i++){
            ReducedCharacter reducedCharacter = new ReducedCharacter(character);
            characters.add(reducedCharacter);
        }
        clientGameModel.setCharactersList(characters);
        clientGameModel.setMyPlayerId(2);
        return clientGameModel;
    }

    private static void runControllerTest(){
        Controller controller = new Controller();
        GameModel game = controller.getGame();


        controller.configure(2,false);

        Player p1 = new Player("Pippo", 1); //the players will be stored in the server class
        Player p2 = new Player("Topolino", 2);

        controller.addPlayer(p1);
        controller.addPlayer(p2);

        controller.chooseTowerColor(1,TowerColor.WHITE);
        controller.chooseTowerColor(2,TowerColor.BLACK);

        controller.chooseWizard(1, Wizard.SAGE);
        controller.chooseWizard(2,Wizard.WITCH);

        printIslands(game);
        printDashboards(game);

        printClouds(game);

        System.out.println("------------------------Started phase:"+ game.getCurrRound().getStage()+"----------------------------------------------");

        controller.selectAssistant(2);
        controller.selectAssistant(4);

        if(game.getCurrRound().getStage() == RoundState.ACTION_STATE){
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

        controller.takeFromCloud(1);

        printClouds(game);
        printDashboards(game);
    }

    private static void printIslands(GameModel game){
        for (int i=0; i<12;i++){
            Island island = game.getIslandByIndex(i);
            System.out.print("----------ISOLA "+i+"---------- ");
            System.out.println("Dimensione: " +island.getWeight()+" Forbid card: "+island.getForbidCards());
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

        ClientGameModel clientGameModel = generateClientGameModel();
        List<ReducedIsland> islands = new ArrayList<>();
        for(Island i : game.getIslandsList()){
            islands.add(new ReducedIsland(i));
        }
        clientGameModel.setIslandList(islands);
        clientGameModel.setMotherNaturePos(game.getMotherNaturePos());
        GamePrinter printer = new GamePrinter();
        printer.print(clientGameModel);
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
