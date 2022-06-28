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
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.network.server.Server.SERVERID;

/**
 * This class manages each client socket,it implements {@link Runnable}
 */
public class SocketClientManager implements Runnable{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Socket socket;
    private RemoteView remoteView;
    private Server server;
    private boolean connected;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final Object inputLock;
    private final Object outputLock;

    /**
     * Constructor
     * @param server the server
     * @param socket the socket
     *input and output objects are also initialized
     *it also catches an IOException
     */
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

    /**
     * This method handles each new client connection
     * @throws IOException
     * if an exception is the detected the client disconnects
     */
    private void handleClientConnection() throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();
                    manageReception(message);
                }
            }
        } catch (ClassCastException | ClassNotFoundException | NullPointerException e) {
            logger.log(Level.SEVERE,"error in reception, closing socket");
            e.printStackTrace();
        }
        disconnect();
    }

    /**
     * This method manages messages reception
     * @param message the message received
     */
    private void manageReception(Message message){
        if (message != null && server.checkIdSocket(message,this)) {
            if(message.isInitMessage()) {
                server.onInitMessageReceived(message, this);
            }
            else if(!message.isInitMessage() && remoteView!=null){
                remoteView.sendToController(message);
                server.removeFinishedController(); //check after every move if the match is ended and we have to close the controller
            }
        }
        else{
            sendMessage(new StringMessage(MessageType.ERROR_REPLY, SERVERID, true, "The player id is not valid"));
        }
    }

    /**
     * This method sends a message
     * @param message the message to be sent
     */
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

    /**
     * This method handles client's disconnection
     */
    public void disconnect(){
        if (connected) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                    logger.log(Level.SEVERE,"the client disconnected");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE,"error in disconnect");
                e.printStackTrace();
            }
            connected = false;
            server.onDisconnect(this);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sets a remote view
     * @param remoteView the remote view
     */
    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }
}
