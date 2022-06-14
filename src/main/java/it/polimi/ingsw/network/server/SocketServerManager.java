package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerManager implements Runnable{

    private final Logger logger = Logger.getLogger(getClass().getName());
    private ServerSocket serverSocket;
    private Server server;
    private int port;

    public SocketServerManager(Server server, int port){
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            logger.log(Level.INFO,"Server created on port "+port);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                SocketClientManager clientManager = new SocketClientManager(server, client);
                Thread thread = new Thread(clientManager, "ss_manager" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Connection dropped");
            }
        }
    }
}
