package com.yigu.shop.fragment.index;

import android.os.Bundle;
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

/**
 * Created by brain on 2016/9/26.
 */
public class IndexToolFragment extends BaseFrag{
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    HomeAdapter mAdapter;
    List<IndexData> mList = new ArrayList<>();
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";
    String cat_id = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_device, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        initListener();
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

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;

    }

    private void initListener(){

    }

    public void load() {
        mList.clear();
        mList.add(new IndexData(0,TOOL,new ArrayList<MapiResourceResult>()));
        mList.add(new IndexData(1,ITEM,cat_id));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
