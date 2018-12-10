package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class MyDevicesBean implements Serializable {

    public String errcode;
    public List<DeviceList> deviceList;

    public class DeviceList implements Serializable {

        public String familyId;
        public String productId;
        public String name;
        public int index;
        public String started;
        public String id;
        public String mac;
        public String orgId;
        public String roomId;
        public String gatewayId;
    }
}
