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


public class CharacterSceneController {

    @FXML private ImageView characterDetailImageView;
    @FXML private Button buttonPlayCharacter;
    @FXML private GridPane characterGridPane;
    @FXML private ImageView imageViewUsedMoney;


    private Stage stage;

    @FXML
    public void initialize() {
        buttonPlayCharacter.addEventHandler(MouseEvent.MOUSE_PRESSED,this::onUseClick);

    }


    public void loadCharacter(ReducedCharacter character){
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
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/deny_icon.png")));
                        imageView.setFitWidth(44);
                        imageView.setFitHeight(44);
                        characterGridPane.add(imageView, i, j);
                        index++;
                    }
                }
            }
        }

    }

    private void onUseClick(Event e){

    }

    private void onStudentClicked(Event e){

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void getCharacterImage(int id, ImageView imageView){
        String path = "/images/char"+id+".jpg";
        imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(329);
        imageView.setFitHeight(500);
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
}
