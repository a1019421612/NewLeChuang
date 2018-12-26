package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 音源
 *
 * @author hult
 */
public enum MediaSource {
    LOCAL("local"), BLUETOOTH("bluetooth"), LINE_IN("linein"),ALINK("alink");

    private final String state;

    MediaSource(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static MediaSource code(String name) {
        MediaSource audioOutput;
        switch (name) {
            case "local":
                audioOutput = LOCAL;
                break;
            case "bluetooth":
                audioOutput = BLUETOOTH;
                break;
            case "alink":
                audioOutput = ALINK;
                break;
            case "linein":
            default:
                audioOutput = LINE_IN;
        }
        return audioOutput;
    }

    public static MediaSource convert(String name) {
        MediaSource audioOutput;
        switch (name) {
            case "本地":
                audioOutput = LOCAL;
                break;
            case "蓝牙":
                audioOutput = BLUETOOTH;
                break;
            case "云端":
                audioOutput = ALINK;
                break;
            case "外音":
            default:
                audioOutput = LINE_IN;
        }
        return audioOutput;
    }

    public static MediaSource code(int source) {
        switch (source) {
            case 1:
                return LOCAL;
            case 2:
                return LINE_IN;
            case 3:
                return BLUETOOTH;
            default:
                return LOCAL;
        }
    }

    public static int code(MediaSource source) {
        switch (source) {
            case LOCAL:
                return 1;
            case LINE_IN:
                return 2;
            case BLUETOOTH:
                return 3;
            default:
                return 1;
        }
    }

    public static String convert(MediaSource source) {
        switch (source) {
            case LOCAL:
                return "本地";
            case LINE_IN:
                return "外音";
            case BLUETOOTH:
                return "蓝牙";
            case ALINK:
                return "云端";
            default:
                return "本地";
        }
    }
}
