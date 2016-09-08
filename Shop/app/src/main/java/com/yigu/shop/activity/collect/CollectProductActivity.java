package com.yigu.shop.activity.collect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.product.ProductAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectProductActivity extends BaseActivity {

    @Bind(R.id.lay_header)
    RelativeLayout layHeader;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_product);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#696969")));
        recyclerView.setLayoutManager(manager);
        mAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {

    }
}
