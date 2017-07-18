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
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.util.ControllerUtil;
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
        initListener();
    }

    public ComItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initListener();
    }

    public ComItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initListener();
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

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiResourceResult item = mList.get(position);
                switch (item.getId()) {
                    case ShopDataSource.TYPE_qiuzhi:
                        ControllerUtil.go2ComJobList();
                        break;
                    case ShopDataSource.TYPE_mendian:
                        ControllerUtil.go2ServiceList();
                        break;
                    case ShopDataSource.TYPE_anli:
                        break;
                    case ShopDataSource.TYPE_wenti:
                        break;
                    case ShopDataSource.TYPE_geshujijian:
                        ControllerUtil.go2ComChange();
                        break;
                    case ShopDataSource.TYPE_kuaisu:
                        ControllerUtil.go2MasterList();
                        break;
                }

            }
        });
    }

    public void load() {
        mList.clear();
        mList.addAll(ShopDataSource.getComItemResource());
        mAdapter.notifyDataSetChanged();
    }

}
