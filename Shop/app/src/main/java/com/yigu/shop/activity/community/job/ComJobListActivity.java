package com.yigu.shop.activity.community.job;

import android.os.Bundle;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;

import butterknife.ButterKnife;

public class ComJobListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_job_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

    }

}
