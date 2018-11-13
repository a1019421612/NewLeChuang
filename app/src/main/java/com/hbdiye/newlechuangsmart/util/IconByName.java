package com.hbdiye.newlechuangsmart.util;

import com.hbdiye.newlechuangsmart.R;

public class IconByName {
    public static int drawableByName(String name){
        if (name.equals("huijia")){
            return R.drawable.huijia;
        }else if (name.equals("lijia")){
            return R.drawable.lijia;
        }else if (name.equals("yeqi")){
            return R.drawable.yeqi;
        }else if (name.equals("xican")){
            return R.drawable.xican;
        }else if (name.equals("xiuxi")){
            return R.drawable.xiuxi;
        }else if (name.equals("xiawucha")){
            return R.drawable.xiawucha;
        }else if (name.equals("zuofan")){
            return R.drawable.zuofan;
        }else if (name.equals("xizao")){
            return R.drawable.xizao;
        }else if (name.equals("shuijiao")){
            return R.drawable.shuijiao;
        }else if (name.equals("kandianshi")){
            return R.drawable.kandianshi;
        }else if (name.equals("diannao")){
            return R.drawable.diannao;
        }else if (name.equals("yinyue")){
            return R.drawable.yinyue;
        }else if (name.equals("wucan")){
            return R.drawable.wucan;
        }else if (name.equals("youxi")){
            return R.drawable.youxi;
        }else if (name.equals("huike")){
            return R.drawable.huike;
        }else if (name.equals("xiyifu")){
            return R.drawable.xiyifu;
        }else if (name.equals("kaihui")){
            return R.drawable.kaihui;
        }else if (name.equals("huazhuang")){
            return R.drawable.huazhuang;
        }else if (name.equals("dushu")){
            return R.drawable.dushu;
        }else if (name.equals("qichuang")){
            return R.drawable.qichuang;
        }
        return R.drawable.huijia;
    }

    public static String nameByDrawable(int name){
        if (name==R.drawable.huijia){
            return "huijia";
        }else if (name==R.drawable.lijia){
            return "lijia";
        }else if (name==R.drawable.yeqi){
            return "yeqi";
        }else if (name==R.drawable.xican){
            return "xican";
        }else if (name==R.drawable.xiuxi){
            return "xiuxi";
        }else if (name==R.drawable.xiawucha){
            return "xiawucha";
        }else if (name==R.drawable.zuofan){
            return "zuofan";
        }else if (name==R.drawable.xizao){
            return "xizao";
        }else if (name==R.drawable.shuijiao){
            return "shuijiao";
        }else if (name==R.drawable.kandianshi){
            return "kandianshi";
        }else if (name==R.drawable.diannao){
            return "diannao";
        }else if (name==R.drawable.yinyue){
            return "yinyue";
        }else if (name==R.drawable.wucan){
            return "wucan";
        }else if (name==R.drawable.youxi){
            return "youxi";
        }else if (name==R.drawable.huike){
            return "huike";
        }else if (name==R.drawable.xiyifu){
            return "xiyifu";
        }else if (name==R.drawable.kaihui){
            return "kaihui";
        }else if (name==R.drawable.huazhuang){
            return "huazhuang";
        }else if (name==R.drawable.dushu){
            return "dushu";
        }else if (name==R.drawable.qichuang){
            return "qichuang";
        }
        return "huijia";
    }
}
