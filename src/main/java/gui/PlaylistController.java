package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlaylistController {
    private Controller.WindowMode mode;

    private String playlistName;

    @FXML
    private TextField inputPlaylistName;

    @FXML
    private Label playlistLbl;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    public void setMode(Controller.WindowMode mode, Stage stage) {
        this.mode = mode;
        if (mode == Controller.WindowMode.create) {
            stage.setTitle("Add a playlist");
            inputPlaylistName.setText("");
        } else {
            stage.setTitle("Edit existing playlist");
            inputPlaylistName.setText(playlistName);
            playlistLbl.setText("Edit existing playlist");
        }
    }

    @FXML
    public void btnSaveClicked(ActionEvent actionEvent) {
        ((Stage)btnSave.getScene().getWindow()).close();
    }

    @FXML
    public void btnCancelClicked(ActionEvent actionEvent) {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
