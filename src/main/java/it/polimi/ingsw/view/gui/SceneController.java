
package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.scene.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


//the folder scene is to define, now there are something not good for this project


/**
 * This is the class the manage the change of views in the GUI.
 */
public class SceneController{

    private static Scene activeScene;
    private static GenericSceneController activeController;
    private static String currFxml ="";
    private static Stage mainStage;

    /**
     *
     * @return the current fxml file
     */
    public static String getCurrFxml() {
        return currFxml;
    }

    /**
     * Sets the current active scene
     * @param activeScene the current scene
     */
    public static void setActiveScene(Scene activeScene) {
        SceneController.activeScene = activeScene;
    }

    /**
     * Sets the current active scene controller
     * @param activeController the current scene controller
     */
    public static void setActiveController(GenericSceneController activeController) {
        SceneController.activeController = activeController;
    }

    /**
     *
     * @return the active scene
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**
     *
     * @return the main stage
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Sets the main stage
     * @param mainStage
     */
    public static void setMainStage(Stage mainStage) {
        SceneController.mainStage = mainStage;
    }

    /**
     *
     * @return the active scene controller
     */
    public static GenericSceneController getActiveController() {
        return activeController;
    }

    /**
     * Changes the scene
     * @param observerList the list of observers
     * @param fxml the fxml file
     */
    public static void changeRootPane(List<ViewObserver> observerList, String fxml) {
        try {
            if(currFxml.equals("TableScene.fxml")){
                TableSceneController tableSceneController = (TableSceneController) activeController;
                tableSceneController.closeAllPopups();
            }

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

    /**
     * Show a string message
     * @param message the string message
     */
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
