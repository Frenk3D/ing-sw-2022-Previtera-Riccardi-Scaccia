package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.client.ReducedDashboard;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class TableSceneController extends ViewObservable implements GenericSceneController{

    @FXML private StackPane island1Pane;
    @FXML private StackPane island2Pane;
    @FXML private StackPane island3Pane;
    @FXML private StackPane island4Pane;
    @FXML private StackPane island5Pane;
    @FXML private StackPane island6Pane;
    @FXML private StackPane island7Pane;
    @FXML private StackPane island8Pane;
    @FXML private StackPane island9Pane;
    @FXML private StackPane island10Pane;
    @FXML private StackPane island11Pane;
    @FXML private StackPane island12Pane;
    @FXML private HBox HboxPlayer1;
    @FXML private HBox HboxPlayer2;
    @FXML private HBox HboxPlayer3;
    @FXML private HBox HboxPlayer4;
    @FXML private StackPane cloud1Pane;
    @FXML private StackPane cloud2Pane;
    @FXML private StackPane cloud3Pane;
    @FXML private StackPane cloud4Pane;
    @FXML private HBox assistantsPane;
    @FXML private StackPane character1Pane;
    @FXML private StackPane character2Pane;
    @FXML private StackPane character3Pane;
    @FXML private Button buttonCommand1;
    @FXML private Button buttonCommand2;

    private Scene dashboardScene;

    private StackPane[] islandArray = new StackPane[12];
    private HBox[] dashboardArray = new HBox[4];
    private StackPane[] cloudArray = new StackPane[4];
    private StackPane[] characterArray = new StackPane[3];

    @FXML
    public void initialize() {
        loadGraphicsArrays(); //first load javafx references in arrays

        //set islands listener
        for(int i=0;i<islandArray.length;i++){
            StackPane island = islandArray[i];
            island.setUserData(i);
            island.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onIslandClick);
            island.setOnMouseEntered((e)->{
                island.setScaleX(1.04);
                island.setScaleY(1.04);
            });
            island.setOnMouseExited((e)->{
                island.setScaleX(1);
                island.setScaleY(1);
            });
        }

        //set dashboards listeners
        for (int i=0;i<dashboardArray.length;i++){
            StackPane dashboardPane = (StackPane) dashboardArray[i].getChildren().get(0);
            Button playerButton = (Button) dashboardPane.getChildren().get(1);
            playerButton.setUserData(i);
            playerButton.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onDashboardClick);
        }

        //set clouds listeners
        for (int i=0;i<cloudArray.length;i++){
            StackPane cloud = cloudArray[i];
            cloud.setUserData(i);
            cloud.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onCloudClick);
            cloud.setOnMouseEntered((e)->{
                cloud.setScaleX(1.04);
                cloud.setScaleY(1.04);
            });
            cloud.setOnMouseExited((e)->{
                cloud.setScaleX(1);
                cloud.setScaleY(1);
            });
        }

        //set characters listeners
        for (int i=0;i<characterArray.length;i++){
            StackPane character = characterArray[i];
            character.setUserData(i);
            character.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onCharacterClick);
            character.setOnMouseEntered((e)->{
                character.setScaleX(1.04);
                character.setScaleY(1.04);
            });
            character.setOnMouseExited((e)->{
                character.setScaleX(1);
                character.setScaleY(1);
            });
        }

        buttonCommand1.setUserData(0);
        buttonCommand2.setUserData(1);
        buttonCommand1.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onButtonCommandClick);
        buttonCommand2.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onButtonCommandClick);
    }


    private void onIslandClick(Event e){
        int islandIndex = (int)((StackPane)e.getSource()).getUserData();
        System.out.println("Click on island "+ islandIndex);
        islandArray[islandIndex].setVisible(false);
    }

    private void onCloudClick(Event e){
        int cloudIndex = (int)((StackPane)e.getSource()).getUserData();
        System.out.println("Click on cloud "+ cloudIndex);
    }

    private void onDashboardClick(Event e){
        int dashboardIndex = (int)((Button)e.getSource()).getUserData();
        System.out.println("Click on dashboard "+ dashboardIndex);
        openDashboard(null);
    }

    private void onCharacterClick(Event e){
        int characterIndex = (int)((StackPane)e.getSource()).getUserData();
        System.out.println("Click on character "+ characterIndex);
    }

    private void onButtonCommandClick(Event e){
        int buttonIndex = (int)((Button)e.getSource()).getUserData();
        System.out.println("Click on button "+ buttonIndex);
    }

    private void openDashboard(ReducedDashboard dashboard){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/DashboardScene.fxml"));

        try {
            dashboardScene = new Scene(loader.load());
            dashboardScene.setUserData(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(dashboardScene);
        stage.setTitle("Dashboard");
        stage.setWidth(999);
        stage.setHeight(434);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        DashboardSceneController dashboardController = loader.getController();
        dashboardController.loadDashboard(dashboard);
        stage.showAndWait();
    }

    public void updateGraphics(ClientGameModel model){

    }

    private void loadGraphicsArrays(){
        islandArray[0]=island1Pane;
        islandArray[1]=island2Pane;
        islandArray[2]=island3Pane;
        islandArray[3]=island4Pane;
        islandArray[4]=island5Pane;
        islandArray[5]=island6Pane;
        islandArray[6]=island7Pane;
        islandArray[7]=island8Pane;
        islandArray[8]=island9Pane;
        islandArray[9]=island10Pane;
        islandArray[10]=island11Pane;
        islandArray[11]=island12Pane;
        dashboardArray[0]=HboxPlayer1;
        dashboardArray[1]=HboxPlayer2;
        dashboardArray[2]=HboxPlayer3;
        dashboardArray[3]=HboxPlayer4;
        cloudArray[0]=cloud1Pane;
        cloudArray[1]=cloud2Pane;
        cloudArray[2]=cloud3Pane;
        cloudArray[3]=cloud4Pane;
        characterArray[0]=character1Pane;
        characterArray[1]=character2Pane;
        characterArray[2]=character3Pane;
    }

}
