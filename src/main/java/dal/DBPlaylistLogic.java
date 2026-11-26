package dal;

import be.MyTunesException;
import be.Playlist;
import be.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPlaylistLogic {


    // Gets all playlists, each with its songs in the right order
    public List<Playlist> getAllPlaylists() throws MyTunesException {
        List<Playlist> playlists = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection()) {
            // 1. Get the playlists
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Playlists ORDER BY Name");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                playlists.add(new Playlist(result.getInt("id"), result.getString("Name")));
            }

            // 2. Get the songs on each playlist
            for (Playlist playlist : playlists) {
                playlist.getSongs().addAll(getSongsInPlaylist(connection, playlist.getId()));
            }
        } catch (SQLException e) {
            throw new MyTunesException("Could not load the playlists.\n" + e.getMessage());
        }
        return playlists;
    }

    // Gets the songs on one playlist, sorted by their position
    private List<Song> getSongsInPlaylist(Connection connection, int playlistId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.* FROM PlaylistSongs " + "JOIN Songs ON Songs.id = PlaylistSongs.songId "
                + "WHERE PlaylistSongs.playlistId = ? " + "ORDER BY PlaylistSongs.position";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, playlistId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            songs.add(DBSongLogic.makeSong(result));
        }
        return songs;
    }

    // Makes a new, empty playlist
    public void createPlaylist(String name) throws MyTunesException {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Playlists (Name) VALUES (?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not create the playlist.\n" + e.getMessage());
        }
    }

    // Saves a new name for the playlist
    public void updatePlaylist(Playlist playlist) throws MyTunesException {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Playlists SET Name = ? WHERE id = ?");
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not rename the playlist.\n" + e.getMessage());
        }
    }

    // Deletes a playlist. Its songs are only taken off the playlist - they are not deleted.
    public void deletePlaylist(Playlist playlist) throws MyTunesException {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement removeSongs = connection.prepareStatement("DELETE FROM PlaylistSongs WHERE playlistId = ?");
            removeSongs.setInt(1, playlist.getId());
            removeSongs.executeUpdate();

            PreparedStatement deletePlaylist = connection.prepareStatement("DELETE FROM Playlists WHERE id = ?");
            deletePlaylist.setInt(1, playlist.getId());
            deletePlaylist.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not delete the playlist.\n" + e.getMessage());
        }
    }

    // Puts a song on a playlist at the given position
    public void addSongToPlaylist(Playlist playlist, Song song, int position) throws MyTunesException {
        String sql = "INSERT INTO PlaylistSongs (playlistId, songId, position) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlist.getId());
            statement.setInt(2, song.getId());
            statement.setInt(3, position);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not add the song to the playlist.\n" + e.getMessage());
        }
    }

    // Takes a song off a playlist
    public void removeSongFromPlaylist(Playlist playlist, Song song) throws MyTunesException {
        String sql = "DELETE FROM PlaylistSongs WHERE playlistId = ? AND songId = ?";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlist.getId());
            statement.setInt(2, song.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new MyTunesException("Could not remove the song from the playlist.\n" + e.getMessage());
        }
    }

    public void savePositions(Playlist playlist) throws MyTunesException {
        String sql = "UPDATE PlaylistSongs SET position = ? WHERE playlistId = ? AND songId = ?";

        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            List<Song> songs = playlist.getSongs();
            for (int i = 0; i < songs.size(); i++) {
                statement.setInt(1, i + 1);
                statement.setInt(2, playlist.getId());
                statement.setInt(3, songs.get(i).getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MyTunesException("Could not save the order of the songs.\n" + e.getMessage());
        }
    }
}
