package com.hbdiye.newlechuangsmart.global;

import java.util.HashMap;

public class InterfaceManager {
    public static final String LOGIN = "LOGIN";//登录
    public static final String REGISTER = "REGISTER";//注册
    public static final String GETVAILCODE = "GETVAILCODE";//获取验证码
    public static final String FORGETPSW = "FORGETPSW";//忘记密码
    public static final String HWREGISTER = "HWREGISTER";//红外注册
    public static final String HWGETREMOTE = "HWGETREMOTE";//获取红外列表
    public static final String ADDREMOTE = "ADDREMOTE";//添加红外
    public static final String EDITREMOTE = "EDITREMOTE";//编辑遥控中心
    public static final String DELREMOTE = "DELREMOTE";//删除遥控中心
    public static final String GETDEVICELIST = "GETDEVICELIST";//支持的红外设备列表
    public static final String GETBRANDLIST = "GETBRANDLIST";//获取品牌列表
    public static final String GETMODELIST = "GETMODELIST";//获取型号列表
    public static final String CHECKIRDEVICE = "CHECKIRDEVICE";//匹配一个指定的红外设备
    public static final String ADDIRDEVICE = "ADDIRDEVICE";//增加红外设备
    public static final String EDITIRDEVICE = "EDITIRDEVICE";//编辑红外设备名称
    public static final String DELIRDEVICE = "DELIRDEVICE";//删除红外设备
    public static final String SENDCODE = "SENDCODE";//控制红外设备


    public static final String HOSTURL = "";
    public static final String BASEURL = "http://www.thingtill.com/Home/ThreeIr/";
    public static final String HWREGISTER_URL = BASEURL + "reg";

    public static final String APPID = "123456";
    public static final String APPKEY = "3576be75bca3495e";

    //    ===============================================================
    public static final String NEWBASEURL="http://39.104.119.0:5678/";
    public static final String DEVICELIST="DEVICELIST";//第三级详情界面
    public static final String FINDDEVICELISTBYPRODUCTID="FINDDEVICELISTBYPRODUCTID";//DYPServer/device/findDeviceListByProductId
    public static final String PERSONINFO="PERSONINFO";//我的信息（包含家庭信息）
    public static final String GETINDEXDATA="GETINDEXDATA";//首页数据初始化
    public static final String MEMBERLIST="MEMBERLIST";//家庭成员列表
    public static final String KICKEDOUTUSERBYPHONE="KICKEDOUTUSERBYPHONE";//踢出家庭成员
    public static final String MESSAGELIST="MESSAGELIST";//消息列表
    public static final String SCENELIST="SCENELIST";//场景列表
    public static final String DEVICEDETAIL="DEVICEDETAIL";//设备详情
    public static final String GATEWAYLIST="GATEWAYLIST";//主机列表
    public static final String UPDATESCENEINDEX="UPDATESCENEINDEX";//场景排序
    public static final String UPDATESCENE="UPDATESCENE";//修改场景名称和图标
    public static final String SCENEDETAIL="SCENEDETAIL";//场景详情
    public static final String CREATESCENE="CREATESCENE";//创建场景
    public static final String DEVICELISTSCENE="DEVICELISTSCENE";//场景添加设备列表
    public static final String SCENEEDITACTION="SCENEEDITACTION";//联动条件设备列表
    public static final String LINKAGELIST="LINKAGELIST";//联动列表
    public static final String UPDATELINKAGEACTIVE="UPDATELINKAGEACTIVE";//联动开关
    public static final String CREATELINKAGE="CREATELINKAGE";//创建联动
    public static final String DELLINKAGE="DELLINKAGE";//删除联动
    public static final String LINKAGEDETAIL="LINKAGEDETAIL";//联动详情
    public static final String CREATECONDITION="CREATECONDITION";//创建联动条件
    public static final String CREATETASK="CREATETASK";//创建联动任务
    public static final String DELLINKAGEACTION="DELLINKAGEACTION";//删除联动动作
    public static final String UPDATELINKAGETASK="UPDATELINKAGETASK";//联动更新动作
    public static final String UPDATELINKAGECONDITION="UPDATELINKAGECONDITION";//更新联动条件

    public static final String BINDGATEWAY="BINDGATEWAY";//添加网关
    public static final String USERANDFAMILYINFO="USERANDFAMILYINFO";//个人和家庭信息
    public static final String UPDATENICKNAME="UPDATENICKNAME";//修改昵称
//    ===============================================================


    private static HashMap<String, String> urlManager = new HashMap<String, String>();
    private static InterfaceManager manager;

