package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String genre;
    private int durationSeconds;
    private String filePath;

    public Song(int id, String title, String artist, String genre, int durationSeconds, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
        this.filePath = filePath;
    }

    public Song(String title, String artist, String category,
                int durationSeconds, String filePath){
        this(0, title, artist, category, durationSeconds, filePath);
    }

    public int getId(){
        return id;
    }

    public void setId( int id ){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist( String artist ){
        this.artist = artist;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre( String genre ){
        this.genre = genre;
    }

    public int getDurationSeconds(){
        return durationSeconds;
    }

    public void setDurationSeconds( int durationSeconds ){
        this.durationSeconds = durationSeconds;
    }

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath( String filePath ){
        this.filePath = filePath;
    }

    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int restSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, restSeconds);
    }






}
