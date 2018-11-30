package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class ChuanglianBean implements Serializable {

    public String errcode;
    public Data data;

    public class Data implements Serializable {

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
        public List<DevAttList> devAttList;

        public class DevAttList {
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
}