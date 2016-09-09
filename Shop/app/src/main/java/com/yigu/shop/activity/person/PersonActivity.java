package com.yigu.shop.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.activity.collect.CollectProductActivity;
import com.yigu.shop.activity.collect.CollectShopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_celProduct)
    RelativeLayout rlCelProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

    }

    private void initListener() {

    }

    @OnClick({R.id.iv_back, R.id.rl_celProduct, R.id.rl_celShop})
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_celProduct:
                i.setClass(PersonActivity.this, CollectProductActivity.class);
                startActivity(i);
                break;
            case R.id.rl_celShop:
                i.setClass(PersonActivity.this, CollectShopActivity.class);
                startActivity(i);
                break;
        }
    }

}
