package com.hbdiye.newlechuangsmart.bean;

import java.io.Serializable;

public class DeviceBean implements Serializable {
    public String deviceId;
    public String deviceName;
    public int deviceUrl;
    public int rid;//遥控器id
    public EditRemoteControlBean.Data data;
}