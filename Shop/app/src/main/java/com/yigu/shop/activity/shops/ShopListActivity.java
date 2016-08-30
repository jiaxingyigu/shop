package com.yigu.shop.activity.shops;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopListActivity extends BaseActivity {

    @Bind(R.id.rvShop)
    RecyclerView rvShop;
    @Bind(R.id.BSRLShop)
    BestSwipeRefreshLayout BSRLShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        rvShop.setHasFixedSize(true);
        rvShop.addItemDecoration(new DividerListItemDecoration(this,OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#ffffff")));
        rvShop.setLayoutManager(manager);
    }
}
