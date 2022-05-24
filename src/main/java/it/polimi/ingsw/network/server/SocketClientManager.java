package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.StringMessage;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static it.polimi.ingsw.network.server.Server.SERVERID;

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
        } catch (ClassCastException | ClassNotFoundException | NullPointerException e) {
            System.out.println("SocketClientManager: error in reception, closing socket");
            e.printStackTrace();
        }
        disconnect();
    }


    private void manageReception(Message message){
        if (message != null && server.checkIdSocket(message,this)) {
            if(message.isInitMessage()) {
                server.onInitMessageReceived(message, this);
            }
            else if(!message.isInitMessage() && remoteView!=null){
                remoteView.sendToController(message);
            }
        }
        else{
            sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true, "The player id is not valid"));
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
            e.printStackTrace();
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
                    System.out.println("Socket: Client disconnected");
                }
            } catch (IOException e) {
                System.out.println("Error disconnect");
                e.printStackTrace();
            }
            connected = false;
            server.onDisconnect(this);
            Thread.currentThread().interrupt();
        }
    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }
}
