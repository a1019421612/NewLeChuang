package com.hbdiye.newlechuangsmart.bean;
        import java.io.Serializable;
        import java.util.List;
public class WenShiDuBean implements Serializable  {

    public String errcode  ;
    public  Data data;
    public class Data implements Serializable  {

        public String familyId  ;
        public String productId  ;
        public String name  ;
        public int index  ;
        public String started  ;
        public String id  ;
        public String mac  ;
        public String orgId  ;
        public String roomId  ;
        public String gatewayId  ;public List<DevAttList> devAttList;
        public class DevAttList implements Serializable  {

            public String symbol  ;
            public int port  ;
            public String name  ;
            public int cluNo  ;
            public int index  ;
            public String id  ;
            public int type  ;
            public String deviceId  ;
            public int value  ;
            public int attNo  ;
        }
        public List<DevActList> devActList;
        public class DevActList {
        }

    }
}