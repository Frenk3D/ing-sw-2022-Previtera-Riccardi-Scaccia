package it.polimi.ingsw;


import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;
//import javafx.application.Application;

import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.application.Application;

/**
 * The class used to start the game client-side.
 * It is used for both the {@link Cli} and the {@link Gui}
 */

// It is used for both the {@link CLI} and the {@link GUI}
public class ClientApp {

    private static final String CLI_ARGUMENT = "--cli";
    private static final String HELP_ARGUMENT = "-help";

    public static void main(String[] args) {
        //THIS IS THE REAL CLIENT-APP
        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) {

            ClientController clientController = new ClientController();

            Cli view = new Cli(clientController);


            clientController.getClientGameModel().addObserver(view);
            view.addObserver(clientController);
            view.init();
        } else {
            Application.launch(JavaFXGui.class);
        }


    }
}


//------------------------------------------------------------------------------------------------------------

//THIS IS ONLY FOR THE TESTCLIENT
        /*
        TestClient client = new TestClient("127.0.0.1", 3333);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }


         */




