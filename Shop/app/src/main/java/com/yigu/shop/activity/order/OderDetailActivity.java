package com.yigu.shop.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.activity.addr.SelAddrActivity;
import com.yigu.shop.adapter.order.OrderAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;

import java.text.DecimalFormat;
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
    List<IndexData> indexList = new ArrayList<>();
    private List<MapiItemResult> itemList = new ArrayList<>();
    OrderAdapter mAdapter;
    DecimalFormat df = new DecimalFormat("#0.00");

    double allPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            int count = 0;

            List<MapiItemResult> list = (List<MapiItemResult>) getIntent().getSerializableExtra("list");
            double accountNum = 0;
            double priceff = 0;
            int num = 0;
            allPrice = 0;
            if(null!=list&&list.size()>0){
                itemList.clear();
                itemList.addAll(list);
                indexList.add(new IndexData(count++, "head", itemList.get(0)));
                num++;
                indexList.add(new IndexData(count++, "item", itemList.get(0)));

                priceff = Double.parseDouble(TextUtils.isEmpty( itemList.get(0).getGoods_price())?"0": itemList.get(0).getGoods_price());
                accountNum += priceff;
                allPrice += priceff;

                if(itemList.size()<=1){
                    itemList.get(0).setAllAcount(df.format(accountNum));
                    itemList.get(0).setAllNum(num+"");
                    indexList.add(new IndexData(count++,"bottom", itemList.get(0)));
                }else{
                    for(int i=1;i<itemList.size();i++){

                        MapiItemResult itemResult = itemList.get(i);
                        if(itemResult.getSeller_id().equals(itemList.get(i-1).getSeller_id())){
                            num++;
                            indexList.add(new IndexData(count++, "item", itemResult));
                            priceff = Double.parseDouble(TextUtils.isEmpty(itemResult.getGoods_price())?"0": itemResult.getGoods_price());
                            accountNum += priceff;
                            allPrice += priceff;
                        }else{
                            itemResult.setAllAcount(df.format(accountNum));
                            itemResult.setAllNum(num+"");
                            indexList.add(new IndexData(count++,"bottom", itemResult));
                            indexList.add(new IndexData(count++, "divider", itemResult));
                            accountNum = 0;
                            num = 0;
                            indexList.add(new IndexData(count++, "head", itemResult));
                            indexList.add(new IndexData(count++, "item", itemResult));
                        }

                       if(i==itemList.size()-1){
                           itemResult.setAllAcount(df.format(accountNum));
                           itemResult.setAllNum(num+"");
                           indexList.add(new IndexData(count++,"bottom", itemResult));
                           accountNum = 0;
                           num = 0;
                       }

                    }
                }

            }
        }
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
        mAdapter = new OrderAdapter(this,indexList);
        recyclerView.setAdapter(mAdapter);

        account.setText(allPrice+"");

    }

    public void load() {

    }


    @OnClick({R.id.back, R.id.addr_root, R.id.upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addr_root:
                Intent intent = new Intent(this, SelAddrActivity.class);
                startActivityForResult(intent, RequestCode.sel_addr);
                break;
            case R.id.upload:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.sel_addr:
                    if (null != data) {

                        MapiAddrResult addrResult = (MapiAddrResult) data.getSerializableExtra("item");
                        consignee.setText("收货人："+addrResult.getConsignee());
                        tel.setText(addrResult.getTel());
                        address.setText(addrResult.getProvince()+addrResult.getCity()+addrResult.getDistrict()+addrResult.getAddress());
                    }
                    break;
            }
        }
    }

}
