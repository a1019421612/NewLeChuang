package com.hbdiye.newlechuangsmart.music.bean;


/**
 * 情景
 *
 * @author EthanCo
 * @since 2017/11/14
 */
public class Scene {
    public static final int ID_OPEN_ALL = -1;  //打开全部
    public static final int ID_CLOSE_ALL = -2; //关闭全部

    public static Scene OPEN_ALL_SCENE = new Scene(
            -1, "打开全部", true, ID_OPEN_ALL, ID_OPEN_ALL);
    public static Scene CLOSE_ALL_SCENE = new Scene(
            -1, "关闭全部", true, ID_CLOSE_ALL, ID_CLOSE_ALL);

    private String name;
    private long sceneId;
    private long deviceId;
    private boolean open;
    private boolean editMode;
    private int sceneIcon;
    private int sceId;
//    public ObservableBoolean openOB = new ObservableBoolean();

    public int getSceId() {
        return sceId;
    }

    public void setSceId(int sceId) {
        this.sceId = sceId;
    }

    public int getSceneIcon() {
        return sceneIcon;
    }

    public void setSceneIcon(int sceneIcon) {
        this.sceneIcon = sceneIcon;
    }

    public Scene() {
    }

    public Scene(String name, boolean enable) {
        this.name = name;
        setOpen(enable);
    }

    public Scene(long deviceId, String name, boolean enable) {
        this.name = name;
        this.deviceId = deviceId;
        setOpen(enable);
    }

    public Scene(long deviceId, String name, boolean enable, long sceneId) {
        this.name = name;
        this.sceneId = sceneId;
        this.deviceId = deviceId;
        setOpen(enable);
    }

    public Scene(long deviceId, String name, boolean enable, long sceneId, int sceneIcon) {
        this.name = name;
        this.sceneId = sceneId;
        this.deviceId = deviceId;
        this.sceneIcon = sceneIcon;
        setOpen(enable);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
//        this.openOB.set(open);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", sceneId=" + sceneId +
                ", deviceId=" + deviceId +
                ", open=" + open +
                ", editMode=" + editMode +
                ", sceneIcon=" + sceneIcon +
                ", sceId=" + sceId +
                '}';
    }
}
