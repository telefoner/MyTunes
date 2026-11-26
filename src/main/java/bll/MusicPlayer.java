package bll;

import be.MyTunesException;
import be.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * BLL (business logic layer): plays the music.
 * It remembers a list of songs (a playlist or all songs) and which song in that list is playing,
 * so it knows what the next and previous songs are.
 */
public class MusicPlayer {

    private MediaPlayer mediaPlayer;              // JavaFX's player for the current song (null = no song loaded)
    private List<Song> songs = new ArrayList<>(); // the songs we are playing
    private int currentIndex = 0;                 // which song in the list is the current one
    private boolean playing = false;

    // Starts playing the list of songs, beginning with the song at startIndex
    public void playSongs(List<Song> songs, int startIndex) throws MyTunesException {
        this.songs = new ArrayList<>(songs);   // our own copy of the list
        currentIndex = startIndex;
        playCurrentSong();
    }

    public void next() throws MyTunesException {
        if (songs.isEmpty()) {
            return;
        }
        currentIndex = currentIndex + 1;
        if (currentIndex >= songs.size()) {
            currentIndex = 0;   // after the last song we start from the top again
        }
        playCurrentSong();
    }

    public void previous() throws MyTunesException {
        if (songs.isEmpty()) {
            return;
        }
        currentIndex = currentIndex - 1;
        if (currentIndex < 0) {
            currentIndex = songs.size() - 1;   // before the first song comes the last song
        }
        playCurrentSong();
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playing = false;
        }
    }

    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            playing = true;
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    // true when a song is loaded (playing or paused)
    public boolean hasSong() {
        return mediaPlayer != null;
    }

    // The song that is playing (or paused), or null if there is none
    public Song getCurrentSong() {
        if (mediaPlayer == null) {
            return null;
        }
        return songs.get(currentIndex);
    }

    // How far we are in the song, in seconds
    public double getCurrentTime() {
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    // How long the song is, in seconds
    public double getTotalTime() {
        if (mediaPlayer == null) {
            return 0;
        }
        Duration total = mediaPlayer.getTotalDuration();
        if (total == null || total.isUnknown()) {
            return getCurrentSong().getDurationSeconds();   // file not read yet: use the time from the database
        }
        return total.toSeconds();
    }

    // Jumps to a time in the song (used by the slider)
    public void seek(double seconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(seconds));
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();   // frees the memory used by the old song
            mediaPlayer = null;
        }
        playing = false;
    }

    private void playCurrentSong() throws MyTunesException {
        stop();   // stop the song that is playing now (if there is one)

        Song song = songs.get(currentIndex);
        File file = new File(song.getFilePath());
        if (!file.exists()) {
            throw new MyTunesException("Can't find the music file for \"" + song.getTitle() + "\":\n" + song.getFilePath());
        }

        try {
            mediaPlayer = new MediaPlayer(new Media(file.toURI().toString()));
        } catch (MediaException e) {
            throw new MyTunesException("Can't play this file:\n" + song.getFilePath());
        }

        mediaPlayer.setOnEndOfMedia(() -> playNextAfterSongEnds());   // when the song is over, play the next one
        mediaPlayer.play();
        playing = true;
    }

    private void playNextAfterSongEnds() {
        try {
            next();
        } catch (MyTunesException e) {
            stop();   // the next song could not be played, so we stop
        }
    }
}
