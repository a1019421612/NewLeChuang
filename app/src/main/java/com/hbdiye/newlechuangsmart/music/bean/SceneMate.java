package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 情景 Id 补位后的
 *
 * @author EthanCo
 * @since 2017/11/14
 */
public class SceneMate {
    private String name;
    private int sceId;
    private String deviceId;
    private boolean open;
    private boolean editMode;

    public SceneMate() {
    }

    public SceneMate(String name, boolean open) {
        this.name = name;
        this.open = open;
    }

    public SceneMate(String deviceId, String name, boolean open) {
        this.deviceId = deviceId;
        this.name = name;
        this.open = open;
    }


    public SceneMate(String deviceId, String name, boolean open, int sceId) {
        this.deviceId = deviceId;
        this.name = name;
        this.open = open;
        this.sceId = sceId;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", open=" + open +
                ", editMode=" + editMode +
                '}';
    }
}
