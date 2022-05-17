package it.polimi.ingsw;


/* import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import javafx.application.Application; */

import it.polimi.ingsw.network.client.ClientSocket;

import java.io.IOException;

/**
 * The class used to start the game client-side.
 */

// It is used for both the {@link CLI} and the {@link GUI}
public class ClientApp {

    private static final String CLI_ARGUMENT = "-cli";
    private static final String HELP_ARGUMENT = "-help";

    public static void main(String[] args){

            ClientSocket clientSocket = new ClientSocket("127.0.0.1", 12345);
            try{
                clientSocket.run();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }





        /* if (args.length == 0) Application.launch(GUI.class, args);
        else if(args.length > 1) System.out.println("Too many arguments, insert " + HELP_ARGUMENT + " to see the available graphical interface options.");
        else{
            if(CLI_ARGUMENT.equals(args[0])) CLI.main(args);
            else if(HELP_ARGUMENT.equals(args[0])) System.out.println("Insert " + CLI_ARGUMENT + " to start the game in command line interface mode, otherwise don't insert anything to start the GUI.");
            else System.out.println("ReorganizeDepotsCommand not found, insert " + HELP_ARGUMENT + " to see the available graphical interface options."); */
        }
    }


