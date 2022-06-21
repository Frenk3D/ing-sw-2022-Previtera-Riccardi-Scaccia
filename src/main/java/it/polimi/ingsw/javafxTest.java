package it.polimi.ingsw;
import javax.swing.*;

import it.polimi.ingsw.view.gui.JavaFXGui;
import it.polimi.ingsw.view.gui.scene.DashboardSceneController;
import javafx.application.Application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

//only for tests
public class javafxTest extends Application {

    Stage rootLayout;
    Scene firstScene;
    Scene secondScene;

        public void start(Stage stage) {
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/TableScene.fxml"));
            try {
                secondScene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage2.setScene(secondScene);
            stage2.setTitle("Dashboard");
            stage2.setWidth(1200);
            stage2.setHeight(650);
            stage2.setResizable(true);
            stage2.show();
        }
    }

