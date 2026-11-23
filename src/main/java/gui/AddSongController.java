package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSongController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField fileField;
    @FXML
    private ComboBox GenreField;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

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
    }
}
