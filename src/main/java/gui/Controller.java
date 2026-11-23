package gui;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;


public class Controller {
    @FXML
    private Button upBtn;
    @FXML FXML
    private Button downbtn;

    @FXML
    private Button newBtn;
    @FXML
    private Button btnEdit;
    @FXML
    private Circle vinyl;

    @FXML
    public void initialize() {
        //setting image and animation for vinyl
        Image image = new Image(getClass().getResourceAsStream("/vinyl.png"));
        vinyl.setFill(new ImagePattern(image));
        RotateTransition rotation = new RotateTransition(Duration.seconds(4), vinyl);
        rotation.setByAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.play();
    }

    public void editbuttonClicked(ActionEvent actionEvent) {
    }

    public void newButtonClicked(ActionEvent actionEvent) {
    }

    public void upButtonClicked(ActionEvent actionEvent) {
    }

    public void downButtonClicked(ActionEvent actionEvent) {
    }

    public void newBtnClicked(ActionEvent actionEvent) {
    }

    public void editButtonClicked(ActionEvent actionEvent) {
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
    }
}
