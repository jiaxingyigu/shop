package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.yigu.shop.R;
import com.yigu.shop.adapter.logistics.LogisticsAdapter;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiShipResult;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/9.
 */
public class LogisticsLayout extends LinearLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private View view;

    List<MapiShipResult> list = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    int count = 0;

    LogisticsAdapter mAdapter;

    public LogisticsLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public LogisticsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_logistics, this);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new LogisticsAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        initListener();
    }

    public void load(List<MapiShipResult> shipList) {
        mList.clear();
        list.clear();
        list.addAll(shipList);
        loadData();
    }

    private void loadData(){
        mList.clear();
        count = 0;
        if (list == null || list.size() == 0) {
            count = 0;
        } else {
            for (int i=0;i<list.size();i++) {
                MapiShipResult mapiShipResult = list.get(i);
                mList.add(new IndexData(count++, "item", mapiShipResult));
                if(i<list.size()){
                    mList.add(new IndexData(count++, "divider", new Object()));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               for(int i=0;i<mList.size();i++){
                   IndexData indexData = mList.get(i);
                   if("item".equals(indexData.getType())){
                       MapiShipResult mapiShipResult = (MapiShipResult) indexData.getData();
                       if(i==position) {
                           mapiShipResult.setSel(true);
                           if(null!=shipSelListener)
                               shipSelListener.shipSel(mapiShipResult);
                       }else
                           mapiShipResult.setSel(false);
                   }
               }
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    public interface ShipSelListener{
        void shipSel(MapiShipResult mapiShipResult);
    }

    private ShipSelListener shipSelListener;

    public void setShipSelListener(ShipSelListener shipSelListener){
        this.shipSelListener = shipSelListener;
    }

}
