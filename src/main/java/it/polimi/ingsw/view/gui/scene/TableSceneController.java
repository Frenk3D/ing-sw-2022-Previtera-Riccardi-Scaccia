package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.observer.ViewObservable;
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
    @FXML private Label tableInfoLabel;

    private Stage stage;
    private Scene dashboardScene;
    private Scene characterScene;
    private CharacterSceneController currCharacterController;
    private DashboardSceneController currDashboardController;
    private GuiState guiState = GuiState.LOCKED;

    private StackPane[] islandArray = new StackPane[12];
    private HBox[] dashboardArray = new HBox[4];
    private StackPane[] cloudArray = new StackPane[4];
    private StackPane[] characterArray = new StackPane[3];
    private int[] islandToId = new int[12];

    private ClientGameModel gameModel;
    private List<Integer> dashboardEntranceSelection;

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
        tableInfoLabel.setText("Choose an assistant...");
    }

    public void requestedMoveStudent(){
        guiState = GuiState.WAITING_FOR_MOVE_OR;
        buttonCommand1.setVisible(true);
        buttonCommand2.setVisible(true);
        tableInfoLabel.setText("Choose if you want to move a student on the dashboard or on an island...");
    }

    public void requestedMoveMotherNature(){
        guiState = GuiState.WAITING_FOR_ISLAND_MN;
        tableInfoLabel.setText("Choose where to move mother nature...");
    }

    public void requestedChooseCloud(){
        guiState = GuiState.WAITING_FOR_CLOUD;
        tableInfoLabel.setText("Choose a cloud...");
    }

    public void manageGameInfo(String text){
        tableInfoLabel.setText(text);
        guiState = GuiState.LOCKED;
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
        int playerIndex = (int)((Button)e.getSource()).getUserData();
        System.out.println("Click on dashboard "+ playerIndex);
        openDashboard(gameModel.getPlayersList().get(playerIndex));
    }

    private void onCharacterClick(Event e){
        int characterIndex = (int)((StackPane)e.getSource()).getUserData();
        openCharacter(gameModel.getCharactersList().get(characterIndex));
        System.out.println("Click on character "+ characterIndex);
    }

    private void onButtonCommandClick(Event e){
        buttonCommand1.setVisible(false);
        buttonCommand2.setVisible(false);
        if(guiState==GuiState.WAITING_FOR_MOVE_OR) {
            guiState=GuiState.LOCKED;
            int buttonIndex = (int) ((Button) e.getSource()).getUserData();
            System.out.println("Click on button " + buttonIndex);
            selectFromDashboard(1,false);
            if(dashboardEntranceSelection.size()==0){ //the dashboard was closed without selection
                requestedMoveStudent();
                return;
            }

            if(buttonIndex==0){ //move student island
                guiState=GuiState.WAITING_FOR_ISLAND_MOVE;
                tableInfoLabel.setText("Choose the island to move the student...");
            }
            else { //move student dashboard
                new Thread(() -> notifyObserver((obs -> obs.onSendMoveAStudentDashboard(dashboardEntranceSelection.get(0))))).start();
            }
        }
    }


    private void openCharacter(ReducedCharacter character){
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/CharacterScene.fxml"));

        try {
            characterScene = new Scene(loader.load());
            characterScene.setUserData(character);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(characterScene);
        stage.setTitle("Character Detail");
        stage.setWidth(329);
        stage.setHeight(540);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        CharacterSceneController characterController = loader.getController();
        characterController.loadCharacter(character);
        currCharacterController = characterController;
        stage.showAndWait();
    }


    private void openDashboard(ReducedPlayer player){
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/DashboardScene.fxml"));

        try {
            dashboardScene = new Scene(loader.load());
            dashboardScene.setUserData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(dashboardScene);
        stage.setTitle("Dashboard "+ player.getName());
        stage.setWidth(999);
        stage.setHeight(434);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        DashboardSceneController dashboardController = loader.getController();
        dashboardController.loadDashboard(player.getDashboard(),player.getPlayerTowerColor());
        currDashboardController = dashboardController;
        stage.showAndWait();
    }

    private void selectFromDashboard(int entranceChoice, boolean isVariable){
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/DashboardScene.fxml"));

        try {
            dashboardScene = new Scene(loader.load());
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
        dashboardController.setStage(stage);
        dashboardController.selectionMode(entranceChoice);
        dashboardController.loadDashboard(gameModel.findPlayerById(gameModel.getMyPlayerId()).getDashboard(),gameModel.findPlayerById(gameModel.getMyPlayerId()).getPlayerTowerColor());
        currDashboardController = dashboardController;
        stage.showAndWait();
        dashboardEntranceSelection=dashboardController.getEntranceChoiceSelection();
    }

    public void updateGraphics(ClientGameModel model){
        gameModel = model;
        loadAssistants(model.getAssistantList());
        loadIslands(model.getIslandList(), model.getMotherNaturePos());
        loadClouds(model.getCloudList());
        loadPlayers(model.getPlayersList());
        if(model.isExpertMode()){
            loadCharacters(gameModel.getCharactersList());
        }
        else {
            character1Pane.setManaged(false);
            character2Pane.setManaged(false);
            character3Pane.setManaged(false);
        }

        if(stage!= null && stage.isShowing()){
            currDashboardController.loadDashboard(((ReducedPlayer)dashboardScene.getUserData()).getDashboard(),((ReducedPlayer)dashboardScene.getUserData()).getPlayerTowerColor());
        }
    }




    private void loadPlayers(List<ReducedPlayer> playerList){
        int i = 0;
        for (ReducedPlayer player : playerList){
            dashboardArray[i].setVisible(true);
            loadPlayer(dashboardArray[i],player);
            i++;
        }
    }

    private void loadPlayer(HBox playerPane, ReducedPlayer player){
        StackPane dashboardPane = (StackPane) playerPane.getChildren().get(0);
        Button button = (Button) dashboardPane.getChildren().get(1);

        String buttonText = "";
        buttonText += player.getName();

        if(player.getId() == gameModel.getMyPlayerId()){
            buttonText+= " (Me)";
        }

        if(gameModel.isExpertMode()){
            buttonText+=" - Money: "+player.getNumOfMoney();
        }

        button.setText(buttonText);

        ImageView thrownAssistantImage = (ImageView) playerPane.getChildren().get(1);
        if(player.getSelectedAssistant()!=null){
            getAssistantImage(player.getSelectedAssistant().getId(),thrownAssistantImage);
        }
        else {
            getWizardImage(player.getWizard(),thrownAssistantImage);
        }


    }

    private void loadCharacters(List<ReducedCharacter> characterList){
        int i=0;
        for (ReducedCharacter character:characterList){
            characterArray[i].setVisible(true);
            StackPane characterPane = characterArray[i];
            if(characterPane.getChildren().size()!=1){
                characterPane.getChildren().remove(1);
            }
            loadCharacter(characterPane,character);
            i++;
        }
    }

    private void loadCharacter(StackPane characterPane, ReducedCharacter character){
        ImageView charImageView = (ImageView) characterPane.getChildren().get(0);
        getCharacterImage(character.getId(),charImageView);
    }

    private void loadClouds(List<ReducedCloud> cloudList){
        int i=0;
        for (ReducedCloud c : cloudList){
            cloudArray[i].setVisible(true);
            StackPane cloudPane = cloudArray[i];
            if(cloudPane.getChildren().size()!=1){
                cloudPane.getChildren().remove(1);
            }
            loadCloud(cloudPane,c);
            i++;
        }
    }

    private void loadCloud(StackPane cloudPane, ReducedCloud cloud){
        AnchorPane anchorPane = new AnchorPane();
        double[][] positions = {{25,30},{58,30},{25,63},{58,63}};

        int i = 0;
        for (PawnColor pawn: cloud.getStudentsList()){
            ImageView pawnImageView = getPawnImage(pawn);
            pawnImageView.setFitHeight(30);
            pawnImageView.setFitWidth(30);
            pawnImageView.setLayoutX(positions[i][0]);
            pawnImageView.setLayoutY(positions[i][1]);
            anchorPane.getChildren().add(pawnImageView);
            i++;
        }

        cloudPane.getChildren().add(anchorPane);

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
            if(islandPane.getChildren().size()!=1){ //clear the old islands
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
        islandPane.setVisible(true);
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

    private void getAssistantImage(int id, ImageView imageView){
        String path = "/images/ass"+id+".png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(78);
        imageView.setFitHeight(115);
    }

    private void getCharacterImage(int id, ImageView imageView){
        String path = "/images/char"+id+"-small.png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(85);
        imageView.setFitHeight(130);
    }

    private void getWizardImage(Wizard wizard, ImageView imageView){
        String path = "/images/";
        switch (wizard){
            case SAGE:
                path+="MAGO1_1.jpg";
                break;
            case KING:
                path+="MAGO2_1.jpg";
                break;
            case WITCH:
                path+="MAGO3_1_correzioni.jpg";
                break;
            case ASIATIC:
                path+="MAGO4_1.jpg";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(78);
        imageView.setFitHeight(115);
    }

}
