package com.example.user.projectbringback.data;

public class Playlist {
    private String name;
    private int numberOfSong;
    private String image;


    public Playlist(String name, int numberOfSong, String image) {
        this.name = name;
        this.numberOfSong = numberOfSong;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSong() {
        return numberOfSong;
    }

    public void setNumberOfSong(int numberOfSong) {
        this.numberOfSong = numberOfSong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
