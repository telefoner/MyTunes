package dal;

import java.sql.SQLException;

public interface IDBPlaylistLogic {

    void createPlaylist() throws SQLException;

    void deletePlaylist() throws SQLException;

    void addSongToPlaylist() throws SQLException;

    void removeSongFromPlaylist() throws SQLException;

    void getAllPlaylists() throws SQLException;

    void getSongsInPlaylist() throws SQLException;

    void updatePlaylist() throws SQLException;




}
