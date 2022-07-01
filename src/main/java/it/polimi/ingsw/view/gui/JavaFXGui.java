package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controllers.ClientController;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 * Main JavaFX class which starts the main stage and scene.
 * we have to choose between swing or javafx
 * It extends {@link Application}
 */
public class JavaFXGui extends Application {

    /**
     * Starts the JavaFXGui instance
     *
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {

        ClientController clientController = new ClientController();
        Gui view = new Gui(clientController);

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
        SceneController.setMainStage(stage);
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.setTitle("Eriantys Board Game");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mother_nature.png")));
        stage.show();
    }

    /**
     * Stops the ongoing instance
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
