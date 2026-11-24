package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SongController {

    @FXML
    private Label lblSong;

    @FXML
    private TextField titleField;

    @FXML
    private TextField artistField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField fileField;

    @FXML
    private ComboBox genreField;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    private String title;
    private String artist;
    private String genre;
    private double time;
    private String path;

    private Controller.WindowMode mode;


    public void setMode(Controller.WindowMode mode){
        this.mode = mode;
        if (mode == Controller.WindowMode.create) {
            titleField.setText("");
            artistField.setText("");
            timeField.setText("");
            fileField.setText("");
        } else {
            lblSong.setText("Edit existing song");
            titleField.setText(title);
            artistField.setText(artist);
            genreField.setValue(genre);
            timeField.setText(Double.toString(time));
            fileField.setText(path);
        }

    }

    public void initialize(){
        genreField.getItems().addAll("EDM", "Rock", "Metal", "Punk", "Pop", "Rap", "RNB", "Classic", "Lo-Fi", "Noise", "Jazz", "Blues", "Country", "Other");
    }
    @FXML
    private void saveBtnClicked(ActionEvent actionEvent) {
        ((Stage)btnSave.getScene().getWindow()).close();
    }

    @FXML
    private void undoBtnClicked(ActionEvent actionEvent) {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }

    @FXML
    private void onChooseBtnClicked(ActionEvent actionEvent) {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Find your song");

        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File file = filechooser.showOpenDialog(fileField.getScene().getWindow());

        if (file != null) {
            fileField.setText(file.getAbsolutePath());
        }
    }
}
