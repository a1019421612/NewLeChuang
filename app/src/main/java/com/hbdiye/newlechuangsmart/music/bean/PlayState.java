package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 作者：kelingqiu on 16/11/9 11:15
 * 邮箱：42747487@qq.com
 */

public enum PlayState {
    //PREV("prev"),
    //NEXT("next"),
    PLAY("play"),
    PAUSE("pause");
    //STOP("stop");

    private final String state;

    PlayState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    /*public static PlayStateA code(String state) {
        switch (state) {
            case "prev":
                return PREV;
            case "next":
                return NEXT;
            case "play":
                return PLAY;
            case "pause":
                return PAUSE;
            default:
                return STOP;
        }
    }

    public static PlayStateA code(int status) {
        PlayStateA result;
        switch (status) {
            case 0:
                //停止当做暂停进行处理
                result = PlayStateA.PAUSE;
                //result = PlayStateA.STOP;
                break;
            case 1:
                result = PlayStateA.PAUSE;
                break;
            case 2:
            default:
                result = PlayStateA.PLAY;
        }
        return result;
    }*/
}
