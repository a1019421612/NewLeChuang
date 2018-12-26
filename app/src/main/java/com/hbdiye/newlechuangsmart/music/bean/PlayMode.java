package com.hbdiye.newlechuangsmart.music.bean;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 5:48 PM
 * Desc: PlayMode
 */
public enum PlayMode {
    SINGLE("single"), //单曲
    CYCLE("cycle"), //列表循环
    LIST("list"), //顺序播放
    RANDOM("random"); //随机

    private final String state;

    PlayMode(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static PlayMode code(String name) {
        PlayMode playMode;
        switch (name) {
            case "random":
                playMode = RANDOM;
                break;
            case "single":
                playMode = SINGLE;
                break;
            case "list":
                playMode = LIST;
                break;
            case "cycle":
            default:
                playMode = CYCLE;
        }
        return playMode;
    }

    public static PlayMode convert(String name) {
        PlayMode playMode;
        switch (name) {
            case "随机":
                playMode = RANDOM;
                break;
            case "单曲":
                playMode = SINGLE;
                break;
            case "循环":
            default:
                playMode = CYCLE;
        }
        return playMode;
    }

    public static PlayMode getDefault() {
        return CYCLE;
    }

    public static PlayMode switchNextMode(PlayMode current) {
        if (current == null) return getDefault();

        switch (current) {
            case CYCLE:
//                return LIST;
//            case LIST:
                return RANDOM;
            case RANDOM:
                return SINGLE;
            case SINGLE:
                return CYCLE;
        }
        return getDefault();
    }

    public static PlayMode code(int name) {
        PlayMode playMode;
        switch (name) {
            case 1:
                playMode = RANDOM;
                break;
            case 2:
                playMode = CYCLE;
                break;
            case 3:
                playMode = SINGLE;
                break;
            case 4:
            default:
                playMode = LIST;
        }
        return playMode;
    }

    public static int code(PlayMode playMode) {
        switch (playMode) {
            case RANDOM:
                return 1;
            case CYCLE:
                return 2;
            case SINGLE:
                return 3;
            case LIST:
            default:
                return 4;

        }
    }
}
