package com.hbdiye.newlechuangsmart.util;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.devicefragment.ChaZuoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ChuangLianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.DianjiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.JinJianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanOneFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanThreeFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanTwoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KeRanCGQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.MenCiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.MenSuoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.RenTiGYFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ScenePanelFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ScenePanelSixFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ShengGuangBJQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ShuiJinCGQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.WenShiDuFragment;
import com.hbdiye.newlechuangsmart.devicefragment.YanWuCGQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.YiYanghtCGQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ZhenDongCGQFragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ClassyIconByProId {
    /**
     * 根据id判断要显示的图标
     *
     * @param id
     * @return
     */
    public static int iconNormal(String id) {
        if (id.contains("PRO001")) {//智能主机
            return R.drawable.qita_n;
        }else if (id.contains("PRO002")) {//开关设备
            return R.drawable.zhaoming_n;
        } else if (id.contains("PRO003")){//安防设备
            return R.drawable.anfang_n;
        }else if (id.contains("PRO004")){//环境监测
            return R.drawable.huanjingjiance_n;
        }else if (id.contains("PRO005")){//窗帘设备
            return R.drawable.chuanglian_n;
        }else if (id.contains("PRO006")){//家电控制
            return R.drawable.jiaidna_n;
        }else if (id.contains("PRO007")){//智能管家
            return R.drawable.zhinengguanjia_n;
        }else if (id.contains("PRO008")){//医疗养老
            return R.drawable.yiliao_n;
        }else if (id.contains("PRO009")){//其他
            return R.drawable.qita_n;
        }else {
            return R.drawable.qita_n;
        }
    }

    /**
     * 根据id判断要显示的图标
     *
     * @param id
     * @return
     */
    public static int iconPressed(String id) {
        if (id.contains("PRO001")) {//智能主机
            return R.drawable.qita_p;
        }else if (id.contains("PRO002")) {//照明设备
            return R.drawable.zhaoming_p;
        } else if (id.contains("PRO003")){//安防设备
            return R.drawable.anfang_p;
        }else if (id.contains("PRO004")){//环境监测
            return R.drawable.huanjingjiance_p;
        }else if (id.contains("PRO005")){//窗帘设备
            return R.drawable.chaunglian_p;
        }else if (id.contains("PRO006")){//家电控制
            return R.drawable.jiaidna_p;
        }else if (id.contains("PRO007")){//智能管家
            return R.drawable.zhinengguanjia_p;
        }else if (id.contains("PRO008")){//医疗养老
            return R.drawable.yiliao_p;
        }else if (id.contains("PRO009")){//其他
            return R.drawable.qita_p;
        }else {
            return R.drawable.qita_p;
        }
    }

    /**
     * 根据id判断要显示的详情界面
     *
     * @param id
     * @return
     */
    public static Fragment fragmentById(String produceId,String id) {
        if (produceId.equals("PRO002001001")) {//一路开关
            return KaiGuanOneFragment.newInstance(id);
        }else if (produceId.equals("PRO002002001")){//二路开关
            return KaiGuanTwoFragment.newInstance(id);
        }else if (produceId.equals("PRO002003001")){//三路开关
            return KaiGuanThreeFragment.newInstance(id);
        }else if (produceId.equals("PRO003002001")){//声光报警器
            return ShengGuangBJQFragment.newInstance(id);
        }else if (produceId.equals("PRO003004001")){//智能门锁
            return MenSuoFragment.newInstance(id);
        }else if (produceId.equals("PRO003005001")){//门磁
            return MenCiFragment.newInstance(id);
        }else if (produceId.equals("PRO004001001")){//温湿度检测器
            return WenShiDuFragment.newInstance(id);
        }else if (produceId.equals("PRO005002001")){//窗帘电机
            return DianjiFragment.newInstance(id);
        }else if (produceId.equals("PRO005001001")){//一路窗帘开关
            return ChuangLianFragment.newInstance(id);
        }else if (produceId.equals("PRO006001001")){//五孔插座
            return ChaZuoFragment.newInstance(id);
        }else if (produceId.equals("PRO003006004")){//水浸传感器
            return ShuiJinCGQFragment.newInstance(id);
        }else if (produceId.equals("PRO003006008")){//可燃气体传感器
            return KeRanCGQFragment.newInstance(id);
        }else if (produceId.equals("PRO003006003")){//烟雾报警器
            return YanWuCGQFragment.newInstance(id);
        }else if (produceId.equals("PRO003006006")){//一氧化碳报警器
            return YiYanghtCGQFragment.newInstance(id);
        }else if (produceId.equals("PRO003006007")){//震动传感器
            return ZhenDongCGQFragment.newInstance(id);
        }else if (produceId.equals("PRO003006001")){//人体传感器
            return RenTiGYFragment.newInstance(id);
        }else if (produceId.equals("PRO003003001")){//紧急按钮
            return JinJianFragment.newInstance(id);
        }else if (produceId.equals("PRO009001001")){//六键场景按钮
            return ScenePanelSixFragment.newInstance(id);
        }else if (produceId.equals("PRO009001002")){//四键场景按钮
            return ScenePanelFragment.newInstance(id);
        }
        else {
            return ZhenDongCGQFragment.newInstance(id);
        }

    }
    public static int deviceIcon(String id) {
        if (id.equals("PRO003006006")){//一氧化碳
            return R.drawable.dev_yiyanghuatan;
        }else if (id.equals("PRO003002001")){//声光报警器
            return R.drawable.dev_shengguangbaojingqi;
        }else if (id.equals("PRO003006004")){//水浸传感器
            return R.drawable.dev_shuijin;
        }else if (id.equals("PRO003005001")){//门磁
            return R.drawable.dev_menci;
        }else if (id.equals("PRO003006007")){//震动传感器
            return R.drawable.dev_zhendong;
        }else if (id.equals("PRO002003001")){//三路开关
            return R.drawable.chumokaiguan3;
        }else if (id.equals("PRO004001001")){//温湿度
            return R.drawable.dev_wenshidu;
        }else if (id.equals("PRO003003001")){//紧急按钮
            return R.drawable.dev_jijinanniu;
        }else if (id.equals("PRO003006003")){//烟雾报警器
            return R.drawable.dev_shuiwu;
        }else if (id.equals("PRO003004001")){//智能门锁
            return R.drawable.dev_zhinengmensuo;
        }else if (id.equals("PRO009001001")){//六建
            return R.drawable.liujian;
        }else if (id.equals("PRO005001001")){//一路窗帘
            return R.drawable.dev_chuanglian;
        }else if (id.equals("PRO002002001")){//二路开关
            return R.drawable.chumokaiguan2;
        }else if (id.equals("PRO002001001")){//一路开关
            return R.drawable.chumokaiguan1;
        }else if (id.equals("PRO009001002")){//四键
            return R.drawable.sijian;
        }else if (id.equals("PRO003006001")){//人体感应
            return R.drawable.dev_rentiganying;
        }else if (id.equals("PRO006001001")){//移动插座
            return R.drawable.dev_yidongchazuo;
        }else if (id.equals("PRO001001002")){
            //主机
            return R.drawable.znzj;
        }
        return R.drawable.dev_jijinanniu;
    }
    /**
     * 去重复
     *
     * @param list
     */
    public static List<String> removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
    public static ArrayList<String> getAge() {
        ArrayList<String> datas = new ArrayList<String>();
        datas.add("18");
        datas.add("19");
        datas.add("20");
        datas.add("21");
        datas.add("22");
        datas.add("23");
        return datas;
    }
    public static String mesByStatus(String status){
        String message="";
        if (status.equals("404")){
            message="设备未注册";
        }else if (status.equals("500")){
            message="用户未登录或登录失效";
        }else if (status.equals("801")){
            message="数据错误";
        }else if (status.equals("888")){
            message="服务端接口错误";
        }
        return message;
    }
}
