package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.client.ReducedDashboard;
import it.polimi.ingsw.view.gui.scene.DashboardSceneController;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
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


    @Override
    public void start(Stage stage) {

        Gui view = new Gui();
        ClientController clientController = new ClientController();
        view.addObserver(clientController);
        clientController.getClientGameModel().addObserver(view);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent rootLayout = null;
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        LoginSceneController controller = loader.getController();
        controller.addObserver(clientController);

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        SceneController.setActiveScene(scene);
        SceneController.setActiveController(controller);
        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(false);
        //stage.setMaximized(true);
        //stage.setFullScreen(true);
        //stage.setFullScreenExitHint("");
        //stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Eriantys Board Game");
        stage.show();



    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
