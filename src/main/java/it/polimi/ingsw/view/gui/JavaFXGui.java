package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import it.polimi.ingsw.view.gui.scene.MenuSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX class which starts the main stage and scene.
 we have to choose between swing or javafx*/
public class JavaFXGui extends Application {
    Stage rootLayout;
    Scene firstScene;

    @Override
    public void start(Stage stage) {
        Gui view = new Gui();
        ClientController clientController = new ClientController();

        clientController.getClientGameModel().addObserver(view);
        view.addObserver(clientController);
        //view.init();

        // Load root layout from fxml file.
        if(firstScene == null) {
            FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/LoginScene.fxml"));
            try {
                firstScene = new Scene(loader.load(), 364.0, 606.0);
            } catch (IOException e) {
                //Client.LOGGER.severe(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
            stage.setScene(firstScene);
            LoginSceneController controller = loader.getController();
            controller.addObserver(clientController);

            // Show the scene containing the root layout.
            stage.setWidth(606.0);
            stage.setHeight(364.0);
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setTitle("Eriantys Board Game");
            stage.show();
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
