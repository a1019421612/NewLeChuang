package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class LinkageDetailBean implements Serializable {

    public String errcode;
    public List<LinkageTaskLists> linkageTaskList;

    public class LinkageTaskLists implements Serializable {

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

        public List<LinkageTaskList> linkageTaskList;

        public class LinkageTaskList implements Serializable {

            public String devActId;
            public String linkageId;
            public int port;
            public String param;
            public int index;
            public int delayTime;
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

    public Linkage linkage;

    public class Linkage implements Serializable {

        public String familyId;
        public String name;
        public String icon;
        public int active;
        public int index;
        public String id;
    }

    public List<LinkageConditionLists> linkageConditionList;

    public class LinkageConditionLists implements Serializable {

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

        public List<LinkageConditionList> linkageConditionList;

        public class LinkageConditionList implements Serializable {

            public int condition;
            public String linkageId;
            public String devAttId;
            public String id;
            public String deviceId;
            public int value;
        }
    }
}