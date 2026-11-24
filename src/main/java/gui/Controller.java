package gui;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;


public class Controller {

    private RotateTransition rotation;

    @FXML
    public Slider timeSlider;

    @FXML
    public TableView playlistList;

    @FXML
    public TableView songsInPlaylist;

    @FXML
    public TableView songsList;

    @FXML
    public Label songLbl;

    @FXML
    public Button btnPlay;

    @FXML
    public Label artistLbl;

    @FXML
    public Label songTime;

    @FXML
    private Button upBtn;

    @FXML
    private Button downbtn;

    @FXML
    private Button newBtn;

    @FXML
    private Button btnEdit;

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
        rotation = new RotateTransition(Duration.seconds(4), vinyl);
        rotation.setByAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);
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

    public void btnLastSong(ActionEvent actionEvent) {
    }

    public void btnPlayClicked(ActionEvent actionEvent) {
        //set spinning animation
        if (btnPlay.getText().equals("▶")) {
            btnPlay.setText("⏸");
            rotation.play();
        } else {
            btnPlay.setText("▶");
            rotation.stop();
        }

    }

    public void btnNextSong(ActionEvent actionEvent) {
    }

    public void timeSet(MouseDragEvent mouseDragEvent) {
    }

    public void onTextInput(ActionEvent actionEvent) {

    }

    public void onBtnSearchClicked(ActionEvent actionEvent) {

    }

    public void newBtnPlaylstClicked(ActionEvent actionEvent) {

    }

    public void editBtnPlaylstClicked(ActionEvent actionEvent) {

    }

    public void delBtnPlaylstClicked(ActionEvent actionEvent) {

    }

    public void upBtnClicked(ActionEvent actionEvent) {

    }

    public void downBtnClicked(ActionEvent actionEvent) {

    }

    public void newBtnSongsClicked(ActionEvent actionEvent) {

    }

    public void editButtonSongsClicked(ActionEvent actionEvent) {

    }

    public void deleteButtonSongsClicked(ActionEvent actionEvent) {

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
