package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.ClientState;
import it.polimi.ingsw.network.server.Lobby;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.network.client.ClientState.CHOOSING_LOBBY;

public class KeyboardManager implements Runnable{

    private ClientState state;
    private Cli cli;
    private String userInput;
    private static final String STR_INPUT_FAILED = "User input failed.";

    private List<Lobby> lobbylist = null;

    public KeyboardManager(ClientState state, Cli cli){
        this.state = state;
        this.cli = cli;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Scanner scanInput = new Scanner(System.in);
            userInput = scanInput.nextLine();
            System.out.println("login: "+ userInput+" state: "+state);

            switch (state){
                case REQUESTING_LOGIN:
                    cli.notifyObserver(obs -> obs.onSendLoginRequest(userInput));
                    break;
                case CHOOSING_JOIN_CREATE:
                    if(userInput.equals("c")){
                        state = ClientState.CREATING_LOBBY;
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
                    cli.notifyObserver(obs -> obs.onSendNewLobbyRequest(params[0],numOfPlayers,expertMode));
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
                    break;
                case CHOOSING_TOWER_COLOR:
                    break;
                case CHOOSING_WIZARD:
                    break;
            }

        }
    }

    public void setLobbylist(List<Lobby> lobbylist) {
        this.lobbylist = lobbylist;
    }
}
