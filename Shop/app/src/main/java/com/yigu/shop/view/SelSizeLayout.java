package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.adapter.SelSizeAdapter;
import com.yigu.shop.commom.result.MapiAttrResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DebugLog;

import java.text.DecimalFormat;
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
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.good_sn)
    TextView goodSN;

    List<MapiAttrResult> list = new ArrayList<>();
    SelSizeAdapter mAdapter;

    private String market_price="";
    private String goods_price="";
    private String goods_number="";
    private String goods_attr="";
    private String goods_attr_id="";

    MapiItemResult itemResult;

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


        mAdapter.setSelSizeListener(new SelSizeAdapter.SelSizeInterface() {
            @Override
            public void sizeDetail(String[] goodsAttrs, String[] prices,String[] attrIdStrs) {
                double pricesFloat = 0;
                if(null!=goodsAttrs){
                    goods_attr = "";
                    for(String str : goodsAttrs){
                        if(TextUtils.isEmpty(goods_attr))
                            goods_attr = TextUtils.isEmpty(str)?"":str;
                        else
                            goods_attr +=  (TextUtils.isEmpty(str)?"":"  " +str);
                    }

                }

                if(null!=prices){
                    for(String str : prices)
                        pricesFloat += Double.parseDouble(TextUtils.isEmpty(str)?"0":str);
                }

                if(null!=attrIdStrs){
                    goods_attr_id = "";
                    for(String str : attrIdStrs){
                        if(TextUtils.isEmpty(goods_attr_id))
                            goods_attr_id = TextUtils.isEmpty(str)?"":str;
                        else
                            goods_attr_id +=  (TextUtils.isEmpty(str)?"":"," +str);
                    }
                }

                DebugLog.i("goods_attr:"+goods_attr);
                DebugLog.i("pricesFloat:"+pricesFloat);
                DebugLog.i("goods_attr_id:"+goods_attr_id);



                try{
                    if(null!=itemResult){
                        Double priceff = Double.parseDouble(TextUtils.isEmpty(itemResult.getShop_price())?"0":itemResult.getShop_price());
                        priceff += pricesFloat;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        price.setText(df.format(priceff));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }


    public void load(List<MapiAttrResult> attrs, MapiItemResult itemResult) {
        list.clear();
        list.addAll(attrs);
        if(null!=mAdapter)
            mAdapter.notifyDataSetChanged();

        this.itemResult = itemResult;

        if(null!=this.itemResult){
            price.setText(TextUtils.isEmpty(itemResult.getShop_price())?"0":itemResult.getShop_price());
            goodSN.setText(TextUtils.isEmpty(itemResult.getGoods_sn())?"":"商品编号："+itemResult.getGoods_sn());
        }

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
        market_price = TextUtils.isEmpty(itemResult.getMarket_price())?"0":itemResult.getMarket_price();
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
        goods_price = TextUtils.isEmpty(price.getText().toString())?"0":price.getText().toString();
        return goods_price;
    }



    public String getGoods_attr_id() {
        return goods_attr_id;
    }



}
