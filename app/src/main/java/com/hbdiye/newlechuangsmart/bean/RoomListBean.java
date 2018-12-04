package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class RoomListBean implements Serializable {

    public String errcode;
    public List<RoomList> roomList;

    public class RoomList implements Serializable {

        public String familyId;
        public String name;
        public String icon;
        public int index;
        public String id;
    }
}