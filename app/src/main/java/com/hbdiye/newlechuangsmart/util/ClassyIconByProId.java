package com.hbdiye.newlechuangsmart.util;

import android.support.v4.app.Fragment;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.devicefragment.ChaZuoFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ChuangLianFragment;
import com.hbdiye.newlechuangsmart.devicefragment.DianjiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.KaiGuanThreeFragment;
import com.hbdiye.newlechuangsmart.devicefragment.MenCiFragment;
import com.hbdiye.newlechuangsmart.devicefragment.ShuiJinCGQFragment;

import java.util.LinkedHashSet;
import java.util.List;

public class ClassyIconByProId {
    private static int[] tabIcons = {
            R.drawable.icon_deng_n,
            R.drawable.icon_jcq_n,
            R.drawable.icon_jj_n,
            R.drawable.icon_cl_n
    };
    private static int[] tabIconsPressed = {
            R.drawable.icon_deng_p,
            R.drawable.icon_jcq_p,
            R.drawable.icon_jj_p,
            R.drawable.icon_cl_p
    };

    /**
     * 根据id判断要显示的图标
     *
     * @param id
     * @return
     */
    public static int iconNormal(String id) {
        if (id.equals("PRO002003001")) {//开关
            return R.drawable.icon_deng_n;
        }else if (id.equals("PRO003006004")) {//传感器
            return R.drawable.icon_jcq_n;
        } else if (id.equals("PRO006001001")){//插座
            return R.drawable.icon_jcq_n;
        }else if (id.equals("PRO003004001")){//指芯智能门锁
            return R.drawable.icon_jj_n;
        }else if (id.equals("PRO005002001")){//粤奇胜窗帘电机
            return R.drawable.icon_cl_n;
        }
        else {
            return R.drawable.icon_jj_n;
        }
    }

    /**
     * 根据id判断要显示的图标
     *
     * @param id
     * @return
     */
    public static int iconPressed(String id) {
        if (id.equals("PRO002003001")) {//开关
            return R.drawable.icon_deng_p;
        } else if (id.equals("PRO003006004")) {//传感器
            return R.drawable.icon_jcq_p;
        } else if (id.equals("PRO006001001")){//插座
            return R.drawable.icon_jcq_p;
        }else if (id.equals("PRO003004001")){//指芯智能门锁
            return R.drawable.icon_jj_p;
        }else if (id.equals("PRO005002001")){//粤奇胜窗帘电机
            return R.drawable.icon_cl_p;
        }
        else{
            return R.drawable.icon_jj_p;
        }
    }

    /**
     * 根据id判断要显示的详情界面
     *
     * @param id
     * @return
     */
    public static Fragment fragmentById(String id) {
        if (id.equals("PRO002003001")) {//开关
            return new KaiGuanThreeFragment();
        } else if (id.equals("PRO003006004")) {//传感器
            return new ShuiJinCGQFragment();
        }  else if (id.equals("PRO006001001")){//插座
            return new ChaZuoFragment();
        }else if (id.equals("PRO003004001")){//指芯智能门锁
            return new MenCiFragment();
        }else if (id.equals("PRO005002001")){//粤奇胜窗帘电机
            return new DianjiFragment();
        }
        else {
            return new ChuangLianFragment();
        }
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
