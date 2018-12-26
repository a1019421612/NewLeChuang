package com.hbdiye.newlechuangsmart.music.factory;

import com.google.gson.JsonObject;

/**
 * 作者：kelingqiu on 18/2/26 10:00
 * 邮箱：42747487@qq.com
 */

public class HttpRequestFactory {

    /**
     * 获取短信验证码
     * @param phone 手机号/邮箱号
     * @return 请求参数
     */
    public static String codeSend(String phone){
        return "\""+phone+"\"";
    }

    /**
     * 会员帐号注册
     * @param receiver 会员注册时输入的手机号码
     * @param userPwd 会员注册时输入的明文密码进行base64编码后的字符串
     * @param authCode 会员注册时输入的短信验证码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @param registerSource 注册来源（1：android，2：ios，3：平板，4：平台，5：微信）
     * @param deviceNo 设备SN号码，仅设备注册时需要提供，如：90310708010526 （非必填）
     * @param deviceId 设备编号，仅设备注册时需要提供，如：HOPE-A7-AL（非必填）
     * @param proviceName 当前注册时所在位置的省份名称
     * @param cityName 当前注册时所在位置的城市名称
     * @param areaName 当前注册时所在位置的县区名称
     * @return
     */
    public static String memberRegister(String receiver, String userPwd, String authCode, String uuid, int registerSource,
                                        String deviceNo, long deviceId, String proviceName, String cityName, String areaName){
        JsonObject jo = new JsonObject();
        jo.addProperty("receiver", receiver);
        jo.addProperty("userPwd", userPwd);
        jo.addProperty("authCode", authCode);
        jo.addProperty("uuid", uuid);
        jo.addProperty("registerSource", registerSource);
        jo.addProperty("deviceNo", deviceNo);
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("proviceName", proviceName);
        jo.addProperty("cityName", cityName);
        jo.addProperty("areaName", areaName);
        return jo.toString();
    }

    /**
     * 会员帐号登录
     * @param loginAccount 会员登录时输入的登录帐号
     * @param loginPasswd 会员登录时输入的明文密码进行base64编码后的字符串
     * @param sysVersion 设备或手机当前运行的系统版本 （强烈建议填）
     * @param softVersion 设备或手机当前运行的软件版本 （强烈建议填）
     * @param mobileBrand 设备或手机的品牌名称 （强烈建议填）
     * @return
     */
    public static String memberLogin(String loginAccount, String loginPasswd, String sysVersion, String softVersion, String mobileBrand){
        JsonObject jo = new JsonObject();
        jo.addProperty("loginAccount", loginAccount);
        jo.addProperty("loginPasswd", loginPasswd);
        jo.addProperty("sysVersion", sysVersion);
        jo.addProperty("softVersion", softVersion);
        jo.addProperty("mobileBrand", mobileBrand);
        return jo.toString();
    }

    /**
     * 会员忘记密码
     * @param receiver 要进行重置密码的登录帐号，如：手机号码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @param authCode 会员注册时输入的短信验证码
     * @param userPwd 会员重置密码时输入的明文密码进行base64编码后的字符串
     * @return
     */
    public static String memberReset(String receiver, String uuid, String authCode, String userPwd){
        JsonObject jo = new JsonObject();
        jo.addProperty("receiver", receiver);
        jo.addProperty("uuid", uuid);
        jo.addProperty("authCode", authCode);
        jo.addProperty("userPwd", userPwd);
        return jo.toString();
    }

    /**
     * 会员退出登录
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @return
     */
    public static String memberLogout(String tokenId){
        return tokenId;
    }

