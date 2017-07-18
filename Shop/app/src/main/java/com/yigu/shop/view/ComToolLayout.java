package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.community.ComItemAdapter;
import com.yigu.shop.adapter.community.ComToolAdapter;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.util.ShopDataSource;
import com.yigu.shop.widget.DividerGridItemDecoration;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/31.
 */
public class ComToolLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;

    ComToolAdapter mAdapter;
    List<MapiResourceResult> mList;
    public ComToolLayout(Context context) {
        super(context);
        mContext = context;
        initView();
        initListener();
    }

    public ComToolLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initListener();
    }

    public ComToolLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initListener();
    }

    private void initView() {
        if (isInEditMode())
            return;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_com_tool, this);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(mContext,4);
        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, DPUtil.dip2px(0.5f),getResources().getColor(R.color.divider_line)));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        mAdapter = new ComToolAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiResourceResult item = mList.get(position);
                String title = "";
                String boardId = "";
                switch (item.getId()){
                    case ShopDataSource.TYPE_chitai:
                        title = "吃胎案例";
                        boardId = "48";
                        break;
                    case ShopDataSource.TYPE_paopian:
                        title = "跑偏案例";
                        boardId = "47";
                        break;
                    case ShopDataSource.TYPE_doudon:
                        title = "抖动案例";
                        boardId = "49";
                        break;
                    case ShopDataSource.TYPE_qita:
                        title = "其他案例";
                        boardId = "50";
                        break;
                    case ShopDataSource.TYPE_wanxue:
                        title = "读书社";
                        boardId = "41";
                        break;
                    case ShopDataSource.TYPE_zhuanye:
                        title = "视频教程";
                        boardId = "42";
                        break;
                    case ShopDataSource.TYPE_dipan:
                        title = "底盘培训";
                        boardId = "37";
                        break;
                    case ShopDataSource.TYPE_shebei:
                        title = "设配培训";
                        boardId = "2";
                        break;
                }
                ControllerUtil.go2MunityList(title,boardId);
            }
        });
    }

    public void load() {
        mList.clear();
        mList.addAll(ShopDataSource.getRootResource());
        mAdapter.notifyDataSetChanged();
    }

}
