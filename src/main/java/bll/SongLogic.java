package bll;

import be.MyTunesException;
import be.Song;
import dal.DBSongLogic;

import java.util.ArrayList;
import java.util.List;

public class SongLogic {
    private final DBSongLogic dbSongLogic = new DBSongLogic();

    public List<Song> getAllSongs() throws MyTunesException {
        return dbSongLogic.getAllSongs();
    }

    // Finds the songs where the title or the artist contains the search text.
    // Upper/lower case doesn't matter. Empty search text = all songs.
    public List<Song> searchSongs(String searchText) throws MyTunesException {
        List<Song> allSongs = dbSongLogic.getAllSongs();
        String search = searchText.trim().toLowerCase();

        if (search.isEmpty()) {
            return allSongs;
        }

        List<Song> result = new ArrayList<>();
        for (Song song : allSongs) {
            String title = song.getTitle().toLowerCase();
            String artist = song.getArtist().toLowerCase();
            if (title.contains(search) || artist.contains(search)) {
                result.add(song);
            }
        }
        return result;
    }

    public void createSong(Song song) throws MyTunesException {
        checkSong(song);
        dbSongLogic.createSong(song);
    }

    public void updateSong(Song song) throws MyTunesException {
        checkSong(song);
        dbSongLogic.updateSong(song);
    }

    public void deleteSong(Song song) throws MyTunesException {
        dbSongLogic.deleteSong(song);
    }

    // The rules a song must follow before we save it
    private void checkSong(Song song) throws MyTunesException {
        if (song.getTitle().isEmpty()) {
            throw new MyTunesException("Please enter a title.");
        }
        if (song.getArtist().isEmpty()) {
            throw new MyTunesException("Please enter an artist.");
        }
        if (song.getFilePath().isEmpty()) {
            throw new MyTunesException("Please choose a music file.");
        }
    }
}
