/**
 * www.ypn.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */
package com.yigu.shop.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yigu.shop.activity.LoginActivity;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.sharedpreferences.UserSP;

/**
 * @Filename LoginBroadcast.java
 * @Description
 * @Version 1.0
 * @Author kuci
 */
public class LoginBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        UserSP userSP = new UserSP(AppContext.getInstance());
        userSP.clearUserData();

        Intent it = new Intent(AppContext.getInstance(), LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);

       /* Intent it = new Intent(context, LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//为了使从登录页面出来的页面删除掉
        context.startActivity(it);*/
    }

}