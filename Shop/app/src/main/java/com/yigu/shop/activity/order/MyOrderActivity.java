package com.yigu.shop.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.activity.purcase.PurcaseListActivity;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.application.AppContext;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.fragment.order.OrderCompleteFragment;
import com.yigu.shop.fragment.order.WaitPayFragment;
import com.yigu.shop.fragment.order.WaitReceiveFragment;
import com.yigu.shop.fragment.order.WaitSendFragment;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;
    WaitPayFragment waitPayFragment;
    WaitSendFragment waitSendFragment;
    WaitReceiveFragment waitReceiveFragment;
    OrderCompleteFragment orderCompleteFragment;
    private int scrollTime = 270;
    String from = "";
    int fragIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        if(null!=getIntent()) {
            from = getIntent().getStringExtra("from");
            fragIndex = getIntent().getIntExtra("fragIndex",0);
        }
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        center.setText("我的订单");
        back.setImageResource(R.mipmap.back);
        waitPayFragment = new WaitPayFragment();
        waitSendFragment = new WaitSendFragment();
        waitReceiveFragment = new WaitReceiveFragment();
        orderCompleteFragment = new OrderCompleteFragment();
        list.add(waitPayFragment);
        list.add(waitSendFragment);
        list.add(waitReceiveFragment);
        list.add(orderCompleteFragment);
        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");
        list_title.add("完成");
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);

        viewpager.setOffscreenPageLimit(0);

        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(this,new AccelerateDecelerateInterpolator());
        //调整速率
        viewPagerScroller.setScrollDuration(scrollTime);
        viewPagerScroller.initViewPagerScroll(viewpager);           //初始化ViewPager时,反射修改滑动速度
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(fragIndex);

    }

    @OnClick(R.id.back)
    public void onClick() {
        exit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exit();

    }

    private void exit(){
        if(!TextUtils.isEmpty(from)&&from.equals("order")){
            DebugLog.i("exit==order");
            Intent intent = new Intent(AppContext.getInstance(), PurcaseListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.getInstance().startActivity(intent);
            finish();
        }else
            finish();

    }

}
