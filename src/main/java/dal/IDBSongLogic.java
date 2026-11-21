package dal;

import java.sql.SQLException;

public interface IDBSongLogic {
    void addSong() throws SQLException;

    void removeSong() throws SQLException;

    void getAllSongs() throws SQLException;
}
