package com.yigu.shop.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.api.UserApi;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.phoneEt)
    EditText phoneEt;
    @Bind(R.id.psdEt)
    EditText psdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.login, R.id.forget, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                final String phoneStr = phoneEt.getText().toString();
                final String psdStr = psdEt.getText().toString();
                if(TextUtils.isEmpty(phoneStr)){
                    MainToast.showShortToast("请输入账号");
                    return;
                }

               /* if(!StringUtil.isMobile(phoneStr)){
                    MainToast.showShortToast("手机号格式不正确！");
                    return;
                }*/

                if(TextUtils.isEmpty(psdStr)){
                    MainToast.showShortToast("请输入密码");
                    return;
                }
                showLoading();
                UserApi.login(this, phoneStr, psdStr, new RequestCallback<MapiUserResult>() {
                    @Override
                    public void success(MapiUserResult success) {
                        hideLoading();
                        MainToast.showShortToast("登录成功");
                        userSP.saveUserBean(success);
                        userSP.setUserName(phoneStr);
                        userSP.setUserPsd(psdStr);

                        showLoading();
                        CommunityApi.userlogin(LoginActivity.this, userSP.getUserName(), userSP.getUserPsd(), new RequestCallback<MapiMunityUserResult>() {
                            @Override
                            public void success(MapiMunityUserResult success) {
                                hideLoading();
                                comUserSP.saveUserBean(success);
//                                ControllerUtil.go2Community();
                                finish();
                            }
                        }, new RequestExceptionCallback2() {
                            @Override
                            public void error(String code, String message) {
                                hideLoading();
                                MainToast.showShortToast(message);
                            }
                        });
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });
                break;
            case R.id.forget:
                ControllerUtil.go2Forget();
                break;
            case R.id.register:
                ControllerUtil.go2Register();
                break;
        }
    }
}
