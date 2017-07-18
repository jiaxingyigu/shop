package com.yigu.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.LocationUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.fragment.community.ComChangeFragment;
import com.yigu.shop.fragment.community.ComIndexFragment;
import com.yigu.shop.fragment.community.ComMunityFragment;
import com.yigu.shop.fragment.community.ComPersonFragment;
import com.yigu.shop.fragment.community.ComPublishFragment;
import com.yigu.shop.fragment.community.MunityHostFragment;
import com.yigu.shop.fragment.index.FindShopFragment;
import com.yigu.shop.fragment.index.IndextFragment;
import com.yigu.shop.fragment.index.MyShopFragment;
import com.yigu.shop.fragment.index.SortTwoFragment;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityActivity extends BaseActivity implements LocationUtil.OnLocationListener {

    @Bind(R.id.radio_home)
    RadioButton radioHome;
    @Bind(R.id.radio_community)
    RadioButton radioCommunity;
    @Bind(R.id.radio_person)
    RadioButton radioPerson;
    @Bind(R.id.radio_shop)
    RadioButton radioShop;
    @Bind(R.id.radio_tell)
    RadioButton radioTell;
    @Bind(R.id.bottom_layout)
    RadioGroup bottomLayout;
    private BaseFrag[] fragments;
    private int index = 0;

    LocationUtil localUtil = null;

    private RadioButton[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!userSP.checkLogin()) {
//            ControllerUtil.go2Login();
//            finish();
//        } else {
            setContentView(R.layout.activity_community);
            ButterKnife.bind(this);
            initView();
//            load();
//        }
    }

    private void initView() {

        fragments = new BaseFrag[5];
        fragments[0] = new ComIndexFragment();
        fragments[1] = new ComMunityFragment();
        fragments[2] = new IndextFragment();
        fragments[3] = new ComPublishFragment();
        fragments[4] = new ComPersonFragment();
        selectTab();

        localUtil = new LocationUtil(this);
        localUtil.setOnReceivedMessageListener(this);
        localUtil.startLoc();

        buttons = new RadioButton[5];
        buttons[0] = radioHome;
        buttons[1] = radioCommunity;
        buttons[2] = radioShop;
        buttons[3] = radioTell;
        buttons[4] = radioPerson;

    }

    @Override
    public void onStop() {
        super.onStop();
        localUtil.stopLoc();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /*int type = intent.getIntExtra("type",0);
        if(type== RequestCode.login_exit){
            ControllerUtil.go2Login();
            finish();
        }*/
    }


    private void load(){
        showLoading();
        CommunityApi.userlogin(this, userSP.getUserName(), userSP.getUserPsd(), new RequestCallback<MapiMunityUserResult>() {
            @Override
            public void success(MapiMunityUserResult success) {
                hideLoading();
                comUserSP.saveUserBean(success);
            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void selectTab() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (BaseFrag frag : fragments) {
            if (!frag.isAdded())
                transaction.add(R.id.content, frag);
            transaction.hide(frag);
        }
        transaction.show(fragments[index]);
        transaction.commitAllowingStateLoss();
    }


    @OnClick({R.id.radio_home,R.id.radio_community,R.id.radio_shop, R.id.radio_tell, R.id.radio_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                index = 0;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_community:
                index = 1;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_shop:
//                if (!userSP.checkLogin())
//                    ControllerUtil.go2Login();
//                else{
                    buttons[index].setChecked(true);
                    ControllerUtil.go2Main();
//                }

                break;
            case R.id.radio_tell:
                index = 3;
                selectTab();
                fragments[index].load();
                break;
            case R.id.radio_person:
                if (!userSP.checkLogin()) {
                    buttons[index].setChecked(true);
                    ControllerUtil.go2Login();
                } else {
                    index = 4;
                    selectTab();
//                    fragments[index].load();
                }
                break;

        }
    }

    @Override
    public void localInfo(BDLocation location) {
        if(161==location.getLocType()) {//网络定位结果，网络定位定位成功。
            Log.i("info", "定位成功");
            DebugLog.i("纬度："+location.getLatitude());
            DebugLog.i("经度："+location.getLongitude());
            DebugLog.i("省："+location.getProvince());
            DebugLog.i("市："+location.getCity());
            DebugLog.i("区："+location.getDistrict());
            DebugLog.i("街道："+location.getStreet());
            DebugLog.i("地址："+location.getAddress());
            comUserSP.setLatitude(location.getLatitude()+"");
            comUserSP.setLongitude(location.getLongitude()+"");
            comUserSP.setLocation(location.getProvince()+location.getCity()+location.getDistrict()+location.getStreet());
        }else {//定位失败
            Log.i("info", "定位失败");
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
