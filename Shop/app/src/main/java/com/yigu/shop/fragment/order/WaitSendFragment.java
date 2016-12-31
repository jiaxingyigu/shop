package com.yigu.shop.fragment.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.order.OrderAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class WaitSendFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    List<IndexData> mList = new ArrayList<>();
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
    private Integer ISNEXT = 0;
    int count = 0;

    OrderAdapter mAdapter;

    public WaitSendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderAdapter(getActivity(),mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
