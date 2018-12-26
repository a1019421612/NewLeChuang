package com.hbdiye.newlechuangsmart.music.bean;

/**
 * 作者：kelingqiu on 18/3/19 20:20
 * 邮箱：42747487@qq.com
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 系统铃声列表
 *
 * @author EthanCo
 * @since 2017/12/26
 */

public class SystemAlarmList {
    private SystemAlarmList() {
        systemAlarms.add("Argon.ogg");
        systemAlarms.add("Callisto.ogg");
        systemAlarms.add("Carbon.ogg");
        systemAlarms.add("Dione.ogg");
        systemAlarms.add("Ganymede.ogg");
        systemAlarms.add("Helium.ogg");
        systemAlarms.add("Luna.ogg");
        systemAlarms.add("Neon.ogg");
        systemAlarms.add("Phobos.ogg");
        systemAlarms.add("Platinum.ogg");
        systemAlarms.add("RobotsforEveryone.ogg");
        systemAlarms.add("Sedna.ogg");
        systemAlarms.add("SpagnolaOrchestration.ogg");
        systemAlarms.add("Titania.ogg");
        systemAlarms.add("Triton.ogg");
        systemAlarms.add("Umbriel.ogg");
    }

    private static class SingleTonHolder {
        private static SystemAlarmList sInstance = new SystemAlarmList();
    }

    public static SystemAlarmList getInstance() {
        return SingleTonHolder.sInstance;
    }

    private List<String> systemAlarms = new ArrayList<>();

    public List<String> getSystemAlarms() {
        return systemAlarms;
    }
}
