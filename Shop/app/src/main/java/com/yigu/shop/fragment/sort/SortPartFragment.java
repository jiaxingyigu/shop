package com.yigu.shop.fragment.sort;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.sort.SortAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.widget.DividerGridItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class SortPartFragment extends BaseFrag{
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    SortAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        initListener();
        return view;
    }

    private void initView() {
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), DPUtil.dip2px(18), Color.parseColor("#ffffff")));//分割线
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new SortAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MainToast.showShortToast("第"+position+"张");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
