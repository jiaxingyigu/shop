package com.yigu.shop.util;


import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/7/29.
 */
public class ShopDataSource {

    public static final int TYPE_chitai= 0x01;
    public static final int TYPE_paopian = 0x02;
    public static final int TYPE_doudon= 0x03;
    public static final int TYPE_qita = 0x04;
    public static final int TYPE_wanxue = 0x05;
    public static final int TYPE_zhuanye = 0x06;
    public static final int TYPE_dipan= 0x07;
    public static final int TYPE_shebei = 0x08;

    public static final int TYPE_qiuzhi = 0x09;
    public static final int TYPE_mendian = 0x10;
    public static final int TYPE_anli= 0x11;
    public static final int TYPE_wenti = 0x12;
    public static final int TYPE_geshujijian = 0x13;
    public static final int TYPE_kuaisu= 0x14;

    /**
     * 社区案例
     * @return
     */
    public static List<MapiResourceResult> getRootResource(){
        List<MapiResourceResult> list = new ArrayList<>();
        list.add(new MapiResourceResult(TYPE_chitai,"吃胎案例"));
        list.add(new MapiResourceResult(TYPE_paopian,"跑偏案例"));
        list.add(new MapiResourceResult(TYPE_doudon,"抖动案例"));
        list.add(new MapiResourceResult(TYPE_qita,"其它案例"));
        list.add(new MapiResourceResult(TYPE_wanxue,"玩学不误"));
        list.add(new MapiResourceResult(TYPE_zhuanye,"专业测试"));
        list.add(new MapiResourceResult(TYPE_dipan,"地盘培训"));
        list.add(new MapiResourceResult(TYPE_shebei,"设备培训"));
        return list;
    }

    /**
     * 社区模块
     * @return
     */
    public static List<MapiResourceResult> getComItemResource(){
        List<MapiResourceResult> list = new ArrayList<>();
        list.add(new MapiResourceResult(TYPE_qiuzhi,"求职招聘","优秀人才的选择"));
        list.add(new MapiResourceResult(TYPE_mendian,"门店评价","寻找适合的门店"));
        list.add(new MapiResourceResult(TYPE_anli,"案例分析","案例具体分析"));
        list.add(new MapiResourceResult(TYPE_wenti,"我要提问","发布我的问题"));
        list.add(new MapiResourceResult(TYPE_geshujijian,"各抒己见","各种意见探讨"));
        list.add(new MapiResourceResult(TYPE_kuaisu,"快速发表","快速发表意见"));
        return list;
    }

}
