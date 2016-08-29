package com.yigu.shop;

import android.os.Bundle;
import android.widget.TextView;

import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.UserApi;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.ter)
    TextView ter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        UserApi.login(this, "", "", new RequestCallback<List<MapiUserResult>>() {
            @Override
            public void success(List<MapiUserResult> success) {

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(String code, String message) {
                MainToast.showShortToast(message);
            }
        });
    }

    @OnClick(R.id.ter)
    public void onClick() {
        ControllerUtil.go2ProductList();
    }
}
