package gui;

import be.MyTunesException;
import be.Playlist;
import be.Song;
import bll.MusicPlayer;
import bll.PlaylistLogic;
import bll.SongLogic;
import javafx.animation.RotateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.Interpolator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Controller {

    @FXML
    private TableColumn<Song, String> titleColumnSongs;

    @FXML
    private TableColumn<Song, String> artistColumnSongs;

    @FXML
    private TableColumn<Song, String> timeColumnSong;

    @FXML
    private TableColumn<Song, String> genreColumnSongs;
    @FXML
    private TextField filterFieldInput;


    public enum WindowMode{
        create, edit
    }

    private RotateTransition rotation;

    private final ObservableList<Song> allSongs = FXCollections.observableArrayList();

    private final FilteredList<Song> filteredSongs = new FilteredList<>(allSongs, song -> true);

    private boolean filterActive = false;

    private TableView<Song> lastClickedSongTable;

    @FXML
    private Slider timeSlider;

    @FXML
    private TableView<Playlist> playlistList;

    @FXML
    private TableView<Song> songsInPlaylist;

    @FXML
    private TableView<Song> songsList;

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

    private final SongLogic songLogic = new SongLogic();
    private final MusicPlayer musicPlayer = new MusicPlayer();
    private final PlaylistLogic playlistLogic = new PlaylistLogic();


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

        titleColumnSongs.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));

        artistColumnSongs.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getArtist()));

        genreColumnSongs.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));

        timeColumnSong.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getDurationSeconds())));

        SortedList<Song> sortedSongs = new SortedList<>(filteredSongs);

        sortedSongs.comparatorProperty().bind(songsList.comparatorProperty());

        songsList.setItems(sortedSongs);

        allSongs.addAll(
                new Song("Kill the teenager!", "Orgasm nostradamusa", "Punk", 300,"fsmfsdgml/fsdsf")
        );
    }

    @FXML
    private void btnLastSong(ActionEvent actionEvent) {
        try {
            musicPlayer.previous();
        } catch (MyTunesException e) {
            e.printStackTrace();
        }
        updatePlayerInfo();
    }

    @FXML
    private void btnPlayClicked(ActionEvent actionEvent) {
        try {
            playSelectedSong();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
        updatePlayerInfo();
    }

    @FXML
    private void btnNextSong(ActionEvent actionEvent) {
        try {
            musicPlayer.next();
        } catch (MyTunesException e) {
            e.printStackTrace();
        }
        updatePlayerInfo();
    }
    private void playSelectedSong() throws MyTunesException {
        List<Song> songs = songsList.getItems();
        if (songs.isEmpty()) {
            return;   // nothing to play
        }
        int index = songsList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            index = 0;   // nothing selected: start with the first song
        }
        musicPlayer.playSongs(songs, index);
    }

    @FXML
    private void timeSet(MouseDragEvent mouseDragEvent) {
        musicPlayer.seek(timeSlider.getValue());
    }

    @FXML
    private void onTextInput(ActionEvent actionEvent) {
        btnSearchClicked(actionEvent);
    }


    @FXML
    private void btnNewSongsClicked(ActionEvent actionEvent) {
        openSongWindow(WindowMode.create);
    }

    @FXML
    private void btnUpClicked(ActionEvent actionEvent) {
        Playlist playlist = playlistList.getSelectionModel().getSelectedItem();
        int index = songsInPlaylist.getSelectionModel().getSelectedIndex();
        if (playlist == null || index < 0) {
            showError("Select a song in \"Songs in playlist\" first.");
            return;
        }
        try {
            playlistLogic.moveSongUp(playlist, index);
            loadPlaylists();
            songsInPlaylist.getSelectionModel().select(Math.max(index - 1, 0));   // keep the moved song selected
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void btnSongToPlaylist(ActionEvent actionEvent) {
        Playlist playlist = playlistList.getSelectionModel().getSelectedItem();
        Song song = songsList.getSelectionModel().getSelectedItem();
        if (playlist == null || song == null) {
            showError("Select a playlist on the left and a song on the right first.");
            return;
        }
        try {
            playlistLogic.addSongToPlaylist(playlist, song);
            loadPlaylists();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void btnDelSongsClicked(ActionEvent actionEvent) {
        Song song = songsList.getSelectionModel().getSelectedItem();
        if (song == null) {
            showError("Select a song first.");
            return;
        }
        if (!showConfirmation("Delete \"" + song.getTitle() + "\"?\nIt will also be removed from all playlists.")) {
            return;
        }
        try {
            songLogic.deleteSong(song);
            loadSongs();
            loadPlaylists();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void btnDelPlaylstClicked(ActionEvent actionEvent) {
        Playlist playlist = playlistList.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            showError("Select a playlist first.");
            return;
        }
        if (!showConfirmation("Delete the playlist \"" + playlist.getName() + "\"?")) {
            return;
        }
        try {
            playlistLogic.deletePlaylist(playlist);
            loadPlaylists();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void songsInPlaylistClicked(MouseEvent event) {
        lastClickedSongTable = songsInPlaylist;
        if (event.getClickCount() == 2) {
            playClickedSong();
        }
    }

    @FXML
    private void songsListClicked(MouseEvent event) {
        lastClickedSongTable = songsList;
        if (event.getClickCount() == 2) {
            playClickedSong();
        }
    }

    @FXML
    private void btnDownClicked(ActionEvent actionEvent) {
        Playlist playlist = playlistList.getSelectionModel().getSelectedItem();
        int index = songsInPlaylist.getSelectionModel().getSelectedIndex();
        if (playlist == null || index < 0) {
            showError("Select a song in \"Songs in playlist\" first.");
            return;
        }
        try {
            playlistLogic.moveSongDown(playlist, index);
            loadPlaylists();
            int lastIndex = songsInPlaylist.getItems().size() - 1;
            songsInPlaylist.getSelectionModel().select(Math.min(index + 1, lastIndex));   // keep the moved song selected
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void btnSearchClicked(ActionEvent actionEvent) {
        try {
            loadSongs();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
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
        Playlist playlist = playlistList.getSelectionModel().getSelectedItem();
        if (playlist == null) {
            showError("Select a playlist first.");
            return;
        }
        if (!showConfirmation("Delete the playlist \"" + playlist.getName() + "\"?")) {
            return;
        }
        try {
            playlistLogic.deletePlaylist(playlist);
            loadPlaylists();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void btnEditSongsClicked(ActionEvent actionEvent) {
        Song song = songsList.getSelectionModel().getSelectedItem();
        if (song == null) {
            showError("Select a song first.");
            return;
        }
        openSongWindow(WindowMode.edit);
    }

    private boolean showConfirmation(String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("MyTunes");
        alert.setHeaderText(null);
        alert.setContentText(question);
        Optional<ButtonType> answer = alert.showAndWait();
        return answer.isPresent() && answer.get() == ButtonType.OK;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("MyTunes");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updatePlayerInfo() {
        Song song = musicPlayer.getCurrentSong();
        if (song == null) {
            songLbl.setText("Song");
            artistLbl.setText("Artist");
        } else {
            songLbl.setText(song.getTitle());
            artistLbl.setText(song.getArtist());
        }

        // Show ⏸ and spin the vinyl while music plays
        if (musicPlayer.isPlaying()) {
            btnPlay.setText("⏸");
            rotation.play();
        } else {
            btnPlay.setText("▶");
            rotation.pause();
        }

        double currentTime = musicPlayer.getCurrentTime();
        songTime.setText(Song.formatTime((int) currentTime));

        // Move the slider - but not while you are dragging it
        if (!timeSlider.isPressed() && !timeSlider.isValueChanging()) {
            timeSlider.setMax(musicPlayer.getTotalTime());
            timeSlider.setValue(currentTime);
        }
    }

    private void loadPlaylists() throws MyTunesException {
        Playlist selected = playlistList.getSelectionModel().getSelectedItem();
        playlistList.getItems().setAll(playlistLogic.getAllPlaylists());

        if (selected != null) {
            for (Playlist playlist : playlistList.getItems()) {
                if (playlist.getId() == selected.getId()) {
                    playlistList.getSelectionModel().select(playlist);
                }
            }
        }
    }
    private void loadSongs() throws MyTunesException {
        songsList.getItems().setAll(songLogic.searchSongs(filterFieldInput.getText()));
    }

    private void playClickedSong() {
        try {
            playSelectedSong();
        } catch (MyTunesException e) {
            showError(e.getMessage());
        }
        updatePlayerInfo();
    }
}
