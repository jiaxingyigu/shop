package com.yigu.shop.util;

import android.content.Intent;

import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.commom.application.AppContext;

/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {

    /**
     * 由主页进入日常巡查
     */
    public static void go2ProductList() {
        Intent intent = new Intent(AppContext.getInstance(), ProductListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }


}
