package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class SceneDetailBean implements Serializable {

    public String errcode;
    public Timing timing;

    public class Timing implements Serializable {
    }

    public List<DeviceList> deviceList;

    public class DeviceList implements Serializable {

        public String productId;
        public int index;
        public String started;
        public String mac;
        public String orgId;
        public String roomId;
        public String familyId;
        public String reserved;
        public String name;
        public String id;
        public String gatewayId;
        public List<DevAttList> devAttList;

        public class DevAttList implements Serializable {

            public String symbol;
            public int port;
            public String name;
            public int cluNo;
            public int index;
            public String id;
            public int type;
            public String deviceId;
            public int value;
            public int attNo;
        }

        public List<SceneTaskList> sceneTaskList;

        public class SceneTaskList implements Serializable {

            public String devActId;
            public int port;
            public String param;
            public String sceneId;
            public int index;
            public String id;
            public String deviceId;
        }

        public List<DevActList> devActList;

        public class DevActList implements Serializable {

            public int comNo;
            public int port;
            public String param;
            public String name;
            public int cluNo;
            public int index;
            public String id;
            public int dirNo;
            public String deviceId;
        }
    }

    public Scene scene;

    public class Scene implements Serializable {

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