package com.yigu.shop.fragment.community;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.yigu.shop.R;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.fragment.index.HomeFragment;
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class ComMunityFragment extends BaseFrag {
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private MunityHostFragment munityHostFragment;
    private MunityHostFragment munityHostFragment2;
    private MunityHostFragment munityHostFragment3;
    private MunityHostFragment munityHostFragment4;
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
        View view = inflater.inflate(R.layout.fragment_com_munity, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        munityHostFragment = new MunityHostFragment();
        munityHostFragment2 = new MunityHostFragment();
        munityHostFragment3 = new MunityHostFragment();
        munityHostFragment4 = new MunityHostFragment();
        list.add(munityHostFragment);
        list.add(munityHostFragment2);
        list.add(munityHostFragment3);
        list.add(munityHostFragment4);

        list_title.add("推荐");
        list_title.add("吃胎案例");
        list_title.add("跑偏案例");
        list_title.add("抖动案例");
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
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

}
