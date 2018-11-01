package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class DappBean implements Serializable {

    public String pt;
    public String sdid;
    public String pid;
    public String oper;
    public String pn;
    public String token;
    public List<Atts> atts;

    public class Atts implements Serializable {

        public int port;
        public String dattid;
        public int value;
    }
}