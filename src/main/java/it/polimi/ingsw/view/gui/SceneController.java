
package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.scene.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;


//the folder scene is to define, now there are something not good for this project


public class SceneController{

    private static Scene activeScene;
    private static GenericSceneController activeController;
    private static String currFxml ="";


    public static String getCurrFxml() {
        return currFxml;
    }

    public static void setActiveScene(Scene activeScene) {
        SceneController.activeScene = activeScene;
    }

    public static void setActiveController(GenericSceneController activeController) {
        SceneController.activeController = activeController;
    }

    public static Scene getActiveScene() {
        return activeScene;
    }


    public static GenericSceneController getActiveController() {
        return activeController;
    }


    public static void changeRootPane(List<ViewObserver> observerList, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            ViewObservable controller = loader.getController();
            System.out.println(controller);
            controller.addAllObservers(observerList);
            activeController = (GenericSceneController) controller;
            activeScene.setRoot(root);
            currFxml = fxml;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message){
        Platform.runLater(()->{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AlertShowScene.fxml"));

            Parent parent;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            AlertSceneController alertSceneController = loader.getController();
            Scene alertScene = new Scene(parent);
            alertSceneController.setScene(alertScene);
            alertSceneController.setMessage(message);
            alertSceneController.showAlert();
        });

    }
}
