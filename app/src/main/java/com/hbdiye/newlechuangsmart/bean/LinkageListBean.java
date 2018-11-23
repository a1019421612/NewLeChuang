package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class LinkageListBean implements Serializable {

    public String errcode;
    public List<LinkageList> linkageList;

    public class LinkageList implements Serializable {

        public String familyId;
        public String name;
        public String icon;
        public int active;
        public int index;
        public String id;
    }
}
