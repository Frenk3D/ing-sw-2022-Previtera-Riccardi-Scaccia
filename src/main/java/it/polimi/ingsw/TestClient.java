package it.polimi.ingsw;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
                                break;
                            case AVAILABLE_LOBBIES:
                                LobbyMessage lobbyMessage = (LobbyMessage) inputMessage;
                                for (Lobby lobby : lobbyMessage.getLobbiesList()){
                                    System.out.println(lobby);
                                }
                        }

                    }
                } catch (Exception e){
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
                    StringMessage loginMessage = new StringMessage(MessageType.LOGIN_REQUEST,9999,"Marco"+ Math.random());
                    output.writeObject(loginMessage);
                    output.flush();
                    output.reset();
                    System.out.println("Login message sent!");

                    Thread.sleep(500);
                    GenericMessage printLobbies = new GenericMessage(MessageType.LOBBY_REQUEST, clientId, true);
                    output.writeObject(printLobbies);
                    output.flush();
                    output.reset();

                    Thread.sleep(500);
                    StringMessage enterLobbyRequest = new StringMessage(MessageType.CHOOSE_LOBBY,clientId,"test");
                    output.writeObject(enterLobbyRequest);
                    output.flush();
                    output.reset();

                }catch(Exception e){
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
        socket.connect(new InetSocketAddress(ip,port),20000);
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
