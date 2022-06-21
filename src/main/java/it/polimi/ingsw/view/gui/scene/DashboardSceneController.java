package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.client.ReducedDashboard;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DashboardSceneController {

    @FXML private GridPane entranceGridPane;
    @FXML private GridPane hallGridPane;
    @FXML private GridPane towersGridPane;

    public void loadDashboard(ReducedDashboard dashboard){
        for (int j=0;j<5;j++){
            for (int i = 0; i<2;i++){
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/blueStudent3D.png")));
                imageView.setFitWidth(43);
                imageView.setFitHeight(43);
                entranceGridPane.add(imageView,i,j);
            }
        }

        for (int j=0; j<5;j++){
            for (int i = 0; i<10;i++){
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/blueStudent3D.png")));
                imageView.setFitWidth(43);
                imageView.setFitHeight(43);
                imageView.setUserData(i);
                imageView.setOnMouseEntered((e)->{
                    imageView.setFitWidth(46);
                    imageView.setFitHeight(46);
                });

                imageView.setOnMouseExited((e)->{
                    imageView.setFitWidth(43);
                    imageView.setFitHeight(43);
                });
                imageView.setOnMousePressed((e)->{
                    System.out.println("clicked student "+imageView.getUserData());
                });
                hallGridPane.add(imageView, i, j);
            }
        }
    }
}
