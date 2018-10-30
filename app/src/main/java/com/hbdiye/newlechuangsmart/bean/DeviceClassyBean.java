package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class DeviceClassyBean implements Serializable {
    public String errcode;
    public List<DeviceList> deviceList;

    public class DeviceList implements Serializable {

        public String familyId;
        public String devAttList;
        public String productId;
        public String name;
        public int index;
        public String started;
        public String id;
        public String devActList;
        public String mac;
        public String orgId;
        public String roomId;
        public String gatewayId;
    }
}