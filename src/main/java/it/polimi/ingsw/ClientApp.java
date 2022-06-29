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

    public static void main(String[] args) {
        boolean cliParam = false; // default value

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }

        if (cliParam) { //launch the cli
            ClientController clientController = new ClientController();
            Cli view = new Cli(clientController);
            clientController.getClientGameModel().addObserver(view);
            view.addObserver(clientController);
            view.init();
        } else { //launch the gui
            Application.launch(JavaFXGui.class);
        }


    }
}