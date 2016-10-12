package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.ProductDetailActivity;
import com.yigu.shop.activity.addr.ManageAddrActivity;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.activity.purcase.PurcaseActivity;
import com.yigu.shop.activity.shops.ShopDetailActivity;
import com.yigu.shop.commom.application.AppContext;
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
    public static void go2ProductDetail() {
        Intent intent = new Intent(AppContext.getInstance(), ProductDetailActivity.class);
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

}
