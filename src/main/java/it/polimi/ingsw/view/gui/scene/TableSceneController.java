package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.model.characters.MessageCharacterParameters;
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

import static it.polimi.ingsw.view.gui.GuiState.*;


public class TableSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private StackPane island1Pane;
    @FXML
    private StackPane island2Pane;
    @FXML
    private StackPane island3Pane;
    @FXML
    private StackPane island4Pane;
    @FXML
    private StackPane island5Pane;
    @FXML
    private StackPane island6Pane;
    @FXML
    private StackPane island7Pane;
    @FXML
    private StackPane island8Pane;
    @FXML
    private StackPane island9Pane;
    @FXML
    private StackPane island10Pane;
    @FXML
    private StackPane island11Pane;
    @FXML
    private StackPane island12Pane;
    @FXML
    private HBox HboxPlayer1;
    @FXML
    private HBox HboxPlayer2;
    @FXML
    private HBox HboxPlayer3;
    @FXML
    private HBox HboxPlayer4;
    @FXML
    private StackPane cloud1Pane;
    @FXML
    private StackPane cloud2Pane;
    @FXML
    private StackPane cloud3Pane;
    @FXML
    private StackPane cloud4Pane;
    @FXML
    private HBox assistantsPane;
    @FXML
    private StackPane character1Pane;
    @FXML
    private StackPane character2Pane;
    @FXML
    private StackPane character3Pane;
    @FXML
    private Button buttonCommand1;
    @FXML
    private Button buttonCommand2;
    @FXML
    private Label tableInfoLabel;
    @FXML
    private Label tableMoneyLabel;
    @FXML
    private ImageView imageViewTableMoney;

    private Stage stage;
    private Scene dashboardScene;
    private Scene characterScene;
    private CharacterSceneController currCharacterController;
    private DashboardSceneController currDashboardController;
    private GuiState guiState = GuiState.LOCKED;

    private final StackPane[] islandArray = new StackPane[12];
    private final HBox[] dashboardArray = new HBox[4];
    private final StackPane[] cloudArray = new StackPane[4];
    private final StackPane[] characterArray = new StackPane[3];
    private final int[] islandToId = new int[12];

    private ClientGameModel gameModel;
    private List<Integer> dashboardEntranceSelection;
    private List<PawnColor> dashboardHallSelection;
    private List<Integer> cardStudentsSelection;
    private PawnColor cardColorSelection;

    @FXML
    public void initialize() {
        loadGraphicsArrays(); //first load javafx references in arrays

        //set islands listener
        for (int i = 0; i < islandArray.length; i++) {
            StackPane island = islandArray[i];
            island.setUserData(i);
            island.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onIslandClick);
            island.setOnMouseEntered((e) -> {
                island.setScaleX(1.04);
                island.setScaleY(1.04);
            });
            island.setOnMouseExited((e) -> {
                island.setScaleX(1);
                island.setScaleY(1);
            });
        }

        //set dashboards listeners
        for (int i = 0; i < dashboardArray.length; i++) {
            StackPane dashboardPane = (StackPane) dashboardArray[i].getChildren().get(0);
            Button playerButton = (Button) dashboardPane.getChildren().get(1);
            playerButton.setUserData(i);
            playerButton.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onDashboardClick);
        }

        //set clouds listeners
        for (int i = 0; i < cloudArray.length; i++) {
            StackPane cloud = cloudArray[i];
            cloud.setUserData(i);
            cloud.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onCloudClick);
            cloud.setOnMouseEntered((e) -> {
                cloud.setScaleX(1.04);
                cloud.setScaleY(1.04);
            });
            cloud.setOnMouseExited((e) -> {
                cloud.setScaleX(1);
                cloud.setScaleY(1);
            });
        }

        //set characters listeners
        for (int i = 0; i < characterArray.length; i++) {
            StackPane character = characterArray[i];
            character.setUserData(i);
            character.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onCharacterClick);
            character.setOnMouseEntered((e) -> {
                character.setScaleX(1.04);
                character.setScaleY(1.04);
            });
            character.setOnMouseExited((e) -> {
                character.setScaleX(1);
                character.setScaleY(1);
            });
        }

        buttonCommand1.setUserData(0);
        buttonCommand2.setUserData(1);
        buttonCommand1.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onButtonCommandClick);
        buttonCommand2.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onButtonCommandClick);
    }

    /**
     * function called by gui class to ask to choose assistant
     */
    public void requestedChooseAssistant() {
        guiState = WAITING_FOR_ASSISTANT_CLICK;
        tableInfoLabel.setText("Choose an assistant...");
    }

    /**
     * function called by gui class to ask to move student
     */
    public void requestedMoveStudent() {
        guiState = GuiState.WAITING_FOR_MOVE_OR;
        buttonCommand1.setVisible(true);
        buttonCommand2.setVisible(true);
        tableInfoLabel.setText("Choose if you want to move a student on the dashboard or on an island...");
    }

    /**
     * function called by gui class to ask to move mother nature
     */
    public void requestedMoveMotherNature() {
        guiState = GuiState.WAITING_FOR_ISLAND_MN;
        tableInfoLabel.setText("Choose where to move mother nature...");
    }

    /**
     * function called by gui class to ask to choose cloud
     */
    public void requestedChooseCloud() {
        guiState = GuiState.WAITING_FOR_CLOUD;
        tableInfoLabel.setText("Choose a cloud...");
    }

    /**
     * function called by gui class to update game info
     */
    public void manageGameInfo(String text) {
        tableInfoLabel.setText(text);
        guiState = GuiState.LOCKED;
    }

    /**
     * function called by gui class to ask to choose character parameters
     */
    public void requestedCharacterParameters(int id) {
        System.out.println("Requesting character parameters...");
        MessageCharacterParameters params = new MessageCharacterParameters();
        params.setCharacterId(id);
        switch (id) {
            case 1: //choose 1 student from card and 1 island
                if (selectFromCharacter(getCharacterById(id, gameModel.getCharactersList()))) {
                    guiState = GuiState.WAITING_FOR_ISLAND_CHAR1;
                    tableInfoLabel.setText("Select the island where to put the student");
                }
                break;
            case 3:
                guiState = WAITING_FOR_ISLAND_CHAR3;
                tableInfoLabel.setText("Select the island where to calculate the influence");
                break;

            case 5: //choose 1 island
                guiState = WAITING_FOR_ISLAND_CHAR5;
                tableInfoLabel.setText("Select the island where to put the forbid card");
                break;
            case 7: //choose 1-3 students from card and 1-3 students from hall
                if (selectFromCharacter(getCharacterById(id, gameModel.getCharactersList()))) {
                    if (selectFromDashboard(cardStudentsSelection.size(), false)) {
                        params.setStudentsIndexList(cardStudentsSelection);
                        params.setStudentsIndexEntranceList(dashboardEntranceSelection);
                        new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
                    }
                }
                break;
            case 9: //choose 1 pawn color from card
                if (selectFromCharacter(getCharacterById(id, gameModel.getCharactersList()))) {
                    params.setSelectedColor(cardColorSelection);
                    new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
                }
                break;
            case 10: //choose 1-2 students from hall and 1-2 pawn colors
                if (selectFromDashboard(2, true)) {
                    params.setStudentsIndexEntranceList(dashboardEntranceSelection);
                    params.setSelectedColor(dashboardHallSelection.get(0));
                    if (dashboardHallSelection.size() == 2) {
                        params.setSelectedColor2(dashboardHallSelection.get(1));
                    }
                    new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
                }
                break;
            case 11: //choose 1 student from card
                if (selectFromCharacter(getCharacterById(id, gameModel.getCharactersList()))) {
                    params.setStudentIndex(cardStudentsSelection.get(0));
                    new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
                }
                break;
            case 12: //choose 1 pawn color from card
                if (selectFromCharacter(getCharacterById(id, gameModel.getCharactersList()))) {
                    params.setSelectedColor(cardColorSelection);
                    new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
                }
                break;
        }
    }

    /**
     * Island click event
     *
     * @param e
     */
    private void onIslandClick(Event e) {
        int islandIndex = (int) ((StackPane) e.getSource()).getUserData();
        if (guiState == GuiState.WAITING_FOR_ISLAND_MOVE) {
            guiState = GuiState.LOCKED;
            new Thread(() -> notifyObserver((obs -> obs.onSendMoveAStudentIsland(dashboardEntranceSelection.get(0), islandToId[islandIndex])))).start();
        } else if (guiState == GuiState.WAITING_FOR_ISLAND_MN) {
            new Thread(() -> notifyObserver((obs -> obs.onSendMoveMotherNature(islandToId[islandIndex])))).start();
        } else if (guiState == GuiState.WAITING_FOR_ISLAND_CHAR1) {
            guiState = GuiState.LOCKED;
            MessageCharacterParameters params = new MessageCharacterParameters();
            params.setCharacterId(1);
            params.setIslandIndex(islandToId[islandIndex]);
            params.setStudentIndex(cardStudentsSelection.get(0));
            new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
        } else if (guiState == GuiState.WAITING_FOR_ISLAND_CHAR3) {
            guiState = GuiState.LOCKED;
            MessageCharacterParameters params = new MessageCharacterParameters();
            params.setCharacterId(3);
            params.setIslandIndex(islandToId[islandIndex]);
            new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
        } else if (guiState == GuiState.WAITING_FOR_ISLAND_CHAR5) {
            guiState = GuiState.LOCKED;
            MessageCharacterParameters params = new MessageCharacterParameters();
            params.setCharacterId(5);
            params.setIslandIndex(islandToId[islandIndex]);
            new Thread(() -> notifyObserver((obs -> obs.onSendUseCharacter(params)))).start();
        }

    }

    /**
     * Assistant click event
     *
     * @param e
     */
    private void onAssistantClick(Event e) {
        int assistantIndex = (int) ((ImageView) e.getSource()).getUserData();
        if (guiState == WAITING_FOR_ASSISTANT_CLICK) {
            System.out.println("Click on assistant " + assistantIndex);
            new Thread(() -> notifyObserver((obs -> obs.onSendSelectAssistant(assistantIndex)))).start();
        }

    }

    /**
     * Cloud click event
     *
     * @param e
     */
    private void onCloudClick(Event e) {
        int cloudIndex = (int) ((StackPane) e.getSource()).getUserData();
        if (guiState == GuiState.WAITING_FOR_CLOUD) {
            System.out.println("Click on cloud " + cloudIndex);
            new Thread(() -> notifyObserver((obs -> obs.onSendChooseCloud(cloudIndex)))).start();
        }
    }

    /**
     * Dashboard click event
     *
     * @param e
     */
    private void onDashboardClick(Event e) {
        int playerIndex = (int) ((Button) e.getSource()).getUserData();
        System.out.println("Click on dashboard " + playerIndex);
        openDashboard(gameModel.getPlayersList().get(playerIndex));
    }

    /**
     * Character click event
     *
     * @param e
     */
    private void onCharacterClick(Event e) {
        int characterIndex = (int) ((StackPane) e.getSource()).getUserData();
        int result = openCharacter(gameModel.getCharactersList().get(characterIndex));
        if (result != -1 && guiState != GuiState.LOCKED && guiState != GuiState.WAITING_FOR_ASSISTANT_CLICK) {
            new Thread(() -> notifyObserver((obs -> obs.onAskCharacter(result)))).start();
            System.out.println("Used character " + result);
        }
    }

    /**
     * Button move student dashboard or move student island click event
     *
     * @param e
     */
    private void onButtonCommandClick(Event e) {
        buttonCommand1.setVisible(false);
        buttonCommand2.setVisible(false);
        if (guiState == GuiState.WAITING_FOR_MOVE_OR) {
            guiState = GuiState.LOCKED;
            int buttonIndex = (int) ((Button) e.getSource()).getUserData();
            System.out.println("Click on button " + buttonIndex);
            if (!selectFromDashboard(1, false)) {
                requestedMoveStudent();
                return;
            }

            if (buttonIndex == 0) { //move student island
                guiState = GuiState.WAITING_FOR_ISLAND_MOVE;
                tableInfoLabel.setText("Choose the island to move the student...");
            } else { //move student dashboard
                new Thread(() -> notifyObserver((obs -> obs.onSendMoveAStudentDashboard(dashboardEntranceSelection.get(0))))).start();
            }
        }
    }


    /**
     * open a new stage with the selected character
     *
     * @param character selected character
     * @return the id of played character if selected else -1
     */
    private int openCharacter(ReducedCharacter character) {
        boolean usable = guiState == WAITING_FOR_MOVE_OR || guiState == WAITING_FOR_ISLAND_MN || guiState == WAITING_FOR_CLOUD;

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
        characterController.loadCharacter(character, usable);
        characterController.setStage(stage);
        currCharacterController = characterController;
        stage.showAndWait();
        if (characterController.isChooseUse()) {
            return character.getId();
        }
        return -1;
    }

    /**
     * open character in a new stage to select things on it
     *
     * @param character selected character
     * @return true if selection succeeded false if window was closed
     */
    private boolean selectFromCharacter(ReducedCharacter character) {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/CharacterScene.fxml"));

        try {
            characterScene = new Scene(loader.load());
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
        characterController.loadCharacter(character, true);
        characterController.setStage(stage);
        characterController.selectionMode();
        currCharacterController = characterController;
        stage.showAndWait();
        cardColorSelection = characterController.getSelectedProfessorResult();
        cardStudentsSelection = characterController.getSelectedStudentResult();
        return characterController.isSelectionSuccess();
    }


    /**
     * open a new stage with the dashboard of selected player
     *
     * @param player selected player
     */
    private void openDashboard(ReducedPlayer player) {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(JavaFXGui.class.getResource("/fxml/DashboardScene.fxml"));

        try {
            dashboardScene = new Scene(loader.load());
            dashboardScene.setUserData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(dashboardScene);
        stage.setTitle("Dashboard " + player.getName());
        stage.setWidth(999);
        stage.setHeight(434);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        DashboardSceneController dashboardController = loader.getController();
        dashboardController.loadDashboard(player.getDashboard(), player.getPlayerTowerColor());
        currDashboardController = dashboardController;
        stage.showAndWait();
    }

    /**
     * open the dashboard of the selected player in selection mode
     *
     * @param entranceChoice number of students to choose
     * @param isCharacter10  true if the choice is of character 10
     * @return true if ok, false if closed
     */
    public boolean selectFromDashboard(int entranceChoice, boolean isCharacter10) {
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
        dashboardController.selectionMode(entranceChoice, isCharacter10);
        dashboardController.loadDashboard(gameModel.findPlayerById(gameModel.getMyPlayerId()).getDashboard(), gameModel.findPlayerById(gameModel.getMyPlayerId()).getPlayerTowerColor());
        currDashboardController = dashboardController;
        stage.showAndWait();
        dashboardEntranceSelection = dashboardController.getEntranceChoiceSelection();
        dashboardHallSelection = dashboardController.getHallChoiceSelection();
        return dashboardController.isSelectionSuccess();
    }

    /**
     * function called by gui when new data arrives
     *
     * @param model game to show
     */
    public void updateGraphics(ClientGameModel model) {
        gameModel = model;
        loadAssistants(model.getAssistantList());
        loadIslands(model.getIslandList(), model.getMotherNaturePos());
        loadClouds(model.getCloudList());
        loadPlayers(model.getPlayersList());
        if (model.isExpertMode()) {
            loadCharacters(gameModel.getCharactersList());
            tableMoneyLabel.setVisible(true);
            imageViewTableMoney.setVisible(true);
            tableMoneyLabel.setText(gameModel.getTableMoney() + " coins");
        } else {
            character1Pane.setManaged(false);
            character2Pane.setManaged(false);
            character3Pane.setManaged(false);
        }

        if (stage != null && stage.isShowing()) {
            currDashboardController.loadDashboard(((ReducedPlayer) dashboardScene.getUserData()).getDashboard(), ((ReducedPlayer) dashboardScene.getUserData()).getPlayerTowerColor());
        }
    }

    /**
     * loads the players in the graphic
     *
     * @param playerList
     */
    private void loadPlayers(List<ReducedPlayer> playerList) {
        int i = 0;
        for (ReducedPlayer player : playerList) {
            dashboardArray[i].setVisible(true);
            loadPlayer(dashboardArray[i], player);
            i++;
        }
    }

    /**
     * loads the selected player
     *
     * @param playerPane pane where to load the player
     * @param player     player to load
     */
    private void loadPlayer(HBox playerPane, ReducedPlayer player) {
        StackPane dashboardPane = (StackPane) playerPane.getChildren().get(0);
        Button button = (Button) dashboardPane.getChildren().get(1);

        String buttonText = "";
        buttonText += player.getName();

        if (player.getId() == gameModel.getMyPlayerId()) {
            buttonText += " (Me)";
        }

        if (gameModel.isExpertMode()) {
            buttonText += " - Coins: " + player.getNumOfMoney();
        }

        if (gameModel.getNumOfPlayers() == 4) {
            for (ReducedPlayer currP : gameModel.getPlayersList()) {
                if (currP != player && currP.getTeam() == player.getTeam()) {
                    buttonText += "\nTeam: " + currP.getName();
                    break;
                }
            }
        }

        button.setText(buttonText);

        ImageView thrownAssistantImage = (ImageView) playerPane.getChildren().get(1);
        if (player.getSelectedAssistant() != null) {
            getAssistantImage(player.getSelectedAssistant().getId(), thrownAssistantImage);
        } else {
            getWizardImage(player.getWizard(), thrownAssistantImage);
        }


    }

    /**
     * loads the characters in the graphic
     *
     * @param characterList
     */
    private void loadCharacters(List<ReducedCharacter> characterList) {
        int i = 0;
        for (ReducedCharacter character : characterList) {
            characterArray[i].setVisible(true);
            StackPane characterPane = characterArray[i];
            if (characterPane.getChildren().size() != 1) {
                characterPane.getChildren().remove(1);
            }
            loadCharacter(characterPane, character);
            i++;
        }
    }

    /**
     * loads the character
     *
     * @param characterPane selected Pane
     * @param character     selected character
     */
    private void loadCharacter(StackPane characterPane, ReducedCharacter character) {
        ImageView charImageView = (ImageView) characterPane.getChildren().get(0);
        getCharacterImage(character.getId(), charImageView);
    }

    /**
     * loads the clouds in the graphic
     *
     * @param cloudList
     */
    private void loadClouds(List<ReducedCloud> cloudList) {
        int i = 0;
        for (ReducedCloud c : cloudList) {
            cloudArray[i].setVisible(true);
            StackPane cloudPane = cloudArray[i];
            if (cloudPane.getChildren().size() != 1) {
                cloudPane.getChildren().remove(1);
            }
            loadCloud(cloudPane, c);
            i++;
        }
    }

    /**
     * load the cloud
     *
     * @param cloudPane selected Pane
     * @param cloud     selected cloud
     */
    private void loadCloud(StackPane cloudPane, ReducedCloud cloud) {
        AnchorPane anchorPane = new AnchorPane();
        double[][] positions = {{25, 30}, {58, 30}, {25, 63}, {58, 63}};

        int i = 0;
        for (PawnColor pawn : cloud.getStudentsList()) {
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

    /**
     * load the islands in the graphics
     *
     * @param islandList   list of islands
     * @param motherNature mother nature position
     */
    private void loadIslands(List<ReducedIsland> islandList, int motherNature) {
        int i = 0, index = -1;
        boolean motherNatureHere;
        for (int j = 0; j < 12; j++) {
            islandToId[j] = -1;
        }
        for (ReducedIsland island : islandList) {
            motherNatureHere = motherNature == i;
            index += island.getWeight();
            StackPane islandPane = islandArray[index];
            if (islandPane.getChildren().size() != 1) { //clear the old islands
                islandPane.getChildren().remove(1);
            }
            islandToId[index] = i;
            loadIsland(islandPane, island, motherNatureHere);
            i++;
        }
        for (int j = 0; j < 12; j++) {
            if (islandToId[j] == -1) {
                islandArray[j].setVisible(false);
            }
        }
    }

    /**
     * load selected island
     *
     * @param islandPane   selected Pane
     * @param island
     * @param motherNature true if mother nature is on the island
     */
    private void loadIsland(StackPane islandPane, ReducedIsland island, boolean motherNature) {
        islandPane.setVisible(true);
        AnchorPane anchorPane = new AnchorPane();

        //preload colors
        Map<PawnColor, Integer> colorsMap = new HashMap<>();
        for (PawnColor p : island.getStudentsList()) {
            Integer existingNum = colorsMap.get(p);
            if (existingNum == null) {
                colorsMap.put(p, 1);
            } else {
                colorsMap.put(p, ++existingNum);
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

                Label label = new Label("" + colorsMap.get(p));
                label.setFont(Font.font("System", FontWeight.NORMAL, 13));
                label.setTextFill(Color.WHITE);
                label.setLayoutX(62);
                label.setLayoutY(vPos);
                anchorPane.getChildren().add(label);
                vPos += 20;
            }
        }

        //draw mother nature
        if (motherNature) {
            ImageView mnImageView = new ImageView();
            mnImageView.setImage(new Image(getClass().getResourceAsStream("/images/mother_nature.png")));
            mnImageView.setFitWidth(61);
            mnImageView.setFitHeight(61);
            mnImageView.setLayoutX(72);
            mnImageView.setLayoutY(-3);
            anchorPane.getChildren().add(mnImageView);
        }


        //draw tower
        if (island.getTowerColor() != null) {
            ImageView towerImageView = getTowerImage(island.getTowerColor());
            towerImageView.setLayoutX(76);
            towerImageView.setLayoutY(60);
            anchorPane.getChildren().add(towerImageView);

            Label towerLabel = new Label("" + island.getWeight());
            towerLabel.setFont(Font.font("System", FontWeight.BOLD, 17));
            towerLabel.setTextFill(Color.WHITE);
            towerLabel.setLayoutX(117);
            towerLabel.setLayoutY(71);
            anchorPane.getChildren().add(towerLabel);
        }

        //draw forbid cards
        if (island.getForbidCards() != 0) {
            ImageView forbidCardImageView = new ImageView();
            forbidCardImageView.setImage(new Image(getClass().getResourceAsStream("/images/deny_icon.png")));
            forbidCardImageView.setFitWidth(40);
            forbidCardImageView.setFitHeight(40);
            forbidCardImageView.setLayoutX(0);
            forbidCardImageView.setLayoutY(60);
            anchorPane.getChildren().add(forbidCardImageView);

            Label forbidLabel = new Label("" + island.getForbidCards());
            forbidLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
            forbidLabel.setTextFill(Color.WHITE);
            forbidLabel.setLayoutX(16);
            forbidLabel.setLayoutY(40);
            anchorPane.getChildren().add(forbidLabel);
        }

        islandPane.getChildren().add(anchorPane);
    }

    /**
     * load assistants on the graphic
     *
     * @param assistantList
     */
    private void loadAssistants(List<ReducedAssistant> assistantList) {
        assistantsPane.getChildren().clear();
        for (ReducedAssistant assistant : assistantList) {
            ImageView assistantImageView = getAssistantImage(assistant.getId());
            assistantsPane.getChildren().add(assistantImageView);
            assistantImageView.setUserData(assistant.getId());
            assistantImageView.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onAssistantClick);
            assistantImageView.setOnMouseEntered((e) -> {
                assistantImageView.setScaleX(1.04);
                assistantImageView.setScaleY(1.04);
            });
            assistantImageView.setOnMouseExited((e) -> {
                assistantImageView.setScaleX(1);
                assistantImageView.setScaleY(1);
            });
        }

    }

    /**
     * load all the table components in an array to iterate on them
     */
    private void loadGraphicsArrays() {
        islandArray[0] = island1Pane;
        islandArray[1] = island2Pane;
        islandArray[2] = island3Pane;
        islandArray[3] = island4Pane;
        islandArray[4] = island5Pane;
        islandArray[5] = island6Pane;
        islandArray[6] = island7Pane;
        islandArray[7] = island8Pane;
        islandArray[8] = island9Pane;
        islandArray[9] = island10Pane;
        islandArray[10] = island11Pane;
        islandArray[11] = island12Pane;
        dashboardArray[0] = HboxPlayer1;
        dashboardArray[1] = HboxPlayer2;
        dashboardArray[2] = HboxPlayer3;
        dashboardArray[3] = HboxPlayer4;
        cloudArray[0] = cloud1Pane;
        cloudArray[1] = cloud2Pane;
        cloudArray[2] = cloud3Pane;
        cloudArray[3] = cloud4Pane;
        characterArray[0] = character1Pane;
        characterArray[1] = character2Pane;
        characterArray[2] = character3Pane;
    }

    /**
     * close dashboard or character stages if opened when we exit the game
     */
    public void closeAllPopups() {
        if (stage != null && stage.isShowing()) {
            stage.close();
        }
    }

    /**
     * get imageview of selected Pawn
     *
     * @param color
     * @return ImageView
     */
    private ImageView getPawnImage(PawnColor color) {
        ImageView imageView = new ImageView();
        String path = "/images/";
        switch (color) {
            case RED:
                path += "redStudent3D.png";
                break;
            case BLUE:
                path += "blueStudent3D.png";
                break;
            case YELLOW:
                path += "yellowStudent3D.png";
                break;
            case PINK:
                path += "pinkStudent3D.png";
                break;
            case GREEN:
                path += "greenStudent3D.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }

    /**
     * get imageview of selected Tower
     *
     * @param color
     * @return ImageView
     */
    private ImageView getTowerImage(TowerColor color) {
        ImageView imageView = new ImageView();
        String path = "/images/";
        switch (color) {
            case BLACK:
                path += "black_tower.png";
                break;
            case WHITE:
                path += "white_tower.png";
                break;
            case GRAY:
                path += "grey_tower.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * get imageview of selected Assistant
     *
     * @param id
     * @return ImageView
     */
    private ImageView getAssistantImage(int id) {
        ImageView imageView = new ImageView();
        String path = "/images/ass" + id + ".png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(74);
        imageView.setFitHeight(110);
        return imageView;
    }

    /**
     * loads assistant image in the selected imageview
     *
     * @param id
     * @param imageView
     */
    private void getAssistantImage(int id, ImageView imageView) {
        String path = "/images/ass" + id + ".png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(78);
        imageView.setFitHeight(115);
    }

    /**
     * loads character image in the selected imageview
     *
     * @param id
     * @param imageView
     */
    private void getCharacterImage(int id, ImageView imageView) {
        String path = "/images/char" + id + "-small.png";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(85);
        imageView.setFitHeight(130);
    }

    /**
     * loads wizard image in the selected imageview
     *
     * @param wizard
     * @param imageView
     */
    private void getWizardImage(Wizard wizard, ImageView imageView) {
        String path = "/images/";
        switch (wizard) {
            case SAGE:
                path += "MAGO1_1.jpg";
                break;
            case KING:
                path += "MAGO2_1.jpg";
                break;
            case WITCH:
                path += "MAGO3_1_correzioni.jpg";
                break;
            case ASIATIC:
                path += "MAGO4_1.jpg";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(78);
        imageView.setFitHeight(115);
    }

    /**
     * get character object from its id
     *
     * @param id            character id
     * @param characterList character list
     * @return ReducedCharacter
     */
    private ReducedCharacter getCharacterById(int id, List<ReducedCharacter> characterList) {
        for (ReducedCharacter c : characterList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

}
