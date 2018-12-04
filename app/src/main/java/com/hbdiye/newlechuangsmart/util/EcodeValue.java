package com.hbdiye.newlechuangsmart.util;

public class EcodeValue {
    public static final String resultEcode(String code){
        if (code.equals("0")){
            return "操作成功";
        }else if (code.equals("304")){
            return "网关未在线";
        }else if (code.equals("399")){
            return "网关设置失败";
        }else if (code.equals("403")){
            return "设备未在线";
        }else if (code.equals("404")){
            return "设备未注册";
        }else if (code.equals("483")){
            return "场景不存在";
        }else if (code.equals("801")){
            return "数据非法";
        }else if (code.equals("888")){
            return "服务端错误";
        }else if (code.equals("999")){
            return "协议非法";
        }else if (code.equals("533")){
            return "房间有设备无法删除";
        }else{
            return "操作失败";
        }
    }
}
