package com.example.user.projectbringback;

public class Music {
    private String name;
    private String singer;
    private String album;
    private String genre;
    private String date;
    private int track_num;

    public Music(){}

    public Music (String name, String singer, String album, String genre, String date, int track_num){
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.genre = genre;
        this.date = date;
        this.track_num = track_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTrack_num() {
        return track_num;
    }

    public void setTrack_num(int track_num) {
        this.track_num = track_num;
    }
}
