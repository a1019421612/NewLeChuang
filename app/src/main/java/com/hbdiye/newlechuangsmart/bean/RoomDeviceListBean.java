package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class RoomDeviceListBean implements Serializable {

    public int errcode;
    public List<RoomList> roomList;

    public class RoomList implements Serializable {

        public String familyId;
        public String temp;
        public String name;
        public int index;
        public String id;
        public List<DeviceList> deviceList;

        public class DeviceList implements Serializable {

            public String familyId;
            public String productId;
            public String name;
            public int index;
            public String started;
            public String id;
            public String gatewayId;
            public String mac;
            public String orgId;
            public String roomId;
            public List<DevAttList> devAttList;

            public class DevAttList implements Serializable {

                public String symbol;
                public int port;
                public int cluNo;
                public String name;
                public int index;
                public String id;
                public int type;
                public String deviceId;
                public int value;
                public int attNo;
            }

            public List<DevActList> devActList;

            public class DevActList implements Serializable {

                public int comNo;
                public int port;
                public int cluNo;
                public String name;
                public int index;
                public String id;
                public int dirNo;
                public String deviceId;
            }
        }
    }
}