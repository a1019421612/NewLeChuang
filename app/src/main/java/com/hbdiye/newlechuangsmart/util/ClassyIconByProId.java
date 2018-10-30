package com.hbdiye.newlechuangsmart.util;

import android.support.v4.app.Fragment;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.devicefragment.ChaZuoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ChuangLianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.DianjiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanOneFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanThreeFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanTwoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.MenCiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ShengGuangBJQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ShuiJinCGQFragment;
import com.hbdiye.newlechuangsmart.devicefragment.WenShiDuFragment;
import com.hbdiye.newlechuangsmart.devicefragment.YanWuCGQFragment;

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
        }else if (id.contains("PRO002")) {//照明设备
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
        if (produceId.contains("PRO002001001")) {//一路开关
            return KaiGuanOneFragment.newInstance(id);
        }else if (produceId.contains("PRO002002001")){//二路开关
            return KaiGuanTwoFragment.newInstance(id);
        }else if (produceId.contains("PRO002003001")){//三路开关
            return KaiGuanThreeFragment.newInstance(id);
        }else if (produceId.contains("PRO003002001")){//声光报警器
            return ShengGuangBJQFragment.newInstance(id);
        }else if (produceId.contains("PRO003004001")){//智能门锁
            return MenCiFragment.newInstance(id);
        }else if (produceId.contains("PRO003005001")){//门磁
            return MenCiFragment.newInstance(id);
        }else if (produceId.contains("PRO004001001")){//温湿度检测器
            return WenShiDuFragment.newInstance(id);
        }else if (produceId.contains("PRO005002001")){//窗帘电机
            return DianjiFragment.newInstance(id);
        }else if (produceId.contains("PRO005001001")){//一路窗帘开关
            return DianjiFragment.newInstance(id);
        }else if (produceId.contains("PRO006001001")){//五孔插座
            return ChaZuoFragment.newInstance(id);
        }else {
            return null;
        }

//        else if (id.equals("PRO003006004")) {//传感器
//            return new ShuiJinCGQFragment();
//        }  else if (id.equals("PRO006001001")){//插座
//            return new ChaZuoFragment();
//        }else if (id.equals("PRO003004001")){//指芯智能门锁
//            return new MenCiFragment();
//        }else if (id.equals("PRO005002001")){//粤奇胜窗帘电机
//            return new DianjiFragment();
//        }
//        else {
//            return new ChuangLianFragment();
//        }
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

}
