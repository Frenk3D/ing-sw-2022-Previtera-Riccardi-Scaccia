package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientManager implements Runnable{
    private Socket socket;
    private RemoteView remoteView;
    private Server server;
    private boolean connected;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final Object inputLock;
    private final Object outputLock;

    public SocketClientManager(Server server, Socket socket){
        this.inputLock = new Object();
        this.outputLock = new Object();
        this.connected = true;
        this.server = server;
        this.socket = socket;

        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {

        }
    }


    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            disconnect();
        }
    }


    private void handleClientConnection() throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();
                    manageReception(message);
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("SocketClientManager: error in reception");
        }
        socket.close();
    }


    private void manageReception(Message message){
        if (message != null) {
            if(message.isInitMessage()) {
                server.onInitMessageReceived(message, this);
            }
            else if(!message.isInitMessage() && remoteView!=null){
                remoteView.sendToController(message);
            }
        }
    }

    public void sendMessage(Message message){
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.flush();
                output.reset();
            }
        } catch (IOException e) {
            disconnect();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void disconnect(){
        if (connected) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Errore disconnect");
                e.printStackTrace();
            }
            connected = false;
            Thread.currentThread().interrupt();

            server.onDisconnect(this);
        }
    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }
}
