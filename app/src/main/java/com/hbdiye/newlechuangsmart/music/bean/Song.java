package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 歌曲
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 4:01 PM
 * Desc: Song
 */
public class Song {
    private String id = "0";

    private String title;

    private String coverUrl;

    private String artist;

    private String album;

    private boolean favorite;

    private int duration;

    public Song(String id, String title, String coverUrl, String artist, int duration) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.artist = artist;
        this.duration = duration;
    }

    public Song(String id, String title, String coverUrl, String artist, String album, int duration) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public Song(String id, String title, String coverUrl, String artist, String album, int duration, boolean favorite) {
        this.id = id;
        this.title = title;
        this.coverUrl = coverUrl;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SongA{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", favorite=" + favorite +
                ", duration=" + duration +
                '}';
    }
}
