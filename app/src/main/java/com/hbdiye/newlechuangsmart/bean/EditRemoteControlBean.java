package com.hbdiye.newlechuangsmart.bean;

import java.io.Serializable;
import java.util.List;

public class EditRemoteControlBean implements Serializable {

    public String errcode;
    public Data data;

    public class Data implements Serializable {

        public int fre;
        public int rid;
        public String exts;
        public List<KeyList> keyList;

        public class KeyList implements Serializable {

            public String fname;
            public String fkey;
            public String pulse;
            public int id;
            public int rid;
        }
    }
}
