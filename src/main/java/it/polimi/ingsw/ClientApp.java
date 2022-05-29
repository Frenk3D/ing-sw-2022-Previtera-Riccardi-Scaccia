package it.polimi.ingsw;


import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;
//import javafx.application.Application;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;
import java.util.logging.Level;

/**
 * The class used to start the game client-side.
 */

// It is used for both the {@link CLI} and the {@link GUI}
public class ClientApp {

    private static final String CLI_ARGUMENT = "-cli";
    private static final String HELP_ARGUMENT = "-help";

    public static void main(String[] args){
        //THIS IS ONLY FOR THE TESTCLIENT
        /*
        TestClient client = new TestClient("127.0.0.1", 3333);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }


         */
        //THIS IS THE REAL CLIENTAPP
        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {
            Cli view = new Cli();
            ClientController clientController = new ClientController();
            clientController.getClientGameModel().addObserver(view);
            view.addObserver(clientController);
            view.init();
        } else {
           // Application.launch(JavaFXGui.class);
        }





//------------------------------------------------------------------------------------------------------------




            /*ClientSocket clientSocket = new ClientSocket("127.0.0.1", 12345);
            try{
                clientSocket.run();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }*/





        /* if (args.length == 0) Application.launch(GUI.class, args);
        else if(args.length > 1) System.out.println("Too many arguments, insert " + HELP_ARGUMENT + " to see the available graphical interface options.");
        else{
            if(CLI_ARGUMENT.equals(args[0])) CLI.main(args);
            else if(HELP_ARGUMENT.equals(args[0])) System.out.println("Insert " + CLI_ARGUMENT + " to start the game in command line interface mode, otherwise don't insert anything to start the GUI.");
            else System.out.println("ReorganizeDepotsCommand not found, insert " + HELP_ARGUMENT + " to see the available graphical interface options."); */
        }
    }


