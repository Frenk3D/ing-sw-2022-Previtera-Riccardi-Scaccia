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
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server(3333);
        } catch (Exception e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}

