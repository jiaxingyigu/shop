package com.yigu.shop.activity.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yigu.shop.R;
import com.yigu.shop.activity.addr.SelAddrActivity;
import com.yigu.shop.adapter.order.OrderAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.result.MapiOrderItem;
import com.yigu.shop.commom.result.MapiShipResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.util.zhifubao.PayResult;
import com.yigu.shop.util.zhifubao.SignUtils;
import com.yigu.shop.view.LogisticsLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
    @Bind(R.id.no_addr_root)
    RelativeLayout no_addr_root;
    @Bind(R.id.addr_root)
    LinearLayout addr_root;
    @Bind(R.id.logisticLayout)
    LogisticsLayout logisticsLayout;

    List<IndexData> indexList = new ArrayList<>();
    OrderAdapter mAdapter;
    DecimalFormat df = new DecimalFormat("#0.00");

    Double allPrice;

    MapiAddrResult mapiAddrResult;

    MapiShipResult shipResult;
    @Bind(R.id.weixin_sel)
    ImageView weixinSel;
    @Bind(R.id.weixin_ll)
    LinearLayout weixinLl;
    @Bind(R.id.zhifubao_sel)
    ImageView zhifubaoSel;
    @Bind(R.id.zhifubao_ll)
    LinearLayout zhifubaoLl;
    @Bind(R.id.ship_tip)
    TextView ship_tip;

    private int selPay = -1;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
        load();
        initListener();
    }

    private void initView() {
        center.setText("确认订单");
        back.setImageResource(R.mipmap.back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(0.5f), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new OrderAdapter(this, indexList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
        logisticsLayout.setShipSelListener(new LogisticsLayout.ShipSelListener() {
            @Override
            public void shipSel(MapiShipResult mapiShipResult) {
                shipResult = mapiShipResult;
                if (null != shipResult) {
                    Double free_money_val = Double.parseDouble(TextUtils.isEmpty(shipResult.getFree_money_val()) ? "0" : shipResult.getFree_money_val());
                    if(free_money_val>0){
                        if(allPrice<free_money_val){
                            ship_tip.setVisibility(View.GONE);
                            Double notifyAccount = allPrice + Double.parseDouble(TextUtils.isEmpty(shipResult.getShipping_fee()) ? "0" : shipResult.getShipping_fee());
                            account.setText(df.format(notifyAccount));
                        }else{
                            ship_tip.setVisibility(View.VISIBLE);
                            account.setText(df.format(allPrice));
                        }
                    }else{
                        ship_tip.setVisibility(View.GONE);
                        Double notifyAccount = allPrice + Double.parseDouble(TextUtils.isEmpty(shipResult.getShipping_fee()) ? "0" : shipResult.getShipping_fee());
                        account.setText(df.format(notifyAccount));
                    }

                }

            }
        });
    }

    public void load() {
        showLoading();
        ItemApi.checkorder(this, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                Gson gson = new Gson();
                if (null != success.getJSONObject("data").getJSONArray("goods_list")) {
                    List<MapiOrderItem> items = gson.fromJson(success.getJSONObject("data").getJSONArray("goods_list").toJSONString(), new TypeToken<List<MapiOrderItem>>() {
                    }.getType());
                    if (!items.isEmpty()) {
                        refreshItemList(items);
                    }
                }

                allPrice = success.getJSONObject("data").getDouble("goods_price");
                DebugLog.i("allPrice==>" + allPrice);
                if (allPrice == null)
                    allPrice = 0d;
                account.setText(df.format(allPrice));

                mapiAddrResult = JSONArray.parseObject(success.getJSONObject("data").getJSONObject("consignee").toJSONString(), MapiAddrResult.class);
                if (null != mapiAddrResult&&!TextUtils.isEmpty(mapiAddrResult.getName())) {
                    addr_root.setVisibility(View.VISIBLE);
                    no_addr_root.setVisibility(View.GONE);
                    consignee.setText("收货人：" + mapiAddrResult.getName());
                    tel.setText(mapiAddrResult.getTel());
                    address.setText(mapiAddrResult.getProvince_name() + mapiAddrResult.getCity_name() + mapiAddrResult.getDistrict_name() + mapiAddrResult.getAddress());
                } else {
                    addr_root.setVisibility(View.GONE);
                    no_addr_root.setVisibility(View.VISIBLE);
                }

                if (null != success.getJSONObject("data").getJSONArray("shipping_list")) {
                    DebugLog.i("!null shipping_list");
                    logisticsLayout.setVisibility(View.VISIBLE);
                    List<MapiShipResult> shipList = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("shipping_list").toJSONString(), MapiShipResult.class);
                    if (null != shipList&&shipList.size()>0) {
                        logisticsLayout.load(shipList);
                    }else{
                        logisticsLayout.setVisibility(View.GONE);
                    }
                } else {
                    logisticsLayout.setVisibility(View.GONE);
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

    private void refreshItemList(List<MapiOrderItem> data) {
        indexList.clear();
        int num = 0;
        if (data == null || data.size() == 0) {
            num = 0;
        } else {
            for (MapiOrderItem ware : data) {
                indexList.add(new IndexData(num++, "item", ware));
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.back, R.id.addr_root, R.id.upload, R.id.no_addr_root,R.id.weixin_ll,R.id.zhifubao_ll})
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
                if(null==mapiAddrResult||TextUtils.isEmpty(mapiAddrResult.getId())){
                    MainToast.showShortToast("请选择地址");
                    return;
                }
                if(logisticsLayout.getVisibility()==View.VISIBLE){
                    if(null==shipResult||TextUtils.isEmpty(shipResult.getShipping_id())){
                        MainToast.showShortToast("请选择物流");
                        return;
                    }
                }

                if(selPay<=0){
                    MainToast.showShortToast("请选择支付方式");
                    return;
                }

                if(logisticsLayout.getVisibility()==View.GONE){
                    MainToast.showShortToast("非常抱歉，该地区暂时不支持配送");
                    return;
                }

                uploadOrder();
                break;
            case R.id.no_addr_root:
                Intent intent2 = new Intent(this, SelAddrActivity.class);
                startActivityForResult(intent2, RequestCode.sel_addr);
                break;
            case R.id.zhifubao_ll:
                selPay = 2;
                notifyPay();
                break;
            case R.id.weixin_ll:
                selPay = 1;
                notifyPay();
                break;
        }
    }

    private void notifyPay(){
        if(selPay==1){
            weixinSel.setImageResource(R.mipmap.circle_red_sel);
            zhifubaoSel.setImageResource(R.mipmap.circle_white);
        }else if(selPay==2){
            weixinSel.setImageResource(R.mipmap.circle_white);
            zhifubaoSel.setImageResource(R.mipmap.circle_red_sel);
        }
    }

    private void uploadOrder(){
        String pay_name = "";
        String shipping_id = "";
        String address_id = "";
        if(selPay==1)
            pay_name = "app_微信";
        else if(selPay==2)
            pay_name = "app_支付宝";
        if(null!=shipResult&&!TextUtils.isEmpty(shipResult.getShipping_id()))
            shipping_id = shipResult.getShipping_id();
        if(null!=mapiAddrResult)
            address_id = mapiAddrResult.getId();


        showLoading();
        ItemApi.orderdown(this, shipping_id, address_id, pay_name, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();

                String orderInfo  = success.getJSONObject("data").getString("pay_info");
                callPay(orderInfo);

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.sel_addr:
                    if (null != data) {
                        addr_root.setVisibility(View.VISIBLE);
                        no_addr_root.setVisibility(View.GONE);
                        mapiAddrResult = (MapiAddrResult) data.getSerializableExtra("item");
                        consignee.setText("收货人：" + mapiAddrResult.getName());
                        tel.setText(mapiAddrResult.getTel());
                        address.setText(mapiAddrResult.getProvince_name() + mapiAddrResult.getCity_name() + mapiAddrResult.getDistrict_name() + mapiAddrResult.getAddress());
                        load();
                    }
                    break;
            }
        }
    }

    private void callPay(final String orderInfo){
        /*try {
            *//**
         * 仅需对sign 做URL编码
         *//*
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
       /* final String payInfo = orderInfo + "&sign=\"" + sign ;*/

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OderDetailActivity.this);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);


            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    Log.i("resultStatus",resultStatus);
                    Log.i("resultInfo",payResult.getResult());
                    Log.i("memo",payResult.getMemo());
                    if (TextUtils.equals(resultStatus, "9000")) {

                        Toast.makeText(OderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        DebugLog.i("支付宝支付成功");
                        ControllerUtil.go2MyOrder("order",0);
                        finish();
//					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OderDetailActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2MyOrder("order",0);
                            finish();
                        }
                        else if(TextUtils.equals(resultStatus, "6002 ")){
                            Log.i("info","网络连接出错");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2MyOrder("order",0);
                            finish();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
