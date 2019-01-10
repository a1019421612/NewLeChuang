package com.hbdiye.newlechuangsmart.bean;

import java.io.Serializable;
import java.util.List;

public class AddRemoteControlBean implements Serializable {

    public String errcode;
    public List<RidsList> ridsList;

    public class RidsList implements Serializable {

        public int infraredType;
        public int brandId;
        public int id;
        public int rid;
        public int spId;
        public TestKey testKey;

        public class TestKey implements Serializable {

            public String fname;
            public String fkey;
            public String pulse;
            public int id;
            public int rid;
        }

    }
}
