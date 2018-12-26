package com.hbdiye.newlechuangsmart.music.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hbdiye.newlechuangsmart.music.bean.MediaSource;
import com.hbdiye.newlechuangsmart.music.bean.PlayMode;
import com.hbdiye.newlechuangsmart.music.bean.SoundEffect;
import com.lib.smartlib.tcp.utils.ByteUtils;


/**
 * 作者：kelingqiu on 17/12/7 10:13
 * 邮箱：42747487@qq.com
 */
public class TcpRequestFactory {
    /**
     * 提供 播放请求
     *
     * @param deviceId 被控端id
     */
    public static String playRequest(long deviceId) throws Exception
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("status", 1);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 暂停请求
     *
     * @param deviceId 被控端id
     */
    public static String pauseRequest(Long deviceId) throws Exception
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("status", 0);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 指定播放请求
     *
     * @param deviceId 被控端id
     * @param id       歌曲id
     */
    public static String playWithIdRequest(Long deviceId, int id) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("play", id);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 下一曲请求
     *
     * @param deviceId 被控端id
     */
    public static String nextRequest(Long deviceId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("control", 1);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 上一曲请求
     *
     * @param deviceId 被控端id
     */
    public static String prevRequest(Long deviceId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("control", 0);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 拖动进度条请求
     *
     * @param deviceId 被控端id
     * @param progress 进度条时间
     */
    public static String skinProgressRequest(Long deviceId, Long progress) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("skip", progress);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 增大音量请求
     *
     * @param deviceId 被控端id
     */
    public static String inVolRequest(Long deviceId) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("idvol", 1);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 减小音量请求
     *
     * @param deviceId 被控端id
     */
    public static String deVolRequest(Long deviceId) throws Exception
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("idvol", 0);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 静音请求
     *
     * @param deviceId 被控端id
     * @param isMute   True 静音 False 取消静音
     */
    public static String setMuteRequest(Long deviceId, Boolean isMute) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("mute", isMute);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 设置音量请求
     *
     * @param deviceId 被控端id
     * @param vol      音量大小
     */
    public static String setVolRequest(Long deviceId, int vol) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("setvol", vol);
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 设置播放音源请求
     *
     * @param deviceId    被控端id
     * @param mediaSource 音源类型
     */
    public static String setSourceRequest(Long deviceId, MediaSource mediaSource) throws Exception
    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("source", MediaSource.code(mediaSource));
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 设置音效请求
     *
     * @param deviceId    被控端id
     * @param soundEffect 音效类型
     */
    public static String setSoundEffectRequest(Long deviceId, SoundEffect soundEffect) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("effect", SoundEffect.code(soundEffect));
        jo.add("profile", profile);
        return jo.toString();
    }

    /**
     * 提供 设置播放模式请求
     *
     * @param deviceId 被控端id
     * @param playMode 播放模式
     */
    public static String setPlayModeRequest(Long deviceId, PlayMode playMode) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        JsonObject profile = new JsonObject();
        profile.addProperty("model", PlayMode.code(playMode));
        jo.add("profile", profile);
        return jo.toString();
    }

    public static String getSceneRequest(int sceType, int sceBell, int sceShe, String sceMus, int sceRate, String sceValue, String sceDate, String sceTime)

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("sceType", sceType);
        jo.addProperty("sceBell", sceBell);
        jo.addProperty("sceShe", sceShe);
        jo.addProperty("sceMus", sceMus);
        jo.addProperty("sceRate", sceRate);
        jo.addProperty("sceValue", sceValue);
        jo.addProperty("sceDate", sceDate);
        jo.addProperty("sceTime", sceTime);
        return jo.toString();
    }

    public static String createPlaylistRequest(long deviceId, String shtNm) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("operate", 1);
        jo.addProperty("shtNm", shtNm);
        return jo.toString();
    }

    public static String delPlaylistRequest(long deviceId, String shtIds) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("shtIds", shtIds);
        return jo.toString();
    }

    public static String addSongToList(long deviceId, int shtId, String musId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("shtId", shtId);
        jo.addProperty("musId", musId);
        return jo.toString();
    }

    public static String delSongFromList(long deviceId, int shtId, String musId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("shtId", shtId);
        jo.addProperty("musId", musId);
        return jo.toString();
    }

    public static String songCollect(long deviceId, int operate, String musId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("operate", operate);
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("musId", musId);
        return jo.toString();
    }

    public static String editPlaylistRequest(long deviceId, int shtId, String shtNm) throws Exception

    {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("operate", 0);
        jo.addProperty("shtId", shtId);
        jo.addProperty("shtNm", shtNm);
        return jo.toString();
    }

    public static String getPartionStatus(long deviceId) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        return jo.toString();
    }

    public static String setPartion(long deviceId, int chId, boolean chEn) throws Exception {
        JsonObject jo = new JsonObject();
        jo.addProperty("devId", ByteUtils.encodePrimary(deviceId));
        jo.addProperty("chId", chId);
        jo.addProperty("chEn", chEn);
        return jo.toString();
    }

    public static String createScene(String sceNm, int sceImg, JsonElement sceConf){
        JsonObject jo = new JsonObject();
        jo.addProperty("operate", 1);
        jo.addProperty("sceNm", sceNm);
        jo.addProperty("sceImg", sceImg);
        jo.add("sceConf", sceConf);
        return jo.toString();
    }

    public static String editScene(int sceneId, String sceNm, int sceImg, JsonElement sceConf){
        JsonObject jo = new JsonObject();
        jo.addProperty("operate", 0);
        jo.addProperty("sceneId", sceneId);
        jo.addProperty("sceNm", sceNm);
        jo.addProperty("sceImg", sceImg);
        jo.add("sceConf", sceConf);
        return jo.toString();
    }

    public static String delScene(String sceneId){
        JsonObject jo = new JsonObject();
        jo.addProperty("sceneIds", sceneId);
        return jo.toString();
    }
}