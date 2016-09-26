package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.ItemTwoAdapter;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/23.
 */
public class HomeItemLayout extends RelativeLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private Context mContext;
    private View view;
    ItemTwoAdapter mAdapter;
    List<MapiItemResult> mList = new ArrayList<>();
    public HomeItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_item, this);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext,OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemTwoAdapter(mContext,mList);
        recyclerView.setAdapter(mAdapter);
        initListener();
    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MainToast.showShortToast(position+"");
            }
        });
    }

    public void load(List<MapiItemResult> list){
       /* if(!list.isEmpty()){
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }*/

    }

}
