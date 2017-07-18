package com.yigu.shop.activity;

import android.os.Bundle;


import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.util.ControllerUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                if (!getversionCode().equals(userSP.getUserGuide())) {
                    ControllerUtil.go2Guide();
                    finish();
                }else{
                    ControllerUtil.go2Community();
                    finish();
                }

            }
        };
        timer.schedule(task,2*1000);
    }
}
