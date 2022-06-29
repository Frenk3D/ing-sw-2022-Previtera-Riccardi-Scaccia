package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.client.ReducedCharacter;
import it.polimi.ingsw.model.enumerations.PawnColor;
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


public class CharacterSceneController {

    @FXML private ImageView characterDetailImageView;
    @FXML private Button buttonPlayCharacter;
    @FXML private GridPane characterGridPane;
    @FXML private ImageView imageViewUsedMoney;


    private ReducedCharacter character;
    private Stage stage;
    private boolean chooseUse = false; //set true if I choose to use the character

    private List<Integer> selectedStudentResult; //result of the selection
    private PawnColor selectedProfessorResult; //result of the selection
    private int selectedStudents=0;
    private boolean selectionMode=false;
    private boolean selectionSuccess=false;

    public CharacterSceneController(){
        selectedStudentResult = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        buttonPlayCharacter.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onUseClick);

    }


    /**
     * laad selected character on the graphics
     * @param character
     */
    public void loadCharacter(ReducedCharacter character, boolean enabled){
        this.character=character;
        buttonPlayCharacter.setVisible(enabled);
        getCharacterImage(character.getId(),characterDetailImageView);
        if(character.isUsed()){
            imageViewUsedMoney.setVisible(true);
        }
        else {
            imageViewUsedMoney.setVisible(false);
        }

        if(character.getId() == 1 || character.getId() == 7 || character.getId() == 11){
            int index = 0;
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 2; i++) {
                    if (index < character.getCardStudentsList().size()) {
                        ImageView pawnImage = getPawnImage(character.getCardStudentsList().get(index));
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
                        characterGridPane.add(pawnImage, i, j);
                        index++;
                    }
                }
            }
        }

        else if(character.getId() == 5){
            int index = 0;
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 2; i++) {
                    if (index < character.getNumOfForbidCards()) {
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/forbidCircle.png")));
                        imageView.setFitWidth(44);
                        imageView.setFitHeight(44);
                        characterGridPane.add(imageView, i, j);
                        index++;
                    }
                }
            }
        }

    }

    /**
     * enters the character in selection mode to choose its pawn
     */
    public void selectionMode(){
        selectionMode=true;
        buttonPlayCharacter.setText("Select");
        buttonPlayCharacter.setVisible(false);

        if(character.getId()==9 || character.getId()==12){
            PawnColor[] pawnColors = PawnColor.values();

            int index = 0;
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 2; i++) {
                    if (index < pawnColors.length) {
                        ImageView professorImage = getProfessorImage(pawnColors[index]);
                        professorImage.setRotate(-90);
                        professorImage.setOnMouseEntered((e)->{
                            professorImage.setFitWidth(46);
                            professorImage.setFitHeight(46);
                        });

                        professorImage.setOnMouseExited((e)->{
                            professorImage.setFitWidth(44);
                            professorImage.setFitHeight(44);
                        });
                        professorImage.setUserData(pawnColors[index]);
                        professorImage.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onProfessorClicked);
                        characterGridPane.add(professorImage, i, j);
                        index++;
                    }
                }
            }

        }
    }

    /**
     * executed when clicking the use button
     * closes the stage
     * @param e
     */
    private void onUseClick(Event e){
        chooseUse = true;
        selectionSuccess = true;
        stage.close();
    }

    /**
     * executed when clicking on one student on the card
     * @param e
     */
    private void onStudentClicked(Event e){
        ImageView pawnImg = (ImageView)e.getSource();
        int studentId = (int) pawnImg.getUserData();

        if (selectionMode){
            if(character.getId()==1||character.getId()==11){
                selectedStudentResult.add(studentId);
                selectionSuccess = true;
                stage.close();
                return;
            }
            if(character.getId()==7){
                selectedStudentResult.add(studentId);
                selectedStudents++;
                pawnImg.setDisable(true);
                pawnImg.setOpacity(0.35);
                if(selectedStudents==3){
                    selectionSuccess = true;
                    stage.close();
                }
                if(selectedStudents>=1){
                    buttonPlayCharacter.setVisible(true); //enable the selection button for choosing less than 3 students
                }
            }
        }
    }

    /**
     * executed when clicking on one professor on the card
     * @param e
     */
    private void onProfessorClicked(Event e){
        PawnColor pawnColor = (PawnColor) ((ImageView)e.getSource()).getUserData();
        if(selectionMode){
            selectedProfessorResult = pawnColor;
            selectionSuccess=true;
            stage.close();
        }
    }

    /**
     *
     * @return true if play button was clicked
     */
    public boolean isChooseUse() {
        return chooseUse;
    }

    /**
     * Give reference of current stage
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @return true if selection was successful
     */
    public boolean isSelectionSuccess() {
        return selectionSuccess;
    }

    /**
     *
     * @return selected PawnColor
     */
    public PawnColor getSelectedProfessorResult() {
        return selectedProfessorResult;
    }

    /**
     *
     * @return list of selected students
     */
    public List<Integer> getSelectedStudentResult() {
        return selectedStudentResult;
    }

    /**
     * loads selected character on imageView
     * @param id
     * @param imageView
     */
    private void getCharacterImage(int id, ImageView imageView){
        String path = "/images/char"+id+".jpg";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(329);
        imageView.setFitHeight(500);
    }

    /**
     * gives imageview of selected student
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
     * gives ImageView of selected professor
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
        imageView.setFitWidth(44);
        imageView.setFitHeight(44);
        return imageView;
    }
}
