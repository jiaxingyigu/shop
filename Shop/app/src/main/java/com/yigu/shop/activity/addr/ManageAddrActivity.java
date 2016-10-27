package com.yigu.shop.activity.addr;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.ItemTwoAdapter;
import com.yigu.shop.adapter.addr.ManageAddrAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageAddrActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    ManageAddrAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_addr);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("管理收货地址");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ManageAddrAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.back, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add:
                ControllerUtil.go2AddAddr();
                break;
        }
    }
}
