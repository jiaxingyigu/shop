package com.yigu.shop.activity.collect;

import android.os.Bundle;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;

import butterknife.ButterKnife;

public class CollectShopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_shop);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

    }

    private void initListener() {

    }
}
