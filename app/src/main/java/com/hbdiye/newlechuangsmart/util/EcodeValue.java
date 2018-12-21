package com.hbdiye.newlechuangsmart.util;

public class EcodeValue {
    public static final String resultEcode(String code){
//        463	联动条件不存在
//        465	联动任务不存在
//        481	组号设置失败
//        482	场景号设置失败
//        484	场景设置冲突
//        485	场景删除失败
//        486	场景任务不存在
//        487	情景面板不支持绑定该场景
//        488	启动场景无响应
//        499	定时操作失败
//        500	用户未登录或登录失效
//        501	用户未注册
//        502	用户名或密码错误
//        503	验证码请求失败
//        504	用户电话已存在
//        505	验证码已过期
//        506	验证码错误
//        507	验证码到达上限
//        510	不是家庭创建者
//        511	家庭不存在
//        521	家庭创建者不可以退出家庭
//        522	非家庭创建者不允许设置家庭信息
//        531	房间不存在
//        532	不可以删除最后一个房间
//        533	房间中包含设备，不可删除
//        598	客户端未授权连接（白名单）
//        599	客户端被拒绝连接（黑名单）
//        601	机器人未注册
//        602	机器人已经绑定家庭
//        1000	未知错误

        if (code.equals("0")){
            return "操作成功";
        }else if (code.equals("101")){
            return "产品类型不存在";
        }else if (code.equals("301")){
            return "网关未注册";
        }else if (code.equals("302")){
            return "网关解绑失败";
        }else if (code.equals("305")){
            return "部分网关未在线";
        }else if (code.equals("306")){
            return "网关已经绑定家庭";
        }else if (code.equals("398")){
            return "网关查询信息失败";
        }else if (code.equals("401")){
            return "设备入网失败";
        }else if (code.equals("402")){
            return "设备离网失败";
        }else if (code.equals("405")){
            return "部分设备未在线";
        }else if (code.equals("421")){
            return "设备动作不存在";
        }else if (code.equals("431")){
            return "设备属性不存在";
        }else if (code.equals("461")){
            return "联动不存在";
        }else if (code.equals("463")){
            return "联动条件不存在";
        }else if (code.equals("465")){
            return "联动任务不存在";
        }else if (code.equals("481")){
            return "组号设置失败";
        }else if (code.equals("482")){
            return "场景号设置失败";
        }else if (code.equals("484")){
            return "场景设置冲突";
        }else if (code.equals("485")){
            return "场景删除失败";
        }else if (code.equals("486")){
            return "场景任务不存在";
        }else if (code.equals("487")){
            return "情景面板不支持绑定该场景";
        }else if (code.equals("488")){
            return "启动场景无响应";
        }else if (code.equals("499")){
            return "定时操作失败";
        }else if (code.equals("500")){
            return "用户未登录或登录失效";
        }else if (code.equals("501")){
            return "用户未注册";
        }else if (code.equals("502")){
            return "用户名或密码错误";
        }else if (code.equals("503")){
            return "验证码请求失败";
        }else if (code.equals("504")){
            return "用户电话已存在";
        }else if (code.equals("505")){
            return "验证码已过期";
        }else if (code.equals("506")){
            return "验证码错误";
        }else if (code.equals("507")){
            return "验证码到达上限";
        }else if (code.equals("510")){
            return "不是家庭创建者";
        }else if (code.equals("511")){
            return "家庭不存在";
        }else if (code.equals("521")){
            return "家庭创建者不可以退出家庭";
        }else if (code.equals("522")){
            return "非家庭创建者不允许设置家庭信息";
        }else if (code.equals("531")){
            return "房间不存在";
        }else if (code.equals("532")){
            return "不可以删除最后一个房间";
        }else if (code.equals("533")){
            return "房间中包含设备，不可删除";
        }else if (code.equals("598")){
            return "客户端未授权连接（白名单）";
        }else if (code.equals("599")){
            return "客户端被拒绝连接（黑名单）";
        }else if (code.equals("601")){
            return "机器人未注册";
        }else if (code.equals("602")){
            return "机器人已经绑定家庭";
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
            return "房间中包含设备，不可删除";
        }else if (code.equals("1000")){
            return "未知错误";
        }
        else{
            return "操作失败";
        }
    }
}
