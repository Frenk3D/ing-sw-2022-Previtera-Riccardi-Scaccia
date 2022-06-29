package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.client.ReducedDashboard;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DashboardSceneController {

    @FXML private GridPane entranceGridPane;
    @FXML private GridPane hallGridPane;
    @FXML private GridPane towersGridPane;
    @FXML private Button buttonSelectDashboard;

    private List<Integer> entranceChoiceList;
    private List<PawnColor> hallChoiceList;
    private boolean selectionMode=false;
    private boolean isCharacter10 = false;
    private boolean selectionSuccess=false;
    private int selectedStudent;
    private int selectedRequest;
    private Stage stage;

    @FXML
    public void initialize() {
        buttonSelectDashboard.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onSelectButtonClick);

    }

    /**
     * Default constructor
     */
    public DashboardSceneController(){
        entranceChoiceList = new ArrayList<>();
        hallChoiceList = new ArrayList<>();
    }

    /**
     * click on select button for character 10
     * @param e
     */
    private void onSelectButtonClick(Event e){
        selectionSuccess=true;
        stage.close();
    }

    /**
     * loads the dashboard on the graphics
     * @param dashboard
     * @param color tower color
     */
    public void loadDashboard(ReducedDashboard dashboard, TowerColor color){
        clearDashboard();

        //load entrance
        ImageView firstPawn = getPawnImage(dashboard.getEntranceList().get(0));
        firstPawn.setOnMouseEntered((e)->{
            firstPawn.setFitWidth(46);
            firstPawn.setFitHeight(46);
        });

        firstPawn.setOnMouseExited((e)->{
            firstPawn.setFitWidth(43);
            firstPawn.setFitHeight(43);
        });
        firstPawn.setUserData(0);
        firstPawn.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onStudentClicked);
        entranceGridPane.add(firstPawn, 1, 0);

        int index=1;
        for (int j=1;j<5;j++){
            for (int i = 0; i<2;i++){
                if(index < dashboard.getEntranceList().size()) {
                    ImageView pawnImage = getPawnImage(dashboard.getEntranceList().get(index));
                    pawnImage.setOnMouseEntered((e)->{
                        pawnImage.setFitWidth(46);
                        pawnImage.setFitHeight(46);
                    });

                    pawnImage.setOnMouseExited((e)->{
                        pawnImage.setFitWidth(44);
                        pawnImage.setFitHeight(44);
                    });
                    pawnImage.setUserData(index);
                    pawnImage.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onStudentClicked);
                    entranceGridPane.add(pawnImage, i, j);
                    index++;
                }
            }
        }

        //load hall and professors
        int row=0;
        for (PawnColor pawnColor : PawnColor.values()){
            int number = dashboard.getStudentsHall().get(pawnColor);
            for (int i=0;i<number;i++){
                ImageView pawnImage = getPawnImage(pawnColor);
                pawnImage.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onStudentHallClicked);
                pawnImage.setUserData(pawnColor);
                hallGridPane.add(pawnImage, i, row);
            }

            if(dashboard.getProfessorsList().contains(pawnColor)){
                ImageView professorImage = getProfessorImage(pawnColor);
                hallGridPane.add(professorImage,11,row);
            }
            row++;
        }

        //load towers
        if(color!=null) {
            index = 0;
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 2; i++) {
                    if (index < dashboard.getTowerNumber()) {
                        ImageView pawnImage = getTowerImage(color);
                        towersGridPane.add(pawnImage, i, j);
                        index++;
                    }
                }
            }
        }
    }

    private void onStudentClicked(Event e){
        ImageView pawnImg = (ImageView)e.getSource();
        int studentIndex = (int)pawnImg.getUserData();
        if (selectionMode){
            if(isCharacter10){
                if(selectedStudent<selectedRequest){
                    buttonSelectDashboard.setVisible(false);
                    entranceChoiceList.add(studentIndex);
                    selectedStudent++;
                    pawnImg.setOpacity(0.35);
                    pawnImg.setDisable(true);
                }
                return;
            }

            entranceChoiceList.add(studentIndex);
            selectedStudent++;
            pawnImg.setOpacity(0.35);
            pawnImg.setDisable(true);

            if(selectedStudent>=selectedRequest){
                selectionSuccess=true;
                stage.close();
                return;
            }
        }
    }

    /**
     * click on one student in the hall
     * @param e
     */
    private void onStudentHallClicked(Event e){
        ImageView pawnImg = (ImageView)e.getSource();
        PawnColor color = (PawnColor) pawnImg.getUserData();
        if (selectionMode&&isCharacter10&&hallChoiceList.size()<selectedStudent){
            hallChoiceList.add(color);
            pawnImg.setOpacity(0.35);
            pawnImg.setDisable(true);
            if(hallChoiceList.size()==entranceChoiceList.size()){
                buttonSelectDashboard.setVisible(true);
            }
        }
    }

    /**
     * set the stage for allowing to close it
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * enter in selection mode
     * @param entranceChoice
     * @param isCharacter10 true if we must use character 10
     */
    public void selectionMode(int entranceChoice, boolean isCharacter10){
        selectionMode=true;
        selectedStudent=0;
        selectedRequest=entranceChoice;
        this.isCharacter10=isCharacter10;
    }

    /**
     *
     * @return selection in the entrance
     */
    public List<Integer> getEntranceChoiceSelection() {
        return entranceChoiceList;
    }

    /**
     *
     * @return selection in the hall
     */
    public List<PawnColor> getHallChoiceSelection() {
        return hallChoiceList;
    }

    /**
     *
     * @return true if selection was successful, false if window was closed
     * */
    public boolean isSelectionSuccess() {
        return selectionSuccess;
    }

    /**
     * restores all grid panes before loading new data
     */
    private void clearDashboard(){
        for (int i = entranceGridPane.getChildren().size()-1 ; i>=0 ;i--) {
                entranceGridPane.getChildren().remove(i);
        }

        for (int i = hallGridPane.getChildren().size()-1 ; i>=0 ;i--) {
            hallGridPane.getChildren().remove(i);
        }

        for (int i = towersGridPane.getChildren().size()-1 ; i>=0 ;i--) {
            towersGridPane.getChildren().remove(i);
        }
    }

    /**
     * get imageview with selected image
     * @param color
     * @return imageView
     */
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
        imageView.setFitWidth(44);
        imageView.setFitHeight(44);
        return imageView;
    }

    /**
     * get imageview with selected professor
     * @param color
     * @return imageView
     */
    private ImageView getProfessorImage(PawnColor color){
        ImageView imageView = new ImageView();
        String path = "/images/";
        switch (color){
            case RED:
                path+="teacher_red.png";
                break;
            case BLUE:
                path+="teacher_blue.png";
                break;
            case YELLOW:
                path+="teacher_yellow.png";
                break;
            case PINK:
                path+="teacher_pink.png";
                break;
            case GREEN:
                path+="teacher_green.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(52);
        imageView.setFitHeight(52);
        return imageView;
    }

    /**
     * returns imageView with selected tower
     * @param color
     * @return imageView
     */
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
                path+="grey_tower.png";
                break;
        }
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        return imageView;
    }
}
