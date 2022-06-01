package it.polimi.ingsw.view.cli;



import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.client.ReducedCharacter;
import it.polimi.ingsw.model.enumerations.*;

import it.polimi.ingsw.network.server.*;
import it.polimi.ingsw.view.View;


import java.io.PrintStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

//for CLI representation


/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends View {

    private Thread keyboardManagerThread;
    private final PrintStream out;
    private static final String STR_ROW = "Row: ";
    private static final String STR_COLUMN = "Column: ";
    private static final String STR_POSITION = "Position ";
    private KeyboardManager keyboardManager;

    /**
     * Default constructor.
     */
    public Cli(ClientController controller) {
        out = System.out;
        //this.state = state;

        keyboardManager = new KeyboardManager(controller,this);
        keyboardManagerThread = new Thread(keyboardManager, "keyboardManager");
    }


    public void init() { //it is called by the ClientApp
        out.println("" +
                "d8888b.888Y88b\n" +
                "d88P. \n" +
                "Y88b                                 \n" +
                "Y888b.Y8888P.\n" +
                "Y888b.Y8888P. \n" +
                "d88P.  \n" +
                "Y88b  \n" +
                "d8888b.888Y88b'\n");

        out.println("Welcome to magic world of Eriantys!");


        onAskServerInfo();

    }



    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     */


    /**
     * Clears the CLI terminal.
     */
    public void clearCli() { //only on intellij doesn't work
        out.print(ColorCli.CLEAR);
        out.flush();
    }



    /**
     * Asks the server address and port to the user.
     *
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    @Override
    public void onAskServerInfo(){
            //serverInfo is a map with ip and port, they are String, but we parse the port into an Integer
            Map<String, String> serverInfo = new HashMap<>();
            String defaultAddress = "localhost";
            String defaultPort = "3333";
            boolean validInput;
            Scanner scanner = new Scanner(System.in);

            out.println("Please specify the following settings. The default value is shown between brackets.");

            do {
                out.print("Enter the server address [" + defaultAddress + "]: ");
                String address = scanner.nextLine();
                scanner.reset();
                if (address.equals("") || address.equals(" ") || address.equals("\n")) {
                    serverInfo.put("address", defaultAddress);
                    validInput = true;
                } else if (ClientController.isValidIpAddress(address)) {
                    serverInfo.put("address", address);
                    validInput = true;
                } else {
                    out.println("Invalid address!");
                    clearCli();
                    validInput = false;
                }
            } while (!validInput);

            do {
                out.print("Enter the server port [" + defaultPort + "]: ");
                String port = scanner.nextLine();
                scanner.reset();

                if (port.equals("") || port.equals(" ") || port.equals("\n")) {
                    serverInfo.put("port", defaultPort);
                    validInput = true;
                } else {
                    if (ClientController.isValidPort(port)) {
                        serverInfo.put("port", port);
                        validInput = true;
                    } else {
                        out.println("Invalid port!");
                        validInput = false;
                    }
                }
            } while (!validInput);

            notifyObserver(obs -> obs.onAskServerInfo(serverInfo));
            scanner.reset();
    }
    @Override
    public void onSendLoginRequest() {
        if(!keyboardManagerThread.isAlive()){
            keyboardManagerThread.start();
        }

        out.print("Enter name: ");
    }

    @Override
    public void onAskCreateOrJoin(){
        out.println("Type 'c' to create a new lobby and join it,or type 'j' to join an existing lobby");
    }


    public void sendNewLobbyRequest(){
        out.println("Enter Lobby name: (we prefer to avoid bad words)");
        out.println("After a space enter number of players allowed: (from 2 to 4)");
        out.println("After another space enter 'true' for expert mode, or false for normal mode (check the caps-lock)");

    }

    @Override
    public void onSendChooseLobby(List<Lobby> lobbylist){
        out.println("Write a lobby name to access: \n");
        int counter = 1;
        for(Lobby lobby : lobbylist){
            out.println("#" + counter + " Lobby name: " + lobby.getName());
            out.println("Number of players: " + lobby.getActualNumOfPlayers() + "/" + lobby.getNumOfPlayers());
            out.println("Mode: " + ( lobby.isExpertMode()? "Expert\n" : "Normal\n"));
            counter ++;
        }
        keyboardManager.setLobbiesList(lobbylist);
        return;
    }

    @Override
    public void onSendChooseTeam(Map<String,Integer> availablePlayers){
        clearCli();
        out.println("Choose a team player from the available ids:");
        for(Map.Entry<String,Integer> entry : availablePlayers.entrySet()){
            out.println( "Name: " + entry.getKey() + ", Id: " + entry.getValue());
        }
        //String input = null;
        //input = readLine();
        keyboardManager.setAvailablePlayers(availablePlayers);

        return;

    }

    @Override
    public void onSendChooseTowerColor(List<TowerColor> availableTowerColors){ //ONLY IN THREE PLAYERS GAME WE HAVE ALSO GRAY COLOR
        out.println("Choose a Tower Color from the available colors: ");
        for(TowerColor c : availableTowerColors){
            out.println( "Color: " + c );
        }
        keyboardManager.setAvailableTowerColors(availableTowerColors);
        return;

    }

    @Override
    public void onSendChooseWizard(List<Wizard> availableWizards){
        out.println("Choose a Wizard from the available wizards: ");
        for(Wizard wizard : availableWizards){
            out.println( "Wizard: " + wizard);
        }
        keyboardManager.setAvailableWizards(availableWizards);
        return;
    }


//    @Override
//    public void showAvailableLobbies(List<Lobby> lobbiesList) {
//
//    }
//
//    @Override
//    public void askRequestedLobby(List<Lobby> lobbiesList) {
//
//    }
//
//    @Override
//    public void askTeam(List<Player> playersList) {
//
//    }
//
//    @Override
//    public void showAvailableTeams(Map<String, Integer> players) {
//
//    }
//
//    @Override
//    public void askTowerColor() {
//
//    }
//
//    @Override
//    public void showAvailableTowerColors(List<TowerColor> availableTowerColors) {
//
//    }
//
//    @Override
//    public void askWizard() {
//
//    }
//
//    @Override
//    public void showAvailableWizards(List<Wizard> availableWizards) {
//
//    }
//
//    @Override
//    public void showTable(List<Island> islandsList, List<Cloud> cloudsList, int motherNaturePos) {
//
//    }
//
//    @Override
//    public void showThrownAssistant(Assistant thrownAssistant, int playerId) {
//
//    }
//
//    @Override
//    public void showDashboard(Dashboard dashboard, int playerId) {
//
//    }
//
//    @Override
//    public void showCharacterTable(List<Character> charactersList, AtomicInteger tableMoney, Map<Player, AtomicInteger> numOfMoneyMap) {
//
//    }
//
//    @Override
//    public void showAssistantsList(List<Assistant> assistantsList) {
//
//    }
//
//    @Override
//    public void showThrownAssistant(Assistant selectedAssistant) {
//
//    }
//
//    @Override
//    public void askStudentToMoveIsland(List<Island> entranceStudentsList, List<Island> islandsList) {
//
//    }
//
//    @Override
//    public void askStudentToMoveDashboard(List<Student> entranceStudentsList) {
//
//    }
//
//    @Override
//    public void askMotherNatureMove(List<Island> islandsList) {
//
//    }
//
//    @Override
//    public void askCloudExtraction(List<Cloud> cloudsList) {
//
//    }
//
//    @Override
//    public void askSelectedAssistant(List<Assistant> assistantsList) {
//
//    }
//
//    @Override
//    public void askUsedCharacter(List<Character> charactersList) {
//
//    }
//
//    @Override
//    public void showDisconnectionMessage(String text) {
//
//    }
//
//    @Override
//    public void showErrorAndExit(String error) {
//
//    }
//
//    @Override
//    public void showMatchInfo(List<Player> playersList, List<Island> islandsList, List<Cloud> cloudsList, List<Assistant> assistantsList, int motherNaturePos, AtomicInteger tableMoney, List<Character> charactersList, boolean expertMode) {
//
//    }
//
//    @Override
//    public void showWinMessage(String winner) {
//
//    }


    /**
     * @param toShow
     */
    @Override
    public void onShow(Object toShow) { //we can print in different way in base of the type
    if (toShow instanceof String){   //not typeOf...
        System.out.println(toShow);
        }
    }

    @Override
    public void updateCharactersList(List<ReducedCharacter> charactersList) {
        keyboardManager.setCharactersList(charactersList);
    }
}
