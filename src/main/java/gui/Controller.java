package gui;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public enum WindowMode{
        create, edit
    }

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

    public void songDuration() {
        //songTime.setText(getFormatedDuration());
    }

    public void openSongWindow(WindowMode mode){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("song-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            SongController controller = fxmlLoader.getController();
            controller.setMode(mode, stage);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            System.out.println("Failed to open song window: " + e.getMessage());
        }
    }

    public void openPlaylistWindow(Controller.WindowMode mode){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("playlist-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            PlaylistController controller = fxmlLoader.getController();
            controller.setMode(mode, stage);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            System.out.println("Failed to open song window: " + e.getMessage());
        }
    }

    public void initialize() {
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
        openSongWindow(WindowMode.create);
    }

    @FXML
    private void btnUpClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void btnSongToPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDelSongsClicked(ActionEvent actionEvent) {
        showConfirmation("Are you sure you want to delete the song?", "The song will be deleted from the list, but you could add it again later.");
    }

    @FXML
    private void btnDelPlaylstClicked(ActionEvent actionEvent) {
        showConfirmation("Are you sure you want to delete selected playlist?", "The deleted playlist won't delete the songs from your machine");
    }

    @FXML
    private void btnDownClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void btnSearchClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void btnNewPlaylstClicked(ActionEvent actionEvent) {
        openPlaylistWindow(WindowMode.create);
    }

    @FXML
    private void btnEditPlaylstClicked(ActionEvent actionEvent) {
        openPlaylistWindow(WindowMode.edit);
    }

    @FXML
    private void btnDelSongPlaylstClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void btnEditSongsClicked(ActionEvent actionEvent) {
        openSongWindow(WindowMode.edit);
    }

    private void showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
