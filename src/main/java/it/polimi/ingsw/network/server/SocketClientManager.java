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

    public SocketClientManager(){
        this.inputLock = new Object();
        this.outputLock = new Object();

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
            onDisconnect();
        }
    }


    private void handleClientConnection() throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                    if (message != null && message.isInitMessage()) {
                        server.onInitMessageReceived(message,this);
                    }
                    else {
                        remoteView.receive(message);
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
        }
        socket.close();
    }


    private void manageReception(){

    }

    public void sendMessage(Message message){

    }

    public boolean isConnected() {
        return connected;
    }

    public void onDisconnect(){

    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }
}
