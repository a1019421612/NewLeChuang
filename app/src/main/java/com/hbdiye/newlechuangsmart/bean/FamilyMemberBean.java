package com.hbdiye.newlechuangsmart.bean;

import java.io.Serializable;
import java.util.List;

public class FamilyMemberBean implements Serializable {

    public String errcode;
    public List<UserList> userList;

    public class UserList implements Serializable {

        public String familyId;
        public String password;
        public String phone;
        public String name;
        public String icon;
        public String id;
    }
}