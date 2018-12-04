package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class MonitorBean implements Serializable {

    public String errcode;
    public List<RoomList> roomList;

    public class RoomList implements Serializable {

        public String familyId;
        public String reserved;
        public String name;
        public String icon;
        public int index;
        public String id;
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
            public String deviceName;
            public int attNo;
        }
    }
}