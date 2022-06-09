package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.ClientState;
import it.polimi.ingsw.network.server.Lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KeyboardManager implements Runnable{
    private ClientController controller; //only to check the state
    private Cli cli;
    private String userInput;
    private static final String STR_INPUT_FAILED = "User input failed.";

    private List<Lobby> lobbiesList = null;
    private Map<String, Integer> availablePlayers= null;
    private List<TowerColor> availableTowerColors = null;
    private List<Wizard> availableWizards = null;
    private ClientGameModel clientGameModel = null;  //from now on we take the lists from here

    public KeyboardManager(ClientController controller, Cli cli){
        this.controller = controller;
        this.cli = cli;
    }

    @Override
    public void run() {
        Scanner scanInput = new Scanner(System.in);
        while (!Thread.currentThread().isInterrupted()) {  //everytime active to receive input
            //scanInput.reset();
            userInput = scanInput.nextLine(); //whenever the player presses enter this line gets executed
            //scanInput.next();
            System.out.println("login: " + userInput + " state: " + controller.getClientState()); //only for debug
            if (userInput.equals("use_character") && controller.getClientState() != ClientState.WAITING_FOR_YOUR_TURN) {
                if(clientGameModel.getCharactersList().isEmpty() || clientGameModel.getCharactersList()==null){
                    System.out.println("Characters not available");
                }
                else{
                    System.out.println("Choose a character with his parameters");
                    //manage the characters now with the parameters, we have also a client state?
                    //outside switch we can use character everytime, checking the charactersList and choosing the index, after send a message
                }
            }
            else if(controller.getClientState() != ClientState.WAITING_FOR_YOUR_TURN){
                switch (controller.getClientState()) {
                    case REQUESTING_LOGIN:
                        cli.notifyObserver(obs -> obs.onSendLoginRequest(userInput));
                        break;

                    case CHOOSING_JOIN_CREATE:
                        if (userInput.equals("c")) {
                            controller.setClientState(ClientState.CREATING_LOBBY);
                            cli.sendNewLobbyRequest();

                        } else if (userInput.equals("j")) {
                            cli.notifyObserver(obs -> obs.onSendLobbiesRequest());
                        } else {
                            System.out.println("Incorrect input,try again!");
                            cli.onAskCreateOrJoin();
                            break;
                        }
                        break;

                    case CREATING_LOBBY:
                        try {
                            String params[] = userInput.split(" ");

                            int numOfPlayers = Integer.parseInt(params[1]);

                            if (numOfPlayers < 2 || numOfPlayers > 4) {
                                System.out.println("Invalid num of players, retry to create a lobby");
                                cli.sendNewLobbyRequest();
                                break;
                            }

                            if (!params[2].equals("true") && !params[2].equals("false")) {
                                System.out.println("Invalid mode selected, retry to create a lobby");
                                cli.sendNewLobbyRequest();
                                break;
                            }
                            boolean expertMode = Boolean.parseBoolean(params[2]);
                            cli.notifyObserver(obs -> obs.onSendNewLobbyRequest(params[0], numOfPlayers, expertMode));
                        } catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    case CHOOSING_LOBBY:
                        Lobby chosenLobby = null;
                        for (Lobby lobby : lobbiesList) {
                            if (userInput.equals(lobby.getName())) {
                                chosenLobby = lobby;
                                break;
                            }
                        }

                        if (chosenLobby != null) {
                            cli.notifyObserver(obs -> obs.onSendChooseLobby(userInput));
                        } else {
                            System.out.println("This lobby doesn't exist! Choose again!");
                        }
                        break;

                    case CHOOSING_TEAM:
                        try {
                            int chosenPlayer = -1;
                            for (String entry : availablePlayers.keySet()) {
                                if (userInput.equals(entry)) {
                                    chosenPlayer = Integer.parseInt(userInput);
                                    break;
                                }
                            }

                            if (chosenPlayer != -1) {
                                int finalInput = chosenPlayer;
                                cli.notifyObserver(obs -> obs.onSendChooseTeam(finalInput));
                            } else {
                                System.out.println("This player doesn't exist!, choose again");
                            }
                        }
                        catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }

                        break;

                    case CHOOSING_TOWER_COLOR:
                        TowerColor chosenTowerColor = null;
                        for (TowerColor c : availableTowerColors) {
                            if (userInput.equals(c.toString())) {
                                chosenTowerColor = c;
                                break;
                            }
                        }

                        if (chosenTowerColor != null) {
                            TowerColor finalTowerColor = chosenTowerColor;
                            cli.notifyObserver(obs -> obs.onSendChooseTowerColor(finalTowerColor));
                        } else {
                            System.out.println("This color isn't available!, choose again ");
                        }
                        break;

                    case CHOOSING_WIZARD:
                        Wizard chosenWizard = null;
                        for (Wizard wizard : availableWizards) {
                            if (userInput.equals(wizard.toString())) {
                                chosenWizard = wizard;
                                break;
                            }
                        }
                        if (chosenWizard != null) {
                            Wizard finalChosenWizard = chosenWizard;
                            cli.notifyObserver(obs -> obs.onSendChooseWizard(finalChosenWizard));
                        } else {
                            System.out.println("This wizard isn't available!, try to choose again ");
                        }
                        break;

                    case THROWING_ASSISTANT:
                        try {
                            int selectedAssistantId = Integer.parseInt(userInput); //why don't need try catch because if the input is incorrect,nothing is returned
                            cli.notifyObserver(obs -> obs.onSendSelectAssistant(selectedAssistantId));
                        }
                        catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    case CHOOSING_WHERE_TO_MOVE_STUDENT:
                        if (userInput.equals("d")) {
                            controller.setClientState(ClientState.MOVING_A_STUDENT_DASHBOARD);
                            cli.sendMoveStudentDashboard();

                        } else if (userInput.equals("i")) {
                            controller.setClientState(ClientState.MOVING_A_STUDENT_ISLAND);
                            cli.sendMoveStudentIsland();
                        } else {
                            System.out.println("Incorrect input,try again!");
                            cli.onAskWhereToMoveStudent();
                        }
                        break;

                    case MOVING_A_STUDENT_DASHBOARD:
                        try {
                            int parsedInputStud = Integer.parseInt(userInput); //we need try-catch because if the input is incorrect, there is an exception
                            int selectedStudentIndex = parsedInputStud - 1;
                            cli.notifyObserver(obs -> obs.onSendMoveAStudentDashboard(selectedStudentIndex));
                        }
                        catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    case MOVING_A_STUDENT_ISLAND:
                        try {
                            String paramsstud[] = userInput.split(" ");
                            int parsedInputStudIsl = Integer.parseInt(paramsstud[0]);
                            int selectedStudentIslIndex = parsedInputStudIsl-1; //-1 is an error

                            int parsedInputIsl = Integer.parseInt(paramsstud[1]); //we need try-catch because if the input is incorrect, there is an exception
                            int selectedIslandIndex = parsedInputIsl-1; //-1 is an error
                            cli.notifyObserver(obs -> obs.onSendMoveAStudentIsland(selectedStudentIslIndex,selectedIslandIndex));
                        } catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    case MOVING_MOTHER_NATURE:
                        try {
                            int parsedInputMN = Integer.parseInt(userInput); //we need try-catch because if the input is incorrect, there is an exception
                            int selectedMNPos = parsedInputMN-1;
                            cli.notifyObserver(obs -> obs.onSendMoveMotherNature(selectedMNPos));
                        }
                        catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    case CHOOSING_CLOUD:
                        try {
                            int parsedInputCloud = Integer.parseInt(userInput); //we need try-catch because if the input is incorrect, there is an exception
                            int selectedCloudIndex = parsedInputCloud-1;
                            cli.notifyObserver(obs -> obs.onSendChooseCloud(selectedCloudIndex));
                        }
                        catch (Exception e) {
                            System.out.println("Input failed, retry");
                        }
                        break;

                    default:
                        System.out.println("Error in the application"); //it should never happen
                        break;
                }
            }
            else{
                System.out.println("It's not your turn");
            }

        }
    }

    public void setLobbiesList(List<Lobby> lobbiesList) {
        this.lobbiesList = lobbiesList;
    }

    public void setAvailablePlayers(Map<String, Integer> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    public void setAvailableTowerColors(List<TowerColor> availableTowerColors) {
        this.availableTowerColors = availableTowerColors;
    }

    public void setAvailableWizards(List<Wizard> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public void setClientGameModel(ClientGameModel clientGameModel) {
        this.clientGameModel = clientGameModel;
    }
}

