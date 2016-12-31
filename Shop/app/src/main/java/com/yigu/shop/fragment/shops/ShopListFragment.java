package com.yigu.shop.fragment.shops;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.shops.ShopAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShopListFragment extends BaseFrag {

    @Bind(R.id.tabShop)
    TabLayout tabShop;
    @Bind(R.id.rvShop)
    RecyclerView rvShop;
    @Bind(R.id.BSRLShop)
    BestSwipeRefreshLayout BSRLShop;

    ShopAdapter mAdapter;
    List<MapiShopResult> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_shop_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        rvShop.setHasFixedSize(true);
        rvShop.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#000000")));
        rvShop.setLayoutManager(manager);
        mAdapter = new ShopAdapter(getActivity(), mList);
        rvShop.setAdapter(mAdapter);
        tabShop.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabShop.addTab(tabShop.newTab().setText("全部"));
        tabShop.addTab(tabShop.newTab().setText("设备"));
        tabShop.addTab(tabShop.newTab().setText("工具"));
        tabShop.addTab(tabShop.newTab().setText("修补耗材"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
