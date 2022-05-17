package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerManager implements Runnable{

    ServerSocket serverSocket;
    Server server;
    int port;

    public SocketServerManager(Server server, int port){
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server created on port "+port);
        } catch (IOException e) {
            System.out.println("Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();

                client.setSoTimeout(5000);

                SocketClientManager clientManager = new SocketClientManager(server, client);
                Thread thread = new Thread(clientManager, "ss_manager" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection dropped");
            }
        }
    }
}
