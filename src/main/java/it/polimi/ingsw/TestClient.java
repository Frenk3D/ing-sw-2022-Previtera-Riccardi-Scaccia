package it.polimi.ingsw;

import it.polimi.ingsw.client.ReducedIsland;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

//only to test
public class TestClient {

    private String ip;
    private int port;
    private int clientId;
    private boolean active = true;

    public TestClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }



    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Message inputMessage = (Message) socketIn.readObject();
                        System.out.println("Received "+ inputMessage.getMessageType());
                        switch (inputMessage.getMessageType()){
                            case LOGIN_REPLY:
                                StringMessage loginMessage = (StringMessage)inputMessage;
                                clientId = Integer.parseInt(loginMessage.getString());
                                System.out.println("Login successful, id: "+clientId);
                                break;
                            case AVAILABLE_LOBBIES:
                                LobbyMessage lobbyMessage = (LobbyMessage) inputMessage;
                                for (Lobby lobby : lobbyMessage.getLobbiesList()){
                                    System.out.println(lobby);
                                }
                                break;
                            case PLAYER_JOIN:
                                PlayerJoinMessage joinMessage = (PlayerJoinMessage) inputMessage;
                                System.out.println("Giocatori nella lobby:");
                                for (String name : joinMessage.getPlayersList()){
                                    System.out.println(name);
                                }
                                break;
                            case AVAILABLE_TEAM_SEND:
                                break;
                            case AVAILABLE_TOWER_SEND:
                                System.out.println("Scegli un colore delle torri");
                                SyncInitMessage towerMessage = (SyncInitMessage) inputMessage;
                                for (TowerColor t : towerMessage.getAvailableTowerColors()){
                                    System.out.println(t);
                                }
                                break;
                            case AVAILABLE_WIZARD_SEND:
                                System.out.println("Scegli un wizard");
                                SyncInitMessage wizardMessage = (SyncInitMessage) inputMessage;
                                for (Wizard w : wizardMessage.getAvailableWizards()){
                                    System.out.println(w);
                                }
                                break;

                            case INIT_SEND:
                                AllGameMessage allGameMessage = (AllGameMessage) inputMessage;
                                for (ReducedIsland i : allGameMessage.getIslandsList()){
                                    System.out.println(i);
                                }
                                break;
                            case ERROR_REPLY:
                                StringMessage errorMessage = (StringMessage) inputMessage;
                                System.out.println("Errore server: "+ errorMessage.getString());
                                break;

                        }

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final ObjectOutputStream output){
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner scanIn = new Scanner(System.in);
                    while (clientId == 0) {
                        System.out.print("Enter name: ");
                        String input = scanIn.nextLine();
                        StringMessage loginMessage = new StringMessage(MessageType.LOGIN_REQUEST, 1111, true,input);
                        output.writeObject(loginMessage);
                        output.flush();
                        output.reset();
                        Thread.sleep(500);
                    }
                    while (isActive()){
                        String command = scanIn.nextLine();
                        switch (command) {
                            case "lobbylist":
                                GenericMessage printLobbies = new GenericMessage(MessageType.LOBBIES_REQUEST, clientId, true);
                                output.writeObject(printLobbies);
                                output.flush();
                                output.reset();
                                break;

                            case "newlobby":
                                System.out.print("Enter lobby name: ");
                                String newLobbyName = scanIn.nextLine();
                                System.out.print("Enter lobby players: ");
                                int lobbynum = scanIn.nextInt();
                                System.out.print("Enter lobby mode: ");
                                boolean mode = scanIn.nextBoolean();
                                Lobby newLobby = new Lobby(lobbynum,0,mode,newLobbyName);
                                NewLobbyMessage newLobbyMessage = new NewLobbyMessage(clientId, newLobby);
                                output.writeObject(newLobbyMessage);
                                output.flush();
                                output.reset();
                                break;

                            case "enterlobby":
                                System.out.print("Enter lobby name: ");
                                String lobbyName = scanIn.nextLine();
                                StringMessage enterLobbyRequest = new StringMessage(MessageType.CHOOSE_LOBBY, clientId, true, lobbyName);
                                output.writeObject(enterLobbyRequest);
                                output.flush();
                                output.reset();
                                break;

                            case "choosetower":
                                System.out.print("Enter tower color: ");
                                int towerNum = scanIn.nextInt();
                                ChooseTowerColorMessage chooseTowerColorMessage = new ChooseTowerColorMessage(clientId, TowerColor.BLACK);
                                output.writeObject(chooseTowerColorMessage);
                                output.flush();
                                output.reset();
                                break;

                            case "choosewizard":
                                System.out.print("Enter wizard: ");
                                int wizardNum = scanIn.nextInt();
                                ChooseWizardMessage chooseWizardMessage = new ChooseWizardMessage(clientId, Wizard.ASIATIC);
                                output.writeObject(chooseWizardMessage);
                                output.flush();
                                output.reset();
                                break;

                            default:
                                break;

                        }
                    }


                }catch(Exception e){
                    System.out.println("Errore scrittura");
                    e.printStackTrace();
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip,port),0);
        System.out.println("Connection established");

        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(socketOut);
            t0.join();
            t1.join();
            System.out.println("Server closed connection");

        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
