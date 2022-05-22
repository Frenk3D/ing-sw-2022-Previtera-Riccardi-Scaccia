package it.polimi.ingsw.network.client;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.client.*;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.StringMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @see Runnable
 */

public class ClientSocket  {
    private String ip;
    private int port;
    private Socket socket;
    private final ObjectOutputStream outputStm;
    private final ObjectInputStream inputStm;
    private final ExecutorService readExecutionQueue;
    private int clientId;
    private ClientController clientController;


    public ClientSocket(String ip, int port, ClientController clientController) throws IOException{
        this.ip = ip;
        this.port = port;
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(ip, port));
        this.outputStm = new ObjectOutputStream(socket.getOutputStream());
        this.inputStm = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        clientId = 8888;
        this.clientController = clientController;
    }

    private boolean active = true;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }



    public void readMessage() {
        readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) inputStm.readObject();
                    System.out.println("Received: " + message);
                } catch (IOException | ClassNotFoundException e) {
                    message = new StringMessage(MessageType.ERROR_REPLY, clientId, "Connection lost with the server" );
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
                notifyObserver(message);
            }
        });
    }


    public void sendMessage(Message message) {
        try {
            outputStm.writeObject(message);
            outputStm.reset();
        } catch (IOException e) {
            disconnect();
            notifyObserver(new StringMessage(MessageType.ERROR_REPLY,clientId, "Could not send message."));
        }
    }

    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (IOException e) {
            notifyObserver(new StringMessage(MessageType.ERROR_REPLY, clientId,"Could not disconnect."));
        }
    }

    /*public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }*/

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}


