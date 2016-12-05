package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.adapter.SelSizeAdapter;
import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/11/25.
 */
public class SelSizeLayout extends RelativeLayout {
    private Context mContext;
    private View view;

    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.purcaseSheetLayout)
    PurcaseSheetLayout purcaseSheetLayout;

    List<MapiResourceResult> list = new ArrayList<>();
    SelSizeAdapter mAdapter;

    private String market_price="";
    private String goods_price="";
    private String goods_number="";
    private String goods_attr="";
    private String goods_attr_id="";

    public SelSizeLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SelSizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SelSizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_sel_size, this);
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new SelSizeAdapter(mContext, list);
        recyclerView.setAdapter(mAdapter);

    }


    @OnClick(R.id.close_iv)
    public void onClick() {
        if(null!=onHideListener)
            onHideListener.hide();
    }

    private OnHideListener onHideListener;

    public interface OnHideListener{
        void hide();
    }

    public void setOnHideListener(OnHideListener onHideListener){
        this.onHideListener = onHideListener;
    }

    public String getMarket_price() {
        return market_price;
    }


    public String getGoods_attr() {
        return goods_attr;
    }



    public String getGoods_number() {
        goods_number = purcaseSheetLayout.getNum()+"";
        return goods_number;
    }


    public String getGoods_price() {
        return goods_price;
    }



    public String getGoods_attr_id() {
        return goods_attr_id;
    }


}
