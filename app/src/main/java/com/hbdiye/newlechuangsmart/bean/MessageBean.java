package com.hbdiye.newlechuangsmart.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class MessageBean implements Serializable {

    public String errcode;
    public List<MessageList> messageList;

    public class MessageList implements Serializable {

        public String familyId;
        public int read;
        public String subject;
        public String created;
        public String id;
        public String mesTypeId;
        public String userId;
        public String content;
    }
}