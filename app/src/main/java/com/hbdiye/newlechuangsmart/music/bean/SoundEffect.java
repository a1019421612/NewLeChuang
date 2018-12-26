package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 音效
 *
 * @author hult
 */
public enum SoundEffect {
    CLASSICAL("classical"), //古典
    MODERN("modern"), //现代
    ROCKROLL("rockroll"), //摇滚
    POP("pop"), //流行
    DANCE("dance"), //舞曲
    ORIGINAL("original"); //原声

    private final String state;

    SoundEffect(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static SoundEffect code(String name) {
        SoundEffect soundEffect;
        switch (name) {
            case "classical":
                soundEffect = CLASSICAL;
                break;
            case "modern":
                soundEffect = MODERN;
                break;
            case "rockroll":
                soundEffect = ROCKROLL;
                break;
            case "pop":
                soundEffect = POP;
                break;
            case "dance":
                soundEffect = DANCE;
                break;
            case "original":
            default:
                soundEffect = ORIGINAL;
        }
        return soundEffect;
    }

    public static SoundEffect code(int effect) {
        switch (effect) {
            case 0:
                return CLASSICAL;
            case 1:
                return MODERN;
            case 2:
                return ROCKROLL;
            case 3:
                return POP;
            case 4:
                return DANCE;
            case 5:
            default:
                return ORIGINAL;
        }
    }

    public static int code(SoundEffect effect) {
        switch (effect) {
            case CLASSICAL:
                return 0;
            case MODERN:
                return 1;
            case ROCKROLL:
                return 2;
            case POP:
                return 3;
            case DANCE:
                return 4;
            case ORIGINAL:
            default:
                return 5;

        }
    }

    public static String convert(SoundEffect effect) {
        switch (effect) {
            case CLASSICAL:
                return "古典";
            case MODERN:
                return "现代";
            case ROCKROLL:
                return "摇滚";
            case POP:
                return "流行";
            case DANCE:
                return "舞曲";
            case ORIGINAL:
            default:
                return "原声";

        }
    }

    public static SoundEffect convert(String name) {
        SoundEffect soundEffect;
        switch (name) {
            case "古典":
                soundEffect = CLASSICAL;
                break;
            case "现代":
                soundEffect = MODERN;
                break;
            case "摇滚":
                soundEffect = ROCKROLL;
                break;
            case "流行":
                soundEffect = POP;
                break;
            case "舞曲":
                soundEffect = DANCE;
                break;
            case "原声":
            default:
                soundEffect = ORIGINAL;
        }
        return soundEffect;
    }
}
