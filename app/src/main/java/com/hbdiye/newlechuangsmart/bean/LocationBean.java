package com.hbdiye.newlechuangsmart.bean;

import java.io.Serializable;
import java.util.List;

public class LocationBean implements Serializable {

    public String errcode;
    public List<SpList> spList;

    public class SpList implements Serializable {

        public int areadId;
        public String spName;
        public int id;
        public int type;
        public int spId;
    }
}
