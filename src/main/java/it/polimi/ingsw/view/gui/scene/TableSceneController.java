package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.client.ClientGameModel;
import it.polimi.ingsw.model.client.ReducedAssistant;
import it.polimi.ingsw.model.client.ReducedDashboard;
import it.polimi.ingsw.model.client.ReducedIsland;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.cli.ColorCli;
import it.polimi.ingsw.view.gui.GuiState;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private Stage stage;
    private Scene dashboardScene;
    private GuiState guiState = GuiState.LOCKED;

    private StackPane[] islandArray = new StackPane[12];
    private HBox[] dashboardArray = new HBox[4];
    private StackPane[] cloudArray = new StackPane[4];
    private StackPane[] characterArray = new StackPane[3];
    private int[] islandToId = new int[12];

    private ClientGameModel gameModel;
    private List<Integer> dashboardEntranceSelection;
    private List<PawnColor> dashboardHallSelection;

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

    public void requestedChooseAssistant(){
        guiState = GuiState.WAITING_FOR_ASSISTANT_CLICK;
    }
    public void requestedMoveStudent(){
        guiState = GuiState.WAITING_FOR_MOVE_OR;
        buttonCommand1.setVisible(true);
        buttonCommand2.setVisible(true);
    }

    public void requestedMoveMotherNature(){
        guiState = GuiState.WAITING_FOR_ISLAND_MN;
    }

    public void requestedChooseCloud(){
        guiState = GuiState.WAITING_FOR_CLOUD;
    }

    public void requestedCharacterParameters(int id){

    }

    private void onIslandClick(Event e){
        int islandIndex = (int)((StackPane)e.getSource()).getUserData();
        if(guiState==GuiState.WAITING_FOR_ISLAND_MOVE){
            guiState=GuiState.LOCKED;
            System.out.println("Click on island "+ islandToId[islandIndex]);
            new Thread(() -> notifyObserver((obs -> obs.onSendMoveAStudentIsland(dashboardEntranceSelection.get(0),islandToId[islandIndex])))).start();
        }
        else if(guiState==GuiState.WAITING_FOR_ISLAND_MN){
            System.out.println("Click on island "+ islandToId[islandIndex]);
            new Thread(() -> notifyObserver((obs -> obs.onSendMoveMotherNature(islandToId[islandIndex])))).start();
        }

    }

    private void onAssistantClick(Event e){
        int assistantIndex = (int)((ImageView)e.getSource()).getUserData();
        if(guiState==GuiState.WAITING_FOR_ASSISTANT_CLICK){
            System.out.println("Click on assistant "+ assistantIndex);
            new Thread(() -> notifyObserver((obs -> obs.onSendSelectAssistant(assistantIndex)))).start();
        }

    }

    private void onCloudClick(Event e){
        int cloudIndex = (int)((StackPane)e.getSource()).getUserData();
        if(guiState==GuiState.WAITING_FOR_CLOUD){
            System.out.println("Click on cloud "+ cloudIndex);
            new Thread(() -> notifyObserver((obs -> obs.onSendChooseCloud(cloudIndex)))).start();
        }

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
        buttonCommand1.setVisible(false);
        buttonCommand2.setVisible(false);
        if(guiState==GuiState.WAITING_FOR_MOVE_OR) {
            guiState=GuiState.LOCKED;
            int buttonIndex = (int) ((Button) e.getSource()).getUserData();
            System.out.println("Click on button " + buttonIndex);
            if(buttonIndex==0){ //move student island
                selectFromDashboard(1,0,false);
                guiState=GuiState.WAITING_FOR_ISLAND_MOVE;
            }
            else { //move student dashboard
                selectFromDashboard(1,0,false);
                new Thread(() -> notifyObserver((obs -> obs.onSendMoveAStudentDashboard(dashboardEntranceSelection.get(0))))).start();
            }
        }
    }

    private void openDashboard(ReducedDashboard dashboard){
        stage = new Stage();
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

    private void selectFromDashboard(int entranceChoice, int hallChoice, boolean isVariable){
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/DashboardScene.fxml"));

        try {
            dashboardScene = new Scene(loader.load());
            dashboardScene.setUserData(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(dashboardScene);
        stage.setTitle("Select from dashboard");
        stage.setWidth(999);
        stage.setHeight(434);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        DashboardSceneController dashboardController = loader.getController();
        dashboardController.selectionMode(entranceChoice,hallChoice);
        dashboardController.loadDashboard(gameModel.findPlayerById(gameModel.getMyPlayerId()).getDashboard());
        stage.showAndWait();
        dashboardEntranceSelection=dashboardController.getEntranceChoiceSelection();
        dashboardHallSelection=dashboardController.getHallChoiceSelection();
    }

    public void updateGraphics(ClientGameModel model){
        gameModel = model;
        loadAssistants(model.getAssistantList());
        loadIslands(model.getIslandList(), model.getMotherNaturePos());
    }

    private void loadIslands(List<ReducedIsland> islandList, int motherNature) {
        int i=0,index=-1;
        boolean motherNatureHere;
        for (int j=0;j<12;j++){
            islandToId[j]=-1;
        }
        for (ReducedIsland island : islandList){
            if(motherNature == i) {
                motherNatureHere =true;
            }
            else {
                motherNatureHere=false;
            }
            index += island.getWeight();
            StackPane islandPane = islandArray[index];
            if(islandPane.getChildren().size()!=1){
                islandPane.getChildren().remove(1);
            }
            islandToId[index]=i;
            loadIsland(islandPane,island,motherNatureHere);
            i++;
        }
        for (int j=0;j<12;j++){
            if(islandToId[j]==-1){
                islandArray[j].setVisible(false);
            }
        }
    }

    private void loadIsland(StackPane islandPane, ReducedIsland island, boolean motherNature){
        AnchorPane anchorPane = new AnchorPane();

        //preload colors
        Map<PawnColor,Integer> colorsMap = new HashMap<>();
        for (PawnColor p : island.getStudentsList()){
            Integer existingNum = colorsMap.get(p);
            if(existingNum == null){
                colorsMap.put(p,1);
            }
            else {
                colorsMap.put(p,++existingNum);
            }
        }

        //draw students
        int vPos = 18;
        for (PawnColor p : PawnColor.values()) {
            if (colorsMap.get(p) != null) {
                ImageView pawnImageView = getPawnImage(p);
                pawnImageView.setLayoutX(40);
                pawnImageView.setLayoutY(vPos);
                anchorPane.getChildren().add(pawnImageView);

                Label label = new Label(""+colorsMap.get(p));
                label.setFont(Font.font("System", FontWeight.NORMAL,13));
                label.setTextFill(Color.WHITE);
                label.setLayoutX(62);
                label.setLayoutY(vPos);
                anchorPane.getChildren().add(label);
                vPos+=20;
            }
        }

        //draw mother nature
        if(motherNature) {
            ImageView mnImageView = new ImageView();
            mnImageView.setImage(new Image(getClass().getResourceAsStream("/images/mother_nature.png")));
            mnImageView.setFitWidth(61);
            mnImageView.setFitHeight(61);
            mnImageView.setLayoutX(72);
            mnImageView.setLayoutY(-3);
            anchorPane.getChildren().add(mnImageView);
        }


        //draw tower
        if (island.getTowerColor()!=null){
            ImageView towerImageView = getTowerImage(island.getTowerColor());
            towerImageView.setLayoutX(76);
            towerImageView.setLayoutY(60);
            anchorPane.getChildren().add(towerImageView);

            Label towerLabel = new Label(""+island.getWeight());
            towerLabel.setFont(Font.font("System", FontWeight.BOLD,17));
            towerLabel.setTextFill(Color.WHITE);
            towerLabel.setLayoutX(117);
            towerLabel.setLayoutY(71);
            anchorPane.getChildren().add(towerLabel);
        }

        //draw forbid cards
        if(island.getForbidCards() != 0){
            ImageView forbidCardImageView = new ImageView();
            forbidCardImageView.setImage(new Image(getClass().getResourceAsStream("/images/deny_icon.png")));
            forbidCardImageView.setFitWidth(40);
            forbidCardImageView.setFitHeight(40);
            forbidCardImageView.setLayoutX(0);
            forbidCardImageView.setLayoutY(60);
            anchorPane.getChildren().add(forbidCardImageView);

            Label forbidLabel = new Label(""+island.getForbidCards());
            forbidLabel.setFont(Font.font("System", FontWeight.BOLD,15));
            forbidLabel.setTextFill(Color.WHITE);
            forbidLabel.setLayoutX(16);
            forbidLabel.setLayoutY(40);
            anchorPane.getChildren().add(forbidLabel);
        }

        islandPane.getChildren().add(anchorPane);
    }

    private void loadAssistants(List<ReducedAssistant> assistantList){
        assistantsPane.getChildren().clear();
        for (ReducedAssistant assistant : assistantList){
            ImageView assistantImageView = getAssistantImage(assistant.getId());
            assistantsPane.getChildren().add(assistantImageView);
            assistantImageView.setUserData(assistant.getId());
            assistantImageView.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onAssistantClick);
            assistantImageView.setOnMouseEntered((e)->{
                assistantImageView.setScaleX(1.04);
                assistantImageView.setScaleY(1.04);
            });
            assistantImageView.setOnMouseExited((e)->{
                assistantImageView.setScaleX(1);
                assistantImageView.setScaleY(1);
            });
        }

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

    public void closeAllPopups(){
        if(stage!= null && stage.isShowing()){
            stage.close();
        }
    }

    private ImageView getPawnImage(PawnColor color){
        ImageView imageView = new ImageView();
        String path = "/images/";
        switch (color){
            case RED:
                path+="redStudent3D.png";
                break;
            case BLUE:
                path+="blueStudent3D.png";
                break;
            case YELLOW:
                path+="yellowStudent3D.png";
                break;
            case PINK:
                path+="pinkStudent3D.png";
                break;
            case GREEN:
                path+="greenStudent3D.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }

    private ImageView getTowerImage(TowerColor color){
        ImageView imageView = new ImageView();
        String path = "/images/";
        switch (color){
            case BLACK:
                path+="black_tower.png";
                break;
            case WHITE:
                path+="white_tower.png";
                break;
            case GRAY:
                path+="gray_tower.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }

    private ImageView getAssistantImage(int id){
        ImageView imageView = new ImageView();
        String path = "/images/ass"+id+".png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(74);
        imageView.setFitHeight(110);
        return imageView;
    }

}
