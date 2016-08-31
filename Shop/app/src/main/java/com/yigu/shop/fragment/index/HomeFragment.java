package com.yigu.shop.fragment.index;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFrag {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    HomeAdapter mAdapter;
    List<IndexData> mList = new ArrayList<>();

    private final static String SCROLL = "SCROLL";
    private final static String HOST = "HOST";
    private final static String TOOL = "TOOL";
    private final static String BSET_SHOP = "BSET_SHOP";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(),OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(getActivity(),mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load() {
        mList.clear();
        mList.add(new IndexData(0,SCROLL,new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(2,TOOL,new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1,HOST,new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(3,BSET_SHOP,new ArrayList<MapiShopResult>()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.search_iv, R.id.purcase_iv, R.id.person_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv:
                break;
            case R.id.purcase_iv:
                break;
            case R.id.person_iv:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
