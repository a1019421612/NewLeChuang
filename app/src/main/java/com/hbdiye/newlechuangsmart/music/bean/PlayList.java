package com.hbdiye.newlechuangsmart.music.bean;

import java.util.List;

/**
 * 歌单、播放列表
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 5:53 PM
 * Desc: PlayList
 */
public class PlayList {

    private String id;
    private String name;
    private Boolean favorite;
    private Boolean isScene;
    private List<Song> songs;
    private Boolean autoplay;

    public PlayList() {
    }
    
    public PlayList(String name) {
        this.name = name;
    }

    /**
     * 旧播放器以name为唯一标识，新播放器以id为唯一标识
     *
     * @param id
     * @param name
     */
    public PlayList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayList(String id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public PlayList(String id, String name, Boolean favorite, Boolean isScene) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
        this.isScene = isScene;
    }

    public PlayList(String id, String name, Boolean favorite, Boolean isScene, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
        this.isScene = isScene;
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getScene() {
        return isScene;
    }

    public void setScene(Boolean scene) {
        isScene = scene;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Boolean getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(Boolean autoplay) {
        this.autoplay = autoplay;
    }
}
