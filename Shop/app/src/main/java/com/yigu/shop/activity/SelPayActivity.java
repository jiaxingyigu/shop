package com.yigu.shop.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.util.zhifubao.PayResult;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelPayActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.upload)
    TextView upload;
    @Bind(R.id.weixin_sel)
    ImageView weixinSel;
    @Bind(R.id.weixin_ll)
    LinearLayout weixinLl;
    @Bind(R.id.zhifubao_sel)
    ImageView zhifubaoSel;
    @Bind(R.id.zhifubao_ll)
    LinearLayout zhifubaoLl;

    MapiOrderResult mapiOrderResult;

    private int selPay = -1;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pay);
        ButterKnife.bind(this);
        if (null != getIntent())
            mapiOrderResult = (MapiOrderResult) getIntent().getSerializableExtra("item");
        if (null != mapiOrderResult) {
            ButterKnife.bind(this);
            initView();
        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("选择支付方式");

        priceTv.setText("订单总额：" + mapiOrderResult.getTotal_fee());
        upload.setText("确认支付"+mapiOrderResult.getTotal_fee());

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

    @OnClick({R.id.back, R.id.upload,R.id.weixin_ll,R.id.zhifubao_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.zhifubao_ll:
                selPay = 2;
                notifyPay();
                break;
            case R.id.weixin_ll:
                selPay = 1;
                notifyPay();
                break;
            case R.id.upload:
                if(selPay<=0){
                    MainToast.showShortToast("请选择支付方式");
                    return;
                }
                String pay_name = "";
                if(selPay==1)
                    pay_name = "app_微信";
                else if(selPay==2)
                    pay_name = "app_支付宝";
                showLoading();
                ItemApi.orderpay(this, mapiOrderResult.getOrder_id(), pay_name, new RequestCallback<JSONObject>() {
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
                break;
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
                PayTask alipay = new PayTask(SelPayActivity.this);
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

                        Toast.makeText(SelPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        DebugLog.i("支付宝支付成功");
                        ControllerUtil.go2MyOrder("order",0);
                        finish();
//					Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SelPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            ControllerUtil.go2MyOrder("order",0);
                            finish();
                        }
                        else if(TextUtils.equals(resultStatus, "6002 ")){
                            Log.i("info","网络连接出错");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SelPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
