package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class VersionBean implements Serializable {

    public String errcode;
    public Data data;

    public class Data implements Serializable {

        public String classify;
        public String download;
        public String updateTime;
        public String id;
        public String version;
        public String content;
    }
}