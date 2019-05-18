package com.example.mediaplayer.model;

public class Music {

    private String idsong;
    private String namesong;
    private String artist;
    private String album;
    private Boolean favorite;
    private String path;

    public Music() {
    }

    public Music(String idsong, String namesong, String artist, String album, Boolean favorite, String path) {
        this.idsong = idsong;
        this.namesong = namesong;
        this.artist = artist;
        this.album = album;
        this.favorite = favorite;
        this.path = path;
    }

    public String getIdsong() {
        return idsong;
    }

    public void setIdsong(String idsong) {
        this.idsong = idsong;
    }

    public String getNamesong() {
        return namesong;
    }

    public void setNamesong(String namesong) {
        this.namesong = namesong;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
