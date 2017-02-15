package com.yigu.shop.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.adapter.order.HisOrderAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiOrderItem;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HisOrderDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.consignee)
    TextView consignee;
    @Bind(R.id.tel)
    TextView tel;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.addr_root)
    LinearLayout addrRoot;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.time_ll)
    LinearLayout time_ll;
    @Bind(R.id.pay_time)
    TextView payTime;
    @Bind(R.id.shipping_time)
    TextView shippingTime;


    MapiOrderResult mapiOrderResult;

    HisOrderAdapter mAdapter;

    List<IndexData> mList = new ArrayList<>();
    MapiOrderResult detailItem;
    @Bind(R.id.pay_ll)
    LinearLayout payLl;
    @Bind(R.id.shipping_ll)
    LinearLayout shippingLl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_order_detail);
        ButterKnife.bind(this);
        if (null != getIntent())
            mapiOrderResult = (MapiOrderResult) getIntent().getSerializableExtra("item");
        if (null != mapiOrderResult) {
            ButterKnife.bind(this);
            initView();
            load();
        }

    }

    private void initView() {

        if (mapiOrderResult.getType().equals("await_ship")) {
            center.setText("待发货");
        } else if (mapiOrderResult.getType().equals("shipped")) {
            center.setText("待收货");
        } else if (mapiOrderResult.getType().equals("finished")) {
            center.setText("完成");
        } else {
            center.setText("待付款");
        }
        back.setImageResource(R.mipmap.back);

        detailItem = new MapiOrderResult();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HisOrderAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {

    }

    public void load() {
        showLoading();
        ItemApi.orderdetail(this, mapiOrderResult.getOrder_id(), new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                Gson gson = new Gson();
                detailItem = gson.fromJson(success.getJSONObject("data").toJSONString(), new TypeToken<MapiOrderResult>() {
                }.getType());
                if (null != detailItem) {

                    if (TextUtils.isEmpty(detailItem.getPay_time())) {
                        payLl.setVisibility(View.GONE);
                    } else {
                        payLl.setVisibility(View.VISIBLE);
                        payTime.setText("付款时间：" + detailItem.getPay_time());
                    }

                    if (TextUtils.isEmpty(detailItem.getShipping_time())) {
                        shippingLl.setVisibility(View.GONE);
                    } else {
                        shippingLl.setVisibility(View.VISIBLE);
                        shippingTime.setText("发货时间：" + detailItem.getShipping_time());
                    }

                    refreshItemList(detailItem);
                    refreshAddress(detailItem);


                }


            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void refreshAddress(MapiOrderResult data) {
        if (null != data.getAddress()) {
            MapiAddrResult mapiAddrResult = data.getAddress();
            consignee.setText(mapiAddrResult.getName());
            tel.setText(mapiAddrResult.getMobile());
            address.setText(mapiAddrResult.getAddress());
        }
    }

    private void refreshItemList(MapiOrderResult data) {
        mList.clear();
        int num = 0;
        if (data == null) {
            num = 0;
        } else {
            data.setType(mapiOrderResult.getType());
            mList.add(new IndexData(num++, "head", data));
            for (MapiOrderItem mapiOrderItem : data.getGoods_list()) {
                mList.add(new IndexData(num++, "item", mapiOrderItem));
            }
            mList.add(new IndexData(num++, "bottom", data));
            mList.add(new IndexData(num++, "divider", new Object()));
        }
        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
