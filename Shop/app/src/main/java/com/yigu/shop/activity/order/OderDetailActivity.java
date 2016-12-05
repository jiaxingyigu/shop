package com.yigu.shop.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.order.OrderAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/11/30.
 */
public class OderDetailActivity extends BaseActivity {

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
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.account)
    TextView account;

    private List<MapiCartResult> mList = new ArrayList<>();
    OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
        load();
    }

    private void initView() {
        center.setText("确认订单");
        back.setImageResource(R.mipmap.back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderAdapter(this,mList);
        recyclerView.setAdapter(mAdapter);

    }

    public void load() {
        mList.clear();
        List<MapiItemResult> itemList = new ArrayList<>();
        itemList.add(new MapiItemResult());
        mList.add(new MapiCartResult(itemList));
        itemList = new ArrayList<>();
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        mList.add(new MapiCartResult(itemList));
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.back, R.id.addr_root, R.id.upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addr_root:

                break;
            case R.id.upload:

                break;
        }
    }
}
