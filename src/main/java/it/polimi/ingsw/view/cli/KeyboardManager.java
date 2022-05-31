package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.client.ClientState;
import it.polimi.ingsw.network.server.Lobby;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static it.polimi.ingsw.network.client.ClientState.CHOOSING_LOBBY;

public class KeyboardManager implements Runnable{
    private ClientController controller; //only to check the state
    private Cli cli;
    private String userInput;
    private static final String STR_INPUT_FAILED = "User input failed.";

    private List<Lobby> lobbylist = null;
    private Map<String, Integer> availablePlayers= null;
    private List<TowerColor> availableTowerColors = null;
    private List<Wizard> availableWizards = null;

    public KeyboardManager(ClientController controller, Cli cli){
        this.controller = controller;
        this.cli = cli;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {  //everytime active to receive input
            Scanner scanInput = new Scanner(System.in);
            userInput = scanInput.nextLine();
            System.out.println("login: "+ userInput+" state: "+controller.getClientState());

            switch (controller.getClientState()){
                case REQUESTING_LOGIN:
                    cli.notifyObserver(obs -> obs.onSendLoginRequest(userInput));
                    break;
                case CHOOSING_JOIN_CREATE:
                    if(userInput.equals("c")){
                        controller.setClientState(ClientState.CREATING_LOBBY);
                        cli.sendNewLobbyRequest();

                    }
                    else if(userInput.equals("j")){
                        cli.notifyObserver(obs -> obs.onSendLobbiesRequest());
                    }
                    else{
                        System.out.println("Incorrect input,try again!");
                        cli.onAskCreateOrJoin();
                        break;
                    }
                    break;
                case CREATING_LOBBY:
                    try {
                        String params[] = userInput.split(" ");

                    int numOfPlayers = Integer.parseInt(params[1]);

                    if(numOfPlayers<2 || numOfPlayers>4){
                        System.out.println("Invalid num of players, retry to create a lobby");
                        cli.sendNewLobbyRequest();
                        break;
                    }

                    if(!params[2].equals("true") && !params[2].equals("false") ){
                        System.out.println("Invalid mode selected, retry to create a lobby");
                        cli.sendNewLobbyRequest();
                        break;
                    }

                    boolean expertMode = Boolean.parseBoolean(params[2]);
                    cli.notifyObserver(obs -> obs.onSendNewLobbyRequest(params[0],numOfPlayers,expertMode)); }
                    catch (Exception e){
                        System.out.println("Input failed, retry");
                    }
                    break;
                case CHOOSING_LOBBY:
                    for(Lobby lobby : lobbylist){
                        if(userInput.equals(lobby.getName())){
                            cli.notifyObserver(obs -> obs.onSendChooseLobby(userInput));
                            break;
                        }
                    }
                    System.out.println("This lobby doesn't exist! Choose again!");

                    break;
                case CHOOSING_TEAM:
                    for(Map.Entry<String,Integer> entry : availablePlayers.entrySet()){
                        if(userInput.equals(entry.getValue())){
                            int finalInput = Integer.parseInt(userInput);
                            cli.notifyObserver(obs -> obs.onSendChooseTeam(finalInput));
                            break;
                        }
                    }
                    System.out.println("This player doesn't exist!, choose again");
                    //cli.onSendChooseTeam(availablePlayers);
                    break;
                case CHOOSING_TOWER_COLOR:
                    for(TowerColor c : availableTowerColors){
                        if(userInput.equals(c.toString())){
                            cli.notifyObserver(obs -> obs.onSendChooseTowerColor(c));
                            break;
                        }
                    }
                    System.out.println("This color isn't available!, choose again ");
                    //onSendChooseTowerColor(availableTowerColors);
                    break;
                case CHOOSING_WIZARD:
                    for(Wizard wizard : availableWizards){
                        if(userInput.equals(wizard.toString())){
                            cli.notifyObserver(obs -> obs.onSendChooseWizard(wizard));
                            break;
                        }
                    }
                    System.out.println("This wizard isn't available!, try to choose again ");
                    //onSendChooseWizard(availableWizards);
                    break;
            }

        }
    }

    public void setLobbylist(List<Lobby> lobbylist) {
        this.lobbylist = lobbylist;
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
}
