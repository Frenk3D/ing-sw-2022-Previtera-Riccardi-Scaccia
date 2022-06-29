package it.polimi.ingsw;
//Deselect always .idea and another .iml files with pull/push to avoid problems!!! (ok with .gitignore), JavaDoc used somewhere in Bag, Client, ServerApp
/**
 * This is the class that runs the server
 * Game: Eryantis
 * @author Francesco Riccardi, Nicolò Scaccia, Marco Luca Previtera
 *
 */
import it.polimi.ingsw.network.server.*;

import java.io.IOException;

public class ServerApp
{
    public static void main( String[] args ) {
        int port = 3333;
        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("--port") || args[i].equals("-p")){
                try {
                    port = Integer.parseInt(args[i+1]);
                }
                catch (Exception e){
                    System.out.println("Wrong parameters, use --port [port] or -p [port] to choose the port");
                    return;
                }
            }
        }

        try {
            new Server(port);
        } catch (Exception e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}

