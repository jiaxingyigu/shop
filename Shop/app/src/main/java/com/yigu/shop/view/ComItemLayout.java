package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.community.ComItemAdapter;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.util.ShopDataSource;
import com.yigu.shop.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/31.
 */
public class ComItemLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;

    ComItemAdapter mAdapter;
    List<MapiResourceResult> mList;
    public ComItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ComItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ComItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_com_item, this);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, DPUtil.dip2px(0.5f),getResources().getColor(R.color.divider_line)));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        mAdapter = new ComItemAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);

    }

    public void load() {
        mList.clear();
        mList.addAll(ShopDataSource.getComItemResource());
        mAdapter.notifyDataSetChanged();
    }

}