    /**
     * 当前帐号验证
     * @param receiver 要重新绑定的新的手机号码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @param authCode 访问短信验证码接口获取到的验证码字符串
     * @param tokenId 访问登录接口时获取的tokenId进行base64编码后的字符串
     * @return
     */
    public static String accountValidate(String receiver, String uuid, String authCode, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("receiver", receiver);
        jo.addProperty("uuid", uuid);
        jo.addProperty("authCode", authCode);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 会员修改帐号
     * @param receiver 要重新绑定的新的手机号码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @param authCode 访问短信验证码接口获取到的验证码字符串
     * @param tokenId 访问登录接口时获取的tokenId进行base64编码后的字符串
     * @return
     */
    public static String accountBinding(String receiver, String uuid, String authCode, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("receiver", receiver);
        jo.addProperty("uuid", uuid);
        jo.addProperty("authCode", authCode);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 会员修改密码
     * @param tokenId 访问登录接口时获取的tokenId进行base64编码后的字符串
     * @param userPwd 用户当前登录密码进行base64编码后的字符串
     * @param newPasswd 用户新登录密码进行base64编码后的字符串
     * @return
     */
    public static String accountPasswd(String tokenId, String userPwd, String newPasswd) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("userPwd", userPwd);
        jo.addProperty("newPasswd", newPasswd);
        return jo.toString();
    }

    /**
     * 加载会员信息
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @return
     */
    public static String profileLoad(String tokenId){
        return "\""+tokenId+"\"";
    }

    /**
     * 设置会员头像
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @return
     */
    public static String profileHeader(String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("userHeader", "header");
        return jo.toString();
    }

    /**
     * 设置会员昵称
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @param userName 会员设置或修改头像信息时输入的昵称
     * @return
     */
    public static String profileAlias(String tokenId, String userName){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("userName", userName);
        return jo.toString();
    }

    /**
     * 设置会员昵称
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @param userBirth 会员设置或修改头像信息时输入的生日 格式：2017-11-03
     * @return
     */
    public static String profileBirth(String tokenId, String userBirth){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("userBirth", userBirth);
        return jo.toString();
    }

    /**
     *
     * @param bindType
     * @param currentPage
     * @param pageSize
     * @param tokenId
     * @return
     */
    public static String deviceList(int bindType, int currentPage, int pageSize, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("bindType", bindType);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 设备信息绑定
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param comName 设备厂商名称
     * @param deviceSN 设备SN号码，标识唯一一台设备
     * @param deviceCata 设备类型名称
     * @param deviceName 设备名称
     * @param playerType 播放器类型
     * @param parentId 该设备所在的上层类别编号
     * @return
     */
    public static String deviceBinding(String tokenId, String comName, String deviceSN, String deviceCata, String deviceName, String playerType, long parentId){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("comName", comName);
        jo.addProperty("deviceSN", deviceSN);
        jo.addProperty("deviceCata", deviceCata);
        jo.addProperty("deviceName", deviceName);
        jo.addProperty("playerType", playerType);
        jo.addProperty("parentId", parentId);
        return jo.toString();
    }

    /**
     * 设备解绑
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String deviceUnbinding(long deviceId, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 设备管理查询
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String deviceSearch(String tokenId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 加载设备详情
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String deviceLoad(long deviceId, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 修改设备昵称
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String deviceNicker(long deviceId, String tokenId, String deviceMark){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("deviceMark", deviceMark);
        return jo.toString();
    }

    /**
     * 设备分类查询
     * @param parentId 手机端当前查询的上层分类编号
     * @param currentPage App会员登录后的会话token进行base64编码后的字符串
     * @param pageSize 页码
     * @param tokenId 每页显示条数
     * @return
     */
    public static String catalogList(long parentId, int currentPage, int pageSize, String tokenId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("parentId", parentId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 设备分类详情
     * @param refrenceId 从分类列表页获取的分类编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String catalogLoad(long refrenceId, String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("refrenceId", refrenceId);
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 歌曲列表
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String listMusic(long deviceId, String tokenId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 歌手列表
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String listAuthor(long deviceId, String tokenId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 专辑列表
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String listAlbum(long deviceId, String tokenId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 歌单列表查询
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 当前需要查询的页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    public static String sheetList(long deviceId, String tokenId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     *  歌手名称查询
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param authorName 歌手名称
     * @param currentPage 当前需要查询的页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    public static String musicAuthor(long deviceId, String tokenId, String authorName, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("authorName", authorName);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     *  专辑名称查询
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param albumName 专辑名称
     * @param currentPage 当前需要查询的页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    public static String musicAlbum(long deviceId, String tokenId, String albumName, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("albumName", albumName);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 歌单编号查询
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param sheetId 歌单编号
     * @param currentPage 当前需要查询的页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    public static String musicList(long deviceId, String tokenId, int sheetId, int currentPage, int pageSize){
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("sheetId", sheetId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 歌单编号查询
     * @param deviceId 平台分配的设备唯一编号
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param sheetCata 歌单类别
     * @param currentPage 当前需要查询的页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    public static String musicPlayer(long deviceId, String tokenId, int sheetCata, int currentPage, int pageSize) {
        JsonObject jo = new JsonObject();
        jo.addProperty("deviceId", deviceId);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("sheetCata", sheetCata);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    public static String feedbackInsert(String tokenId) {
        JsonObject jo = new JsonObject();
        return jo.toString();
    }

    /**
     * 最新消息
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String memberMessageLatest(String tokenId){
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 消息列表
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param msgCata 消息分类
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String memberMessageList(String tokenId, int msgCata, int currentPage, int pageSize) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("msgCata", msgCata);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 消息筛选
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param object 设备列表和情景列表的json格式数据
     * @return
     */
    public static String memberMessageFilterList(int currentPage, int pageSize, String tokenId, String object) {
        JsonObject jo = new JsonObject();
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("object", object);
        return jo.toString();
    }

    /**
     * 消息筛选
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String memberMessageFilter(String tokenId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId",tokenId);
        return jo.toString();
    }

    /**
     * 清空消息
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param msgCata 消息分类
     * @return
     */
    public static String memberMessageClear(String tokenId, int msgCata) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId",tokenId);
        jo.addProperty("msgCata",msgCata);
        return jo.toString();
    }

    /**
     * 标记已读
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param messageId 消息id
     * @return
     */
    public static String memberMessageMarkRead(String tokenId, long messageId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId",tokenId);
        jo.addProperty("messageId",messageId);
        return jo.toString();
    }

    /**
     * 查询消息设置
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @return
     */
    public static String memberMessageLoadConfig(String tokenId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId",tokenId);
        return jo.toString();
    }

    /**
     * 消息设置
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param receiveMsg 消息接收标识 0-不接收，1-接收
     * @return
     */
    public static String memberMessageConfig(String tokenId, int receiveMsg) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId",tokenId);
        jo.addProperty("receiveMsg",receiveMsg);
        return jo.toString();
    }

    /**
     * 第三方联合登陆
     * @param bindType 当前联合登录类型
     * @param nickName 第三方用户昵称 （有就填）
     * @param unionId 第三方用户唯一标识
     * @param userGender 第三方用户性别 第三方接口返回的当前用户性别，1：男，2：女，0：未知（有就填）
     * @param userCity 第三方用户当前所在城市 （有就填）
     * @param userProvince 第三方用户当前所在省份 （有就填）
     * @return
     */
    public static String unionLogin(int bindType, String nickName, String unionId, int userGender, String userCity, String userProvince){
        JsonObject jo = new JsonObject();
        jo.addProperty("bindType", bindType);
        jo.addProperty("nickName", nickName);
        jo.addProperty("unionId", unionId);
        jo.addProperty("userGender", userGender);
        jo.addProperty("userCity", userCity);
        jo.addProperty("userProvince", userProvince);
        return jo.toString();
    }

    /**
     * 第三方登陆绑定账号
     * @param bindType 当前联合登录类型
     * @param unionId 第三方用户唯一标识
     * @param receiver 用户在手机上输入的手机号码（兼容邮箱）
     * @param authCode 用户输入的短信验证码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @return
     */
    public static String unionAccount(int bindType, String unionId, String receiver, String authCode, String uuid){
        JsonObject jo = new JsonObject();
        jo.addProperty("bindType", bindType);
        jo.addProperty("unionId", unionId);
        jo.addProperty("receiver", receiver);
        jo.addProperty("authCode", authCode);
        jo.addProperty("uuid", uuid);
        return jo.toString();
    }

    /**
     * 登录后，帐号绑定
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @param unionId 第三方用户唯一标识
     * @param bindType 当前联合登录类型
     * @param nickName 第三方用户昵称 （有就填）
     * @param userGender 第三方用户性别 （有就填）
     * @param userProvince 第三方用户当前所在省份 （有就填）
     * @param userCity 第三方用户当前所在城市 （有就填）
     * @return
     */
    public static String unionBind(String tokenId, String unionId, int bindType, String nickName, int userGender, String userProvince, String userCity) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("unionId", unionId);
        jo.addProperty("bindType", bindType);
        jo.addProperty("nickName", nickName);
        jo.addProperty("userGender", userGender);
        jo.addProperty("userProvince", userProvince);
        jo.addProperty("userCity", userCity);
        return jo.toString();
    }

    /**
     * 登录后，解除绑定
     * @param refrenfeId 当前帐号绑定关系唯一编号
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @return
     */
    public static String unionUnbind(long refrenfeId, String tokenId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("refrenfeId", refrenfeId);
        return jo.toString();
    }

    /**
     * 登录后，查询绑定
     * @param tokenId 访问登录接口时获取到的会话token进行base64编码后的字符串
     * @return
     */
    public static String unionList(String tokenId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        return jo.toString();
    }

    /**
     * 未注册，设置密码
     * @param unionId 第三方用户唯一标识
     * @param bindType 当前联合登录类型
     * @param receiver 用户在手机上输入的手机号码（兼容邮箱）
     * @param userPwd 用户输入的明文密码进行base64编码后的字符串
     * @param authCode 用户输入的短信验证码
     * @param uuid 获取短信验证码时平台返回的验证码唯一标识
     * @param registerSource 注册来源（1：android，2：ios，3：平板，4：平台，5：微信）
     * @param userProvince 当前所在位置的省份名称，不是第三方信息中的省份 （有就填）
     * @param userCity 当前所在位置的城市名称，不是第三方信息中的城市（有就填）
     * @param userArea 当前所在位置的县区名称，不是第三方信息中的区域（有就填）
     * @return
     */
    public static String unionPasswd(String unionId, int bindType, String receiver, String userPwd, String authCode, String uuid, int registerSource, String userProvince, String userCity, String userArea) {
        JsonObject jo = new JsonObject();
        jo.addProperty("unionId", unionId);
        jo.addProperty("bindType", bindType);
        jo.addProperty("receiver", receiver);
        jo.addProperty("userPwd", userPwd);
        jo.addProperty("authCode", authCode);
        jo.addProperty("uuid", uuid);
        jo.addProperty("registerSource", registerSource);
        jo.addProperty("userProvince", userProvince);
        jo.addProperty("userCity", userCity);
        jo.addProperty("userArea", userArea);
        return jo.toString();
    }

    /**
     * 分享号码校验
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param receiveIds 逗号隔开的手机号码字符串
     * @return
     */
    public static String appCkMobile(String tokenId, String receiveIds) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("receiveIds", receiveIds);
        return jo.toString();
    }

    /**
     * 手机查询情景
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param currentPage 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public static String sceneSearch(String tokenId, int currentPage, int pageSize) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("currentPage", currentPage);
        jo.addProperty("pageSize", pageSize);
        return jo.toString();
    }

    /**
     * 手机加载情景详情
     * @param tokenId App会员登录后的会话token进行base64编码后的字符串
     * @param refrenceId 情景编号
     * @return
     */
    public static String sceneLoad(String tokenId, long refrenceId) {
        JsonObject jo = new JsonObject();
        jo.addProperty("tokenId", tokenId);
        jo.addProperty("refrenceId", refrenceId);
        return jo.toString();
    }
}
