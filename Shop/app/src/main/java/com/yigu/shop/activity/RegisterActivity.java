package com.yigu.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.UserApi;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.SMSUtils;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.receiver.SMSBroadcastReceiver;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.phoneEt)
    EditText phoneEt;
    @Bind(R.id.codeEt)
    EditText codeEt;
    @Bind(R.id.request)
    TextView requestCode;
    @Bind(R.id.psdEt)
    EditText psdEt;
    @Bind(R.id.psdTwoEt)
    EditText psdTwoEt;

    /**
     * 短信验证倒计时--时长
     */
    private int i = 60;
    // 读取短信广播
    private SMSBroadcastReceiver smsBroadcastReceiver;
    private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    SMSUtils.EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("注册");
        tvRight.setText("完成");
        tvRight.setTextColor(Color.parseColor("#fcb04e"));
    }


    @OnClick({R.id.back, R.id.tv_right, R.id.request})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_right:
                String phoneString = phoneEt.getText().toString();
                String codeStr = codeEt.getText().toString();
                String psdStr = psdEt.getText().toString();
                String psdTwoStr = psdTwoEt.getText().toString();
                if(TextUtils.isEmpty(phoneString)){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(codeStr)){
                    MainToast.showShortToast("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(psdStr)){
                    MainToast.showShortToast("请输入密码");
                    return;
                }
                if(TextUtils.isEmpty(psdTwoStr)){
                    MainToast.showShortToast("请确认密码");
                    return;
                }

                if(!psdStr.equals(psdTwoStr)){
                    MainToast.showShortToast("两次密码输入不一致");
                    return;
                }

                UserApi.register(this, phoneString, codeStr, psdStr, psdTwoStr, new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        MainToast.showShortToast("注册成功");
                       /* userSP.saveUserBean(success);
                        Intent cancelIntent = new Intent(RegisterActivity.this,MainActivity.class);
                        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cancelIntent);*/
                        finish();
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        MainToast.showShortToast(message);
                    }
                });

                break;
            case R.id.request:
                String phoneStr = phoneEt.getText().toString();
                if(TextUtils.isEmpty(phoneStr)){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }

                if(!StringUtil.isMobile(phoneStr)){
                    MainToast.showShortToast("手机号格式不正确！");
                    return;
                }

                requestCode();

                break;
        }
    }

    /**
     * 向服务器请求验证码
     */
    private void requestCode() {
        SMSUtils.requestCode(this,phoneEt.getText().toString());
        // 把按钮变成不可点击，并且显示倒计时（正在获取）
        requestCode.setClickable(false);
        requestCode.setFocusableInTouchMode(false);
        requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_solid_drakyellow_round_4));
        requestCode.setText("重新发送(" + i + ")");
        initHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        i = 30;
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
    }

    private void initHandler(){
        eventHandler = new SMSUtils.EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = -7;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSUtils.registerEventHandler(eventHandler);
    }

    /**
     * 处理服务器返回的信息
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -9:
                    requestCode.setText("重新发送(" + i + ")");
                    break;
                case -8:
                    requestCode.setText("获取验证码");
                    requestCode.setClickable(true);
                    requestCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_pressed_color_red));
                    i = 60;
                    break;
                case -7:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    DebugLog.e("event=" + event);
                    if (result == SMSUtils.RESULT_COMPLETE) {
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE) {
                            MainToast.showShortToast((String) data);

                        }
                    }else if(result == SMSUtils.RESULT_ERROR){
                        if (event == SMSUtils.EVENT_GET_VERIFICATION_CODE_ERROR) {
                            MainToast.showShortToast((String) data);

                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        if(null!=eventHandler)
            SMSUtils.unregisterEventHandler(eventHandler);
//        if(null!=smsBroadcastReceiver)
//            unregisterReceiver(smsBroadcastReceiver);
        super.onDestroy();
    }


}
