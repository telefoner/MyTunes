package gui;

import javafx.animation.RotateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import be.Song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.Locale;


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
    private Circle vinyl;

    @FXML
    private TableColumn<Song, String> songTitleColumn;

    @FXML
    private TableColumn<Song, String> songArtistColumn;

    @FXML
    private TableColumn<Song, String> songGenreColumn;

    @FXML
    private TableColumn<Song, String> songTimeColumn;

    @FXML
    public void initialize() {
        //setting image and animation for vinyl
        Image image = new Image(getClass().getResourceAsStream("/vinyl.png"));
        vinyl.setFill(new ImagePattern(image));
        rotation = new RotateTransition(Duration.seconds(4), vinyl);
        rotation.setByAngle(360);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);

        songTitleColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTitle()));

        songArtistColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getArtist()));

        songGenreColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCategory()));

        songTimeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getFormattedDuration()));

        SortedList<Song> sortedSongs =
                new SortedList<>(filteredSongs);

        sortedSongs.comparatorProperty()
                .bind(songsTable.comparatorProperty());

        songsTable.setItems(sortedSongs);

    }

    @FXML
    private TextField filterTextField;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<Song> songsTable;

    private final ObservableList<Song> allSongs =
            FXCollections.observableArrayList();

    private final FilteredList<Song> filteredSongs =
            new FilteredList<>(allSongs, song -> true);

    private boolean filterActive = false;

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


    @FXML
    private void handleFilterClear() {

        // If filter is active, clear it
        if (filterActive) {
            filteredSongs.setPredicate(song -> true);

            filterTextField.clear();
            filterButton.setText("Filter");
            filterActive = false;
            return;
        }

        // Get the search text
        String query = filterTextField.getText()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (query.isEmpty()) {
            return;
        }

        // Filter by title or artist
        filteredSongs.setPredicate(song -> {

            String title = song.getTitle() == null
                    ? ""
                    : song.getTitle().toLowerCase(Locale.ROOT);

            String artist = song.getArtist() == null
                    ? ""
                    : song.getArtist().toLowerCase(Locale.ROOT);

            return title.contains(query)
                    || artist.contains(query);
        });

        filterButton.setText("Clear");
        filterActive = true;
    }

}
