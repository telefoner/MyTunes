package gui;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;

import java.io.IOException;


public class Controller {

    private RotateTransition rotation;

    @FXML
    private Slider timeSlider;

    @FXML
    private TableView playlistList;

    @FXML
    private TableView songsInPlaylist;

    @FXML
    private TableView songsList;

    @FXML
    private Label songLbl;

    @FXML
    private Button btnPlay;

    @FXML
    private Label artistLbl;

    @FXML
    private Label songTime;

    @FXML
    private Circle vinyl;

    @FXML
    private void initialize() {
        //setting image and animation for vinyl
        Image image = new Image(getClass().getResourceAsStream("/vinyl.png"));
        vinyl.setFill(new ImagePattern(image));
        rotation = new RotateTransition(Duration.seconds(4), vinyl);
        rotation.setByAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);
    }

    @FXML
    private void btnLastSong(ActionEvent actionEvent) {
    }

    @FXML
    private void btnPlayClicked(ActionEvent actionEvent) {
        if (btnPlay.getText().equals("▶")) {
            btnPlay.setText("⏸");
            //activate or stop spinning animation
            rotation.play();
        } else {
            btnPlay.setText("▶");
            rotation.stop();
        }

    }

    @FXML
    private void btnNextSong(ActionEvent actionEvent) {
    }

    @FXML
    private void timeSet(MouseDragEvent mouseDragEvent) {
    }

    @FXML
    private void onTextInput(ActionEvent actionEvent) {

    }


    @FXML
    private void btnNewSongsClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addsong-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add new song");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            System.out.println("Failed to open song window: " + e.getMessage());
        }
    }

    @FXML
    public void btnUpClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnSongToPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDelSongsClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDelPlaylstClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDownClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnSearchClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnNewPlaylstClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnEditPlaylstClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDelSongPlaylstClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void btnEditSongsClicked(ActionEvent actionEvent) {
    }
}
