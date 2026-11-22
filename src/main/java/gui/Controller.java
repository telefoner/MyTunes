package gui;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;


public class Controller {
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
}
