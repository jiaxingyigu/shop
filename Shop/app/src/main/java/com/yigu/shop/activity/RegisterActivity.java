package com.yigu.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.commom.widget.MainToast;

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
    TextView request;
    @Bind(R.id.psdEt)
    EditText psdEt;
    @Bind(R.id.psdTwoEt)
    EditText psdTwoEt;

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

                break;
            case R.id.request:
                String phoneStr = phoneEt.getText().toString();
                if(TextUtils.isEmpty(phoneStr)){
                    MainToast.showShortToast("请输入手机号");
                    return;
                }

                if(StringUtil.isMobile(phoneStr)){
                    MainToast.showShortToast("手机号格式不正确！");
                    return;
                }

                break;
        }
    }
}
