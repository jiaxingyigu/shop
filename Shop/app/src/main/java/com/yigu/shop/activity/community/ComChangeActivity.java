package com.yigu.shop.activity.community;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.fragment.community.ChangeDPHFragment;
import com.yigu.shop.fragment.community.ChangeHostFragment;
import com.yigu.shop.fragment.community.ChangeLunTaiFragment;
import com.yigu.shop.fragment.community.ChangeShaCheFragment;
import com.yigu.shop.fragment.community.ChangeSiLunFragment;
import com.yigu.shop.fragment.community.ChangeTiaoZhengFragment;
import com.yigu.shop.fragment.community.ChangeXuanGuaFragment;
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComChangeActivity extends BaseActivity {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    private ChangeHostFragment changeHostFragment;
    private ChangeSiLunFragment changeSiLunFragment;
    private ChangeDPHFragment changeDPHFragment;
    private ChangeLunTaiFragment changeLunTaiFragment;
    private ChangeTiaoZhengFragment changeTiaoZhengFragment;
    private ChangeXuanGuaFragment changeXuanGuaFragment;
    private ChangeShaCheFragment changeShaCheFragment;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    private int scrollTime = 270;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_change);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("交流切磋");

        changeHostFragment = new ChangeHostFragment();
        changeSiLunFragment = new ChangeSiLunFragment();
        changeDPHFragment = new ChangeDPHFragment();
        changeLunTaiFragment = new ChangeLunTaiFragment();
        changeTiaoZhengFragment = new ChangeTiaoZhengFragment();
        changeXuanGuaFragment = new ChangeXuanGuaFragment();
        changeShaCheFragment = new ChangeShaCheFragment();

        list.add(changeHostFragment);
        list.add(changeSiLunFragment);
        list.add(changeDPHFragment);
        list.add(changeLunTaiFragment);
        list.add(changeTiaoZhengFragment);
        list.add(changeXuanGuaFragment);
        list.add(changeShaCheFragment);

        list_title.add("最新");
        list_title.add("四轮定位");
        list_title.add("动平衡");
        list_title.add("轮胎拆装");
        list_title.add("调整组件");
        list_title.add("悬挂系统");
        list_title.add("刹车系统");

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(5)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(6)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(this, new AccelerateDecelerateInterpolator());
        //调整速率
        viewPagerScroller.setScrollDuration(scrollTime);
        viewPagerScroller.initViewPagerScroll(viewpager);           //初始化ViewPager时,反射修改滑动速度
        tablayout.setupWithViewPager(viewpager);

    }

    public void load() {

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
