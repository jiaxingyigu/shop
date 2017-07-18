package com.yigu.shop.fragment.community;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.fragment.index.HomeFragment;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class ComMunityFragment extends BaseFrag {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private MunityHostFragment munityHostFragment;
    private MunityChiTaiFragment munityChiTaiFragment;
    private MunityPaoPianFragment munityPaoPianFragment;
    private MunityDouDonFragment munityDouDonFragment;
    private MunityQiTaFragment munityQiTaFragment;
    private MunitySheBeiFragment munitySheBeiFragment;
    private MunityDiPanFragment munityDiPanFragment;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    private int scrollTime = 270;

    public ComMunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_com_munity, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {

        back.setImageResource(R.mipmap.logo);
        ivRight.setImageResource(R.mipmap.search);
        tvRightTwo.setText("签到");
        tvRightTwo.setVisibility(View.VISIBLE);

        munityHostFragment = new MunityHostFragment();
        munityChiTaiFragment = new MunityChiTaiFragment();
        munityPaoPianFragment = new MunityPaoPianFragment();
        munityDouDonFragment = new MunityDouDonFragment();
        munityQiTaFragment = new MunityQiTaFragment();
        munitySheBeiFragment = new MunitySheBeiFragment();
        munityDiPanFragment = new MunityDiPanFragment();

        list.add(munityHostFragment);
        list.add(munityChiTaiFragment);
        list.add(munityPaoPianFragment);
        list.add(munityDouDonFragment);
        list.add(munityQiTaFragment);
        list.add(munitySheBeiFragment);
        list.add(munityDiPanFragment);

        list_title.add("推荐");
        list_title.add("吃胎案例");
        list_title.add("跑偏案例");
        list_title.add("抖动案例");
        list_title.add("其它案例");
        list_title.add("设备培训");
        list_title.add("底盘培训");

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(5)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(6)));
        mAdapter = new TabFragmentAdapter(getChildFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(getContext(),new AccelerateDecelerateInterpolator());
        //调整速率
        viewPagerScroller.setScrollDuration(scrollTime);
        viewPagerScroller.initViewPagerScroll(viewpager);           //初始化ViewPager时,反射修改滑动速度
        tablayout.setupWithViewPager(viewpager);

    }

    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.back, R.id.tv_right_two, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tv_right_two:
                // TODO: 2017/3/29
                MainToast.showShortToast("正在开发...");
                break;
            case R.id.iv_right:
                ControllerUtil.go2ComSearch();
                break;
        }
    }

}
