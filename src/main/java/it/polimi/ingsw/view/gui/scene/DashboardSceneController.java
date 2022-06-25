package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.client.ReducedDashboard;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import javafx.event.Event;
import javafx.fxml.FXML;
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

    private List<Integer> entranceChoiceList;
    private boolean selectionMode=false;
    private int selectedStudent;
    private int selectedRequest;
    private Stage stage;

    public DashboardSceneController(){
        entranceChoiceList = new ArrayList<>();
    }

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
        int studentIndex = (int)((ImageView)e.getSource()).getUserData();
        if (selectionMode){
            entranceChoiceList.add(studentIndex);
            selectedStudent++;
            if(selectedStudent>=selectedRequest){
                stage.close();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void selectionMode(int entranceChoice){
        selectionMode=true;
        selectedStudent=0;
        selectedRequest=entranceChoice;
    }

    public List<Integer> getEntranceChoiceSelection() {
        return entranceChoiceList;
    }

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
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
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
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        return imageView;
    }
}
