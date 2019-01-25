package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class ZngjBean implements Serializable {

    public String errcode;
    public List<RobotList> robotList;

    public class RobotList implements Serializable {

        public String familyId;
        public String serialnumber;
        public String productId;
        public String created;
        public String name;
        public int online;
        public String started;
        public String id;
        public String version;
    }
}