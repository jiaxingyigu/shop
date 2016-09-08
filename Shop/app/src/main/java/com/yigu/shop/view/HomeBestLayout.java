package com.yigu.shop.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.activity.shops.ShopListActivity;
import com.yigu.shop.adapter.shops.ShopAdapter;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeBestLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;
    ShopAdapter mAdapter;
    private List<MapiShopResult> mList = new ArrayList<>();
    public HomeBestLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeBestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeBestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_best, this);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext,OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopAdapter(mContext,mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load(List<MapiShopResult> list) {
//        mList.clear();
//        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.more)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(mContext, ShopListActivity.class);
        mContext.startActivity(intent);
    }
}
