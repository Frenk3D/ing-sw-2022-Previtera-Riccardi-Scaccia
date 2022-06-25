package it.polimi.ingsw;

import it.polimi.ingsw.view.gui.JavaFXGui;
import it.polimi.ingsw.view.gui.scene.TableSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//only for tests
public class javafxTest extends Application {

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
            stage2.setTitle("Game");
            stage2.setWidth(1280);
            stage2.setHeight(720);
            stage2.setResizable(true);
            stage2.show();

            TableSceneController controller = loader.getController();
            controller.updateGraphics(ProvaGit.generateClientGameModel());
            controller.selectFromDashboard(3,false);
        }
    }

