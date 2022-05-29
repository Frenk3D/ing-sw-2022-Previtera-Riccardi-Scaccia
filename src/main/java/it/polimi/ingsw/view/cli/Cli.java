package it.polimi.ingsw.view.cli;



import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.*;

import it.polimi.ingsw.network.client.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.*;
import it.polimi.ingsw.observer.*;
import it.polimi.ingsw.view.View;


import java.io.PrintStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

//for CLI representation
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends View {

    private final PrintStream out;
    private final Scanner input;
    private Thread inputThread;

    private static final String STR_INPUT_FAILED = "User input failed.";

    private static final String STR_ROW = "Row: ";
    private static final String STR_COLUMN = "Column: ";
    private static final String STR_POSITION = "Position ";


    /**
     * Default constructor.
     */
    public Cli() {
        out = System.out;
        input = new Scanner(System.in);
    }


    public void init() { //it is called by the ClientApp
        out.println("" +
                "d8888b.888Y88b\n" +
                "d88P \n" +
                "Y88b.                                 \n" +
                "Y888b.Y8888P.\n" +
                "Y888b.Y8888P. \n" +
                "d88P.  \n" +
                "Y88b  \n" +
                "d8888b.888Y88b\n");

        out.println("Welcome to magic world of Eriantys!");


        onAskServerInfo();

    }



    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     */
    public String readLine(){
        String cmd = null;

        try {
            input.reset();
            cmd = input.nextLine();
        }
        catch (NoSuchElementException | IllegalStateException e){
            out.println(STR_INPUT_FAILED);
            Thread.currentThread().interrupt();
        }
        return cmd;
    }


    /**
     * Clears the CLI terminal.
     */
    public void clearCli() {
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
            String defaultPort = "16847";
            boolean validInput;

            out.println("Please specify the following settings. The default value is shown between brackets.");

            do {
                out.print("Enter the server address [" + defaultAddress + "]: ");

                String address = readLine();

                if (address.equals("")) {
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
                String port = readLine();

                if (port.equals("")) {
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
            // ServerInfoMessage serverInfoMessage = new ServerInfoMessage(MessageType.SERVER_INFO,8888 ,serverInfo);
            // notifyObserver(serverInfoMessage);
    }
    @Override
    public void onSendLoginRequest() {
        out.println("Enter name: ");
        String scanIn = readLine();
        notifyObserver(obs -> obs.onSendLoginRequest(scanIn));
        //StringMessage loginRequest = new StringMessage(MessageType.LOGIN_REQUEST, 8888, input);
    }

    @Override
    public void onAskCreateOrJoin(){
        //Scanner scanIn = new Scanner(System.in);
        out.println("Type 'c' to create a new lobby and join it,or type 'j' to join an existing lobby");
        String input = null;
        input = readLine();
        if(input.equals("c")){
            sendNewLobbyRequest();      //or I can write everything here
        }
        else if(input.equals("j")){
            sendLobbiesRequest();
        }
        else{
            onShow("Incorrect input,try again!");
            onAskCreateOrJoin();        }
    }


    public void sendNewLobbyRequest(){
        out.println("Enter Lobby name: ");
        String nameInput = null;
        nameInput = readLine();

        out.println("Enter Number of players allowed: ");
        String numberInput = null;

        numberInput = readLine();

        int numOfPlayers = Integer.parseInt(numberInput);
        out.println("Enter 'true' for expert mode,or type anything else for normal mode");
        String trueInput = null;

        trueInput = readLine();

        boolean expertMode = Boolean.parseBoolean(trueInput);
        String finalNameInput = nameInput;
        notifyObserver(obs -> obs.onSendNewLobbyRequest(finalNameInput,numOfPlayers,expertMode));
    }


    public void  sendLobbiesRequest(){
        notifyObserver(obs -> obs.onSendLobbiesRequest());
    }

    @Override
    public void onSendChooseLobby(List<Lobby> lobbylist){
        out.println("Write a lobby name to access: ");
        int counter = 1;
        for(Lobby lobby : lobbylist){
            out.println("#" + counter + " Lobby name: " + lobby.getName());
            out.println("Number of players: " + lobby.getActualNumOfPlayers() + "/" + lobby.getNumOfPlayers());
            out.println("Mode: " + ( lobby.isExpertMode()? "Expert\n" : "Normal\n"));
            counter ++;
        }
        String input = null;

        input = readLine();

        for(Lobby lobby : lobbylist){
            if(input.equals(lobby.getName())){
                String finalInput = input;
                notifyObserver(obs -> obs.onSendChooseLobby(finalInput));
                return;
            }
        }
        out.println("This lobby doesn't exist! ");
        onSendChooseLobby(lobbylist);

    }

    @Override
    public void onSendChooseTeam(Map<String,Integer> availablePlayers){
        out.println("Choose a team player from the available ids: ");
        for(Map.Entry<String,Integer> entry : availablePlayers.entrySet()){
            out.println( "Name: " + entry.getKey() + ", Id: " + entry.getValue());
        }
        String input = null;

        input = readLine();

        for(Map.Entry<String,Integer> entry : availablePlayers.entrySet()){
            if(input.equals(entry.getValue())){
                int finalInput = Integer.parseInt(input);
                notifyObserver(obs -> obs.onSendChooseTeam(finalInput));
                return;
            }
        }
        out.println("This player doesn't exist! ");
        onSendChooseTeam(availablePlayers);

    }

    @Override
    public void onSendChooseTowerColor(List<TowerColor> availableTowerColors){
        out.println("Choose a Tower Color from the available colors: ");
        for(TowerColor c : availableTowerColors){
            out.println( "Color: " + c );
        }
        String input = null;

        input = readLine();

        for(TowerColor c : availableTowerColors){
            if(input.equals(c.toString())){
                notifyObserver(obs -> obs.onSendChooseTowerColor(c));
                return;
            }
        }
        out.println("This color isn't available! ");
        onSendChooseTowerColor(availableTowerColors);

    }

    @Override
    public void onSendChooseWizard(List<Wizard> availableWizards){
        out.println("Choose a Wizard from the available wizards: ");
        for(Wizard wizard : availableWizards){
            out.println( "Wizard: " + wizard);
        }
        String input = null;
        input = readLine();

        for(Wizard wizard : availableWizards){
            if(input.equals(wizard.toString())){
                notifyObserver(obs -> obs.onSendChooseWizard(wizard));
                return;
            }
        }
        out.println("This wizard isn't available! ");
        onSendChooseWizard(availableWizards);
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

}
