package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private final List<Song> songs = new ArrayList<>();


    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist(String name){
        this(0,name);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<Song> getSongs(){
        return songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongCount(){
        return songs.size();
    }
}
