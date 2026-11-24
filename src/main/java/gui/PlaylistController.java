package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    }

    @FXML
    public void btnCancelClicked(ActionEvent actionEvent) {
    }
}
