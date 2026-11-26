package dal;

import be.MyTunesException;
import be.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAL (data access layer) for songs.
 * All the SQL for the Songs table is in this class.
 */
public class DBSongLogic {

    private final DBConnector DBConnector = new DBConnector();

    // Gets all songs from the database
    public List<Song> getAllSongs() throws MyTunesException {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM Songs ORDER BY title";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                songs.add(makeSong(result));
            }
        } catch (SQLException e) {
            throw new MyTunesException("Could not load the songs.\n" + e.getMessage());
        }
        return songs;
    }

    // Saves a new song. The database gives it an id, which we put on the song.
    public void createSong(Song song) throws MyTunesException {
        String sql = "INSERT INTO Songs (title, artist, genre, duration, filePath) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setInt(4, song.getDurationSeconds());
            statement.setString(5, song.getFilePath());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                song.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new MyTunesException("Could not save the song.\n" + e.getMessage());
        }
    }

    // Saves the changes to a song that is already in the database
    public void updateSong(Song song) throws MyTunesException {
        String sql = "UPDATE Songs SET title = ?, artist = ?, genre = ?, duration = ?, filePath = ? WHERE id = ?";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setInt(4, song.getDurationSeconds());
            statement.setString(5, song.getFilePath());
            statement.setInt(6, song.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not save the changes to the song.\n" + e.getMessage());
        }
    }

    // Deletes a song. First we take it off every playlist, because PlaylistSongs points to it.
    public void deleteSong(Song song) throws MyTunesException {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement removeFromPlaylists = connection.prepareStatement("DELETE FROM PlaylistSongs WHERE songId = ?");
            removeFromPlaylists.setInt(1, song.getId());
            removeFromPlaylists.executeUpdate();

            PreparedStatement deleteSong = connection.prepareStatement("DELETE FROM Songs WHERE id = ?");
            deleteSong.setInt(1, song.getId());
            deleteSong.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not delete the song.\n" + e.getMessage());
        }
    }

    // Turns one row from the database into a Song object.
    // DBPlaylistLogic uses this too, when it reads the songs on a playlist.
    public static Song makeSong(ResultSet row) throws SQLException {
        return new Song(
                row.getInt("id"),
                textOrEmpty(row.getString("title")),
                textOrEmpty(row.getString("artist")),
                textOrEmpty(row.getString("genre")),
                row.getInt("duration"),
                textOrEmpty(row.getString("filePath")));
    }

    // An empty column (NULL) in the database becomes "" in Java
    private static String textOrEmpty(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }
}
