package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class UserFamilyInfoBean implements Serializable {

    public String errcode;
    public Family family;

    public class Family implements Serializable {

        public String phone;
        public String name;
        public String id;
    }

    public User user;

    public class User implements Serializable {

        public String familyId;
        public String password;
        public String phone;
        public String name;
        public String icon;
        public String id;
    }
}