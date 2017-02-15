package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.HelpAdapter;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiHelpResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    HelpAdapter mAdapter;

    List<IndexData> mList;
    List<MapiHelpResult> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("购物帮助");

        mList = new ArrayList<>();
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HelpAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void load(){
        showLoading();
        ItemApi.help(this, new RequestCallback<List<MapiHelpResult>>() {
            @Override
            public void success(List<MapiHelpResult> success) {
                hideLoading();
                if(success.isEmpty())
                    return;
                list.addAll(success);
                notifySortData(list);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            list.clear();
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    private void notifySortData(List<MapiHelpResult> data){
        mList.clear();
        int num=0;
        if(data==null||data.size()==0){
            num = 0;
        }else{
            for (MapiHelpResult ware : data) {
                mList.add(new IndexData(num++,"ITEM_ONE",ware));
                for (int i=0;i<ware.getArticle().size();i++) {
                    mList.add(new IndexData(num++,"ITEM_TWO", ware.getArticle().get(i)));
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
