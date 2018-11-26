package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

//public class GateWayBean implements Serializable {
//
//    public String errcode;
//    public List<RoomList> roomList;
//
//    public class RoomList implements Serializable {
//
//        public String familyId;
//        public String temp;
//        public String name;
//        public String icon;
//        public int index;
//        public String id;
//        public List<DeviceList> deviceList;
//
//        public class DeviceList implements Serializable {
//
//            public String familyId;
//            public String serialnumber;
//            public String productId;
//            public String name;
//            public int index;
//            public String started;
//            public String id;
//            public String version;
//            public String mac;
//            public String orgId;
//            public String roomId;
//        }
//    }
//}
public class GateWayBean implements Serializable {

    public String errcode;
    public List<RoomList> roomList;

    public class RoomList implements Serializable {

        public String familyId;
        public String name;
        public String icon;
        public int index;
        public String id;
        public List<GatewayList> gatewayList;

        public class GatewayList implements Serializable {

            public String familyId;
            public String serialnumber;
            public String productId;
            public String name;
            public int index;
            public String started;
            public String id;
            public String version;
            public String mac;
            public String orgId;
            public String roomId;
        }
    }
}