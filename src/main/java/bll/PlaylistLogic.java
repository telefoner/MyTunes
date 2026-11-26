package bll;

import be.MyTunesException;
import be.Playlist;
import be.Song;
import dal.DBPlaylistLogic;

import java.util.Collections;
import java.util.List;

public class PlaylistLogic {
    private DBPlaylistLogic dbPlaylistLogic = new DBPlaylistLogic();

    public void moveSongDown(Playlist playlist, int index) throws MyTunesException {
        if (index < 0 || index >= playlist.getSongs().size() - 1) {
            return;   // the last song can't go further down
        }
        Collections.swap(playlist.getSongs(), index, index + 1);
        dbPlaylistLogic.savePositions(playlist);
    }

    public void moveSongUp(Playlist playlist, int index) throws MyTunesException {
        if (index <= 0) {
            return;   // the first song can't go further up
        }
        Collections.swap(playlist.getSongs(), index, index - 1);
        dbPlaylistLogic.savePositions(playlist);
    }

    public List<Playlist> getAllPlaylists() throws MyTunesException {
        return dbPlaylistLogic.getAllPlaylists();
    }

    public void createPlaylist(String name) throws MyTunesException {
        checkName(name);
        dbPlaylistLogic.createPlaylist(name.trim());
    }

    public void renamePlaylist(Playlist playlist, String newName) throws MyTunesException {
        checkName(newName);
        playlist.setName(newName.trim());
        dbPlaylistLogic.updatePlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist) throws MyTunesException {
        dbPlaylistLogic.deletePlaylist(playlist);
    }

    // Adds a song to the end of a playlist. A song can only be on the same playlist once.
    public void addSongToPlaylist(Playlist playlist, Song song) throws MyTunesException {
        for (Song songOnPlaylist : playlist.getSongs()) {
            if (songOnPlaylist.getId() == song.getId()) {
                throw new MyTunesException("\"" + song.getTitle() + "\" is already on " + playlist.getName() + ".");
            }
        }
        playlist.getSongs().add(song);
        dbPlaylistLogic.addSongToPlaylist(playlist, song, playlist.getSongs().size());

        // Number the songs 1, 2, 3 ... again, so the order is always right
        // (there can be gaps in the numbers after a song has been deleted)
        dbPlaylistLogic.savePositions(playlist);
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song) throws MyTunesException {
        playlist.getSongs().remove(song);
        dbPlaylistLogic.removeSongFromPlaylist(playlist, song);
    }

    private void checkName(String name) throws MyTunesException {
        if (name == null || name.isEmpty()) {
            throw new MyTunesException("Please enter a name for the playlist.");
        }
    }
}
