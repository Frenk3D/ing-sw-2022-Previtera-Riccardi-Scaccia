package it.polimi.ingsw.view.cli;

import com.sun.security.ntlm.Client;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.network.client.ClientState;
import it.polimi.ingsw.network.server.Lobby;

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
            scanInput.reset();
            userInput = scanInput.nextLine(); //whenever the player presses enter this line gets executed
            System.out.println("login: " + userInput + " state: " + controller.getClientState()); //only for debug
            if (userInput.equals("use_character") && controller.getClientState() != ClientState.WAITING_FOR_YOUR_TURN) {
                if(clientGameModel.getCharactersList().isEmpty() || clientGameModel.getCharactersList()==null){
                    System.out.println("Characters not available");
                }
                else{
                    System.out.println("Choose a character with his parameters");
                    //manage the characters now with the parameters
                    //outside switch we can use character everytime, checking the charactersList and choosing the index
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
                        int selectedAssistantId = -1;
                        int parsedInput = Integer.parseInt(userInput); //why don't need try catch because if the input is incorrect,nothing is returned
                        for(ReducedAssistant a : clientGameModel.getAssistantList()){
                            if(parsedInput == a.getId()){
                                selectedAssistantId = parsedInput;
                            }
                        }
                        if(selectedAssistantId == -1)
                            System.out.println("The selected assistant isn't available,try again!");
                        else{
                            int finalSelectedAssistantId = selectedAssistantId;
                            cli.notifyObserver(obs -> obs.onSendSelectAssistant(finalSelectedAssistantId));
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

