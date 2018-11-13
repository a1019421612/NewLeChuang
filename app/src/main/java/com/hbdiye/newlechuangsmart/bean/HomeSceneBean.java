package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class HomeSceneBean implements Serializable {

    public String errcode;
    public int unread;
    public List<MonitorList> monitorList;

    public class MonitorList {
    }

    public List<SceneList> sceneList;

    public class SceneList implements Serializable {

        public String familyId;
        public int sceneNo;
        public String timingId;
        public String name;
        public String icon;
        public int index;
        public String id;
        public int groupNo;
    }
}