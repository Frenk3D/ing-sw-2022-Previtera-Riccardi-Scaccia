package it.polimi.ingsw.network.client;
import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.network.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *This class is used to implement and manage the client side socket
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

    /**
     * Constructor that initializes the socket and creates the socket's output stream,input stream and the read execution queue
     * @param ip the client ip
     * @param port the client port
     * @param clientController the client controller
     * @throws IOException
     */
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

   /* public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    } */


    /**
     * This method handles the reading of received messages through the input stream
     * If an IOException or a ClassNotFoundException are detected the socket disconnects
     */
    public void readMessage() {
        readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) inputStm.readObject();
                    System.out.println("Received: " + message.getMessageType()); //only for debug
                    clientController.onMessageReceived(message);
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                    disconnect();
                }
            }
        });
    }

    /**
     * This method handles the sending of messages through the socket's output stream
     * If an IOException is detected the socket is disconnected
     * @param message the message to be sent
     */
    public synchronized void sendMessage(Message message) {
        try {
            System.out.println("--SENT MESSAGE: "+message.getMessageType());
            outputStm.writeObject(message);
            outputStm.flush();
            outputStm.reset();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    //it is when THIS client disconnect, or there are others critical problems...

    /**
     * This method handles the socket disconnection by closing it
     */
    public void disconnect() {
        readExecutionQueue.shutdownNow();
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Could not disconnect");
            e.printStackTrace();
        }
        clientController.onSocketDisconnect();
    }

    /**
     *
     * @return the client's id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the client id
     * @param clientId the client id
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}


/*
____________________________________________________Old version____________________________________________________

    public void run() throws IOException {
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


