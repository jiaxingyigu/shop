package com.yigu.shop.fragment.index;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.result.MapiUserResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/10/8.
 */
public class MyShopFragment extends BaseFrag {
    @Bind(R.id.rl_addr)
    RelativeLayout rlAddr;
    @Bind(R.id.rl_order)
    RelativeLayout rl_order;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.iv_pay)
    ImageView ivPay;
    @Bind(R.id.iv_ship)
    ImageView ivShip;
    @Bind(R.id.iv_shipped)
    ImageView ivShipped;
    @Bind(R.id.iv_finished)
    ImageView ivFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_my_shop, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugLog.i("MyShopFragment==>onResume");
        if (userSP.checkLogin()) {
            name.setText(userSP.getUserBean().getName());
        } else {
            name.setText("请先登录");
        }
        if (userSP.checkLogin()) {
            load();
        }

    }

    private void initView() {
       /* if (userSP.checkLogin()) {
            name.setText(userSP.getUserBean().getName());
        } else {
            name.setText("请先登录");
        }*/

    }

    public void load() {
        showLoading();
        ItemApi.getinfo(getActivity(), new RequestCallback<MapiUserResult>() {
            @Override
            public void success(MapiUserResult success) {
                hideLoading();
                userSP.saveUserBean(success);
                if (userSP.checkLogin()) {
                    name.setText(userSP.getUserBean().getName());
                } else {
                    name.setText("请先登录");
                }

                MapiOrderResult mapiOrderResult = userSP.getUserBean().getOrder_num();
                if(null!=mapiOrderResult){
                    int await_pay =  TextUtils.isEmpty(mapiOrderResult.getAwait_pay())?0:Integer.parseInt(mapiOrderResult.getAwait_pay());
                    int await_ship =  TextUtils.isEmpty(mapiOrderResult.getAwait_ship())?0:Integer.parseInt(mapiOrderResult.getAwait_ship());
                    int shipped =  TextUtils.isEmpty(mapiOrderResult.getShipped())?0:Integer.parseInt(mapiOrderResult.getShipped());
                    int finished =  TextUtils.isEmpty(mapiOrderResult.getFinished())?0:Integer.parseInt(mapiOrderResult.getFinished());
                    if(await_pay<=0)
                        ivPay.setVisibility(View.GONE);
                    else
                        ivPay.setVisibility(View.VISIBLE);

                    if(await_ship<=0)
                        ivShip.setVisibility(View.GONE);
                    else
                        ivShip.setVisibility(View.VISIBLE);

                    if(shipped<=0)
                        ivShipped.setVisibility(View.GONE);
                    else
                        ivShipped.setVisibility(View.VISIBLE);

                    if(finished<=0)
                        ivFinished.setVisibility(View.GONE);
                    else
                        ivFinished.setVisibility(View.VISIBLE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_order, R.id.rl_addr, R.id.rl_setting, R.id.rl_collect, R.id.rl_help, R.id.name,R.id.rl_waitpay,R.id.rl_waitsend,R.id.rl_waitreceive,R.id.rl_ordercomplete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2MyOrder("",0);
                }
                break;
            case R.id.rl_waitpay:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2MyOrder("",0);
                }
                break;
            case R.id.rl_waitsend:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2MyOrder("",1);
                }
                break;
            case R.id.rl_waitreceive:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2MyOrder("",2);
                }
                break;
            case R.id.rl_ordercomplete:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2MyOrder("",3);
                }
                break;
            case R.id.rl_addr:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {

                    ControllerUtil.go2ManageAddr();
                }
                break;
            case R.id.rl_setting:
                if (!userSP.checkLogin()) {
                    ControllerUtil.go2Login();
                } else {
                    ControllerUtil.go2Setting();

                }
                break;
            case R.id.rl_collect:
                ControllerUtil.go2CollectList();
                break;
            case R.id.rl_help:
                ControllerUtil.go2Help();
                break;
            case R.id.name:
                if (!userSP.checkLogin())
                    ControllerUtil.go2Login();
                break;
        }
    }
}
