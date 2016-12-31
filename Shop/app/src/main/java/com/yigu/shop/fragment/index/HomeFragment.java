package com.yigu.shop.fragment.index;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yigu.shop.R;
import com.yigu.shop.activity.person.PersonActivity;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
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
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";

    String cat_id = "";
    String typeStr = "";
    List<MapiResourceResult> list = new ArrayList<>();;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
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

    private void initListener(){
        mAdapter.setTypeListener(new HomeAdapter.TypeListener() {
            @Override
            public void getType(String type) {
                typeStr = type;
                load();
            }
        });
    }

    public void load() {
        DebugLog.i("load===>"+cat_id);
        mList.clear();
        if(null!=list)
             mList.add(new IndexData(0,SCROLL,list));
        mList.add(new IndexData(1,TOOL,new ArrayList<MapiResourceResult>()));
        MapiResourceResult resourceResult = new MapiResourceResult();
        resourceResult.setType(typeStr);
        resourceResult.setCat_id(cat_id);
        mList.add(new IndexData(2,ITEM,resourceResult));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();


    }

    public void setCat_id(String cat_id,List<MapiResourceResult> list) {
        DebugLog.i("cat_id===>"+cat_id);
        this.cat_id = cat_id;
        this.list = list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
