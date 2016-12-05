package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.CommunityActivity;
import com.yigu.shop.activity.ForgetActivity;
import com.yigu.shop.activity.LoginActivity;
import com.yigu.shop.activity.MainActivity;
import com.yigu.shop.activity.ProductDetailActivity;
import com.yigu.shop.activity.RegisterActivity;
import com.yigu.shop.activity.addr.AddAddrActivity;
import com.yigu.shop.activity.addr.ManageAddrActivity;
import com.yigu.shop.activity.addr.SelAddrActivity;
import com.yigu.shop.activity.community.ComSearchActivity;
import com.yigu.shop.activity.order.MyOrderActivity;
import com.yigu.shop.activity.order.OderDetailActivity;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.activity.purcase.PurcaseActivity;
import com.yigu.shop.activity.shops.ShopDetailActivity;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;

/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {

    /**
     * 由主页进入购物车
     */
    public static void go2Purcase() {
        Intent intent = new Intent(AppContext.getInstance(), PurcaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 商品详情
     */
    public static void go2ProductDetail(MapiItemResult itemResult) {
        Intent intent = new Intent(AppContext.getInstance(), ProductDetailActivity.class);
        intent.putExtra("item",itemResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 店铺详情
     */
    public static void go2ShopDetail(MapiShopResult result){
        Intent intent = new Intent(AppContext.getInstance(), ShopDetailActivity.class);
        intent.putExtra("item",result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 地址管理
     */
    public static void go2ManageAddr() {
        Intent intent = new Intent(AppContext.getInstance(),ManageAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 添加地址
     */
    public static void go2AddAddr() {
        Intent intent = new Intent(AppContext.getInstance(),AddAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 我的订单
     */
    public static void go2MyOrder() {
        Intent intent = new Intent(AppContext.getInstance(),MyOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 首页进入社区
     */
    public static void go2Community() {
        Intent intent = new Intent(AppContext.getInstance(), CommunityActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }
    /**
    ** 注册
    */
    public static void go2Register() {
        Intent intent = new Intent(AppContext.getInstance(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     ** 忘记密码
     */
    public static void go2Forget() {
        Intent intent = new Intent(AppContext.getInstance(), ForgetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 由登录页进入首页
     */
    public static void go2Main() {
        Intent intent = new Intent(AppContext.getInstance(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 进入登录
     */
    public static void go2Login(){
        Intent intent = new Intent(AppContext.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 社区-搜索
     */
    public static void go2ComSearch() {
        Intent intent = new Intent(AppContext.getInstance(), ComSearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 选择收货地址
     */
    public static void go2SelAddr() {
        Intent intent = new Intent(AppContext.getInstance(), SelAddrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     *
     */
    public static void go2OderDetail() {
        Intent intent = new Intent(AppContext.getInstance(),OderDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

}