    public static InterfaceManager getInstance() {
        if (manager == null) {
            manager = new InterfaceManager();


            urlManager.put(InterfaceManager.LOGIN, NEWBASEURL+"DYPServer/user/login");
            urlManager.put(InterfaceManager.DEVICELIST,NEWBASEURL+"DYPServer/device/findDeviceListByRoom");
            urlManager.put(InterfaceManager.FINDDEVICELISTBYPRODUCTID,NEWBASEURL+"DYPServer/device/findDeviceListGroupByRoomByProduct");
            urlManager.put(InterfaceManager.PERSONINFO,NEWBASEURL+"DYPServer/user/findUser");
            urlManager.put(InterfaceManager.GETINDEXDATA,NEWBASEURL+"DYPServer/index/getIndexData");
            urlManager.put(InterfaceManager.MEMBERLIST,NEWBASEURL+"DYPServer/user/findUserList");
            urlManager.put(InterfaceManager.KICKEDOUTUSERBYPHONE,NEWBASEURL+"DYPServer/user/kickedOutUserByPhone");
            urlManager.put(InterfaceManager.MESSAGELIST,NEWBASEURL+"DYPServer/index/findMessageListByPageBean");
            urlManager.put(InterfaceManager.SCENELIST,NEWBASEURL+"DYPServer/scene/findSceneList");
            urlManager.put(InterfaceManager.DEVICEDETAIL,NEWBASEURL+"DYPServer/device/findDeviceDetails");
            urlManager.put(InterfaceManager.GATEWAYLIST,NEWBASEURL+"DYPServer/device/findGatewayListGroupByRoom");
            urlManager.put(InterfaceManager.UPDATESCENEINDEX,NEWBASEURL+"DYPServer/scene/updateSceneIndex");
            urlManager.put(InterfaceManager.UPDATESCENE,NEWBASEURL+"DYPServer/scene/updateSceneNameAndIcon");
            urlManager.put(InterfaceManager.SCENEDETAIL,NEWBASEURL+"DYPServer/scene/findSceneDetails");
            urlManager.put(InterfaceManager.CREATESCENE,NEWBASEURL+"DYPServer/scene/createScene");
            urlManager.put(InterfaceManager.DEVICELISTSCENE,NEWBASEURL+"DYPServer/device/findDeviceDetailsListGroupByRoomByIncludeAction");
            urlManager.put(InterfaceManager.SCENEEDITACTION,NEWBASEURL+"DYPServer/device/findDeviceDetailsListGroupByRoomByIncludeAttribute");
            urlManager.put(InterfaceManager.LINKAGELIST,NEWBASEURL+"DYPServer/linkage/findLinkageList");
            urlManager.put(InterfaceManager.UPDATELINKAGEACTIVE,NEWBASEURL+"DYPServer/linkage/updateLinkageActive");
            urlManager.put(InterfaceManager.CREATELINKAGE,NEWBASEURL+"DYPServer/linkage/createLinkage");
            urlManager.put(InterfaceManager.DELLINKAGE,NEWBASEURL+"DYPServer/linkage/deleteLinkage");
            urlManager.put(InterfaceManager.LINKAGEDETAIL,NEWBASEURL+"DYPServer/linkage/findLinkageDetails");
            urlManager.put(InterfaceManager.CREATECONDITION,NEWBASEURL+"DYPServer/linkage/createLinkageCondition");
            urlManager.put(InterfaceManager.BINDGATEWAY,NEWBASEURL+"DYPServer/device/bindGateway");
            urlManager.put(InterfaceManager.USERANDFAMILYINFO,NEWBASEURL+"DYPServer/user/findUserAndFamily");
            urlManager.put(InterfaceManager.UPDATENICKNAME,NEWBASEURL+"DYPServer/user/updateUserNickname");
            urlManager.put(InterfaceManager.CREATETASK,NEWBASEURL+"DYPServer/linkage/createLinkageTask");
            urlManager.put(InterfaceManager.DELLINKAGEACTION,NEWBASEURL+"DYPServer/linkage/deleteLinkageTask");
            urlManager.put(InterfaceManager.UPDATELINKAGETASK,NEWBASEURL+"DYPServer/linkage/updateLinkageTask");
            urlManager.put(InterfaceManager.UPDATELINKAGECONDITION,NEWBASEURL+"DYPServer/linkage/updateLinkageCondition");
//            urlManager.put(InterfaceManager.LOGIN, "http://39.104.119.0/SmartHome-java-user/user/login");
            urlManager.put(InterfaceManager.REGISTER, "http://39.104.119.0:80/SmartHome-java-user/user/register");
            urlManager.put(InterfaceManager.GETVAILCODE, "http://39.104.119.0:80/SmartHome-java-user/user/getValidateCode");
            urlManager.put(InterfaceManager.FORGETPSW, "http://39.104.119.0:80/SmartHome-java-user/user/resetPassword");
            urlManager.put(InterfaceManager.HWREGISTER, HWREGISTER_URL);
            urlManager.put(InterfaceManager.HWGETREMOTE, BASEURL + "get_remote");
            urlManager.put(InterfaceManager.ADDREMOTE, BASEURL + "add_remote");
            urlManager.put(InterfaceManager.EDITREMOTE, BASEURL + "edit_remote");
            urlManager.put(InterfaceManager.DELREMOTE, BASEURL + "del_remote");
            urlManager.put(InterfaceManager.GETDEVICELIST, BASEURL + "get_device_list");
            urlManager.put(InterfaceManager.GETBRANDLIST, BASEURL + "get_brand_list");
            urlManager.put(InterfaceManager.GETMODELIST, BASEURL + "get_mode_list");
            urlManager.put(InterfaceManager.CHECKIRDEVICE, BASEURL + "check_ir_device");
            urlManager.put(InterfaceManager.ADDIRDEVICE, BASEURL + "add_ir_device");
            urlManager.put(InterfaceManager.EDITIRDEVICE, BASEURL + "edit_ir_device");
            urlManager.put(InterfaceManager.DELIRDEVICE, BASEURL + "del_ir_device");
            urlManager.put(InterfaceManager.SENDCODE, BASEURL + "send_code");
        }
        return manager;
    }

    public static final int TIMECONDITION = 101;

    /**
     * 获取路径
     *
     * @param name
     * @return
     */
    public String getURL(String name) {
        return urlManager.get(name);
    }
}
