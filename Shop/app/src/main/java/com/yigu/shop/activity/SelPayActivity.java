package com.yigu.shop.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelPayActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pay);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("选择支付方式");
    }

    @OnClick(R.id.back)
    public void onClick() {
    }
}
