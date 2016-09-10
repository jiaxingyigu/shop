package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.ProductDetailActivity;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.activity.purcase.PurcaseActivity;
import com.yigu.shop.commom.application.AppContext;

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

}
