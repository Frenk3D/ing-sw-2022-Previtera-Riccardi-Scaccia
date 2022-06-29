package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.client.ClientGameModel;
import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.server.Lobby;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//for CLI representation, we don't need some parameters because we have the instance of clientGameModel in keyboardManager and we have the GamePrinter, but the interface for GUI need?


/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends View {

    private static final String STR_ROW = "Row: ";
    private static final String STR_COLUMN = "Column: ";
    private static final String STR_POSITION = "Position ";
    private final PrintStream out;
    private final Thread keyboardManagerThread;
    private final KeyboardManager keyboardManager;
    private final GamePrinter gamePrinter;

    /**
     * Default constructor.
     */
    public Cli(ClientController controller) {
        out = System.out;
        //this.state = state;

        keyboardManager = new KeyboardManager(controller, this);
        keyboardManagerThread = new Thread(keyboardManager, "keyboardManager");
        gamePrinter = new GamePrinter();
    }


    public void init() { //it is called by the ClientApp
        String welcome = "███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗\n" +
                "██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝\n" +
                "█████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗\n" +
                "██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║\n" +
                "███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║\n" +
                "╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝";


        out.println(welcome);

        out.println("\n             Welcome to the magic world of Eriantys!");
        out.println("Have fun, from Marco Previtera, Francesco Riccardi and Nicolò Scaccia\n");


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
        out.print(ColorCli.RESET);
        out.print(ColorCli.CLEAR);
        out.flush();
    }

    /**
     * Asks the server address and port to the user.
     */
    @Override
    public void onAskServerInfo() {
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
        if (!keyboardManagerThread.isAlive()) {
            keyboardManagerThread.start();
        }

        out.print("Enter name: ");
    }

    @Override
    public void onAskCreateOrJoin() {
        clearCli();
        out.println("Type 'c' to create a new lobby and join it, or type 'j' to join an existing lobby");
    }


    public void sendNewLobbyRequest() {
        out.println("Enter [Lobby name] [Number of players] [Expert mode: true or false]");

    }

    @Override
    public void onSendChooseLobby(List<Lobby> lobbylist) {
        clearCli();
        out.println("Write a lobby name to access: \n");
        int counter = 1;
        for (Lobby lobby : lobbylist) {
            out.println("#" + counter + " Lobby name: " + lobby.getName());
            out.println("Number of players: " + lobby.getActualNumOfPlayers() + "/" + lobby.getNumOfPlayers());
            out.println("Mode: " + (lobby.isExpertMode() ? "Expert\n" : "Normal\n"));
            counter++;
        }
        keyboardManager.setLobbiesList(lobbylist);
    }

    @Override
    public void onSendChooseTeam(Map<String, Integer> availablePlayers) {
        clearCli();
        out.println("Choose a team player from the available ids:");
        for (Map.Entry<String, Integer> entry : availablePlayers.entrySet()) {
            out.println("Name: " + entry.getKey() + ", Id: " + entry.getValue());
        }
        keyboardManager.setAvailablePlayers(availablePlayers);
    }

    @Override
    public void onSendChooseTowerColor(List<TowerColor> availableTowerColors) { //ONLY IN THREE PLAYERS GAME WE HAVE ALSO GRAY COLOR
        clearCli();
        out.println("Choose a Tower Color from the available colors: ");
        for (TowerColor c : availableTowerColors) {
            out.println("Color: " + c);
        }
        keyboardManager.setAvailableTowerColors(availableTowerColors);
    }

    @Override
    public void onSendChooseWizard(List<Wizard> availableWizards) {
        clearCli();
        out.println("Choose a Wizard from the available wizards: ");
        for (Wizard wizard : availableWizards) {
            out.println("Wizard: " + wizard);
        }
        keyboardManager.setAvailableWizards(availableWizards);
    }

    @Override
    public void onSendSelectAssistant() {
        out.print("Choose an Assistant id from the list: ");
    }

    @Override
    public void onAskWhereToMoveStudent() {
        out.print("Type d to move a student in the dashboard, or type i to move the student on an island: ");
    }

    public void sendMoveStudentDashboard() {
        out.print("Select a student (index) from your entrance list: ");
    }

    public void sendMoveStudentIsland() {
        out.print("Select a student index from your entrance list and a island index where to put: ");
    }

    @Override
    public void onSendMoveMotherNature() {
        out.print("Choose the island index where to move mother nature: ");

    }

    @Override
    public void onSendChooseCloud() {
        out.print("Choose the cloud index where do you want to pick the students: ");

    }

    /**
     * @param characterId
     */
    @Override
    public void onAskCharacterParameters(int characterId) {
        switch (characterId) {
            case 1:
                out.println("Choose a student index from the card and an island index where to put the student");
                break;
            case 3:
                out.println("Choose an island index where to calculate the influence");
                break;
            case 5:
                out.println("Choose an island index where to put a forbid card");
                break;
            case 7:
                out.println("Choose up to three students from the card and after the students to be replaced in the entrance"); //check the parity
                break;
            case 9:
                out.println("Choose a Pawn Color that will be not included in the calculation of the influence");
                break;
            case 10:
                out.println("Choose up to two studentsColor from the hall and after the students to be replaced in the entrance");
                break;
            case 11:
                out.println("Choose a student from the card to put in the hall");
                break;
            case 12:
                out.println("Choose a Pawn Color of the students to put in the bag");
                break;
            default: //it will never happen
                out.println("Error in the application");
                break;
        }
        keyboardManager.setUsedCharacter(characterId);

    }


    @Override
    public void onShow(Object toShow) { //we can print in different way in base of the type  //not typeOf...
        System.out.println(toShow);

    }

    @Override
    public void updateClientGameModel(ClientGameModel clientGameModel) {
        keyboardManager.setClientGameModel(clientGameModel);
    }

    @Override
    public void onShowGame(ClientGameModel clientGameModel) {
        clearCli();
        gamePrinter.print(clientGameModel);
    }

    @Override
    public void onShowPlayerJoin(List<String> playersList) {
        clearCli();
        out.println("The players in the lobby are:");
        for (String player : playersList) {
            out.println("Player: " + player);
        }
        out.println("Waiting for other players...");

    }

    @Override
    public void onShowChosenTeam(String toShow) {
        out.println(toShow);
    }


}
