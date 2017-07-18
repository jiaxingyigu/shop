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
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class ComChangeFragment extends BaseFrag {
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
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

    public ComChangeFragment() {
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
