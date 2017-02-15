package com.yigu.shop.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.sort.SortItemTwoAdapter;
import com.yigu.shop.commom.result.MapiSortChildResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2017/1/16.
 */
public class SortItemTwoLayout extends LinearLayout {
    @Bind(R.id.sort_tv)
    TextView sortTv;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.sort_two)
    LinearLayout sort_two;

    private Context mContext;
    private View view;
    List<MapiSortChildResult> list;
    SortItemTwoAdapter mAdapter;
    MapiSortChildResult mapiSortChildResult;
    public SortItemTwoLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SortItemTwoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SortItemTwoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_sort_item_two, this);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, DPUtil.dip2px(10), Color.parseColor("#ffffff")));//分割线
        mAdapter = new SortItemTwoAdapter(mContext, list);
        recyclerView.setAdapter(mAdapter);
        initListener();
    }

    public void load(MapiSortChildResult mapiSortChildResult) {
        this.mapiSortChildResult = mapiSortChildResult;
        list.clear();
        if(null!=mapiSortChildResult){
            sortTv.setText(mapiSortChildResult.getName());
            if(null==mapiSortChildResult.getList()||mapiSortChildResult.getList().isEmpty()){
                return;
            }
            list.addAll(mapiSortChildResult.getList());
            mAdapter.notifyDataSetChanged();
        }

    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiSortChildResult mapiSortChildResult1 = list.get(position);
                ControllerUtil.go2ItemList(mapiSortChildResult1.getId(),mapiSortChildResult1.getName());
            }
        });
    }

    @OnClick(R.id.sort_two)
    public void onClick() {
        ControllerUtil.go2ItemList(mapiSortChildResult.getId(),mapiSortChildResult.getName());
    }
}
