package it.polimi.ingsw;
import javax.swing.*;
import javafx.application.Application;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

    public class javafxTest extends Application {
        public void start(Stage stage) {
            Circle circ = new Circle(400, 350, 105);
            Group root = new Group(circ);
            Scene scene = new Scene(root, 800, 700);
            //circ.setFill();
            scene.setFill(Color.rgb(43,95,86));
            circ.setFill(Color.rgb(34,23,78));

            stage.setTitle("My JavaFX Application");
            stage.setScene(scene);
            stage.show();

        }
    }

