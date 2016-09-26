package com.yigu.shop.fragment.index;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndextFragment extends BaseFrag {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private HomeFragment homeFragment;
    private HomeFragment homeFragment2;
    private HomeFragment homeFragment3;
    private HomeFragment homeFragment4;
    private HomeFragment homeFragment5;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;
    public IndextFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        homeFragment = new HomeFragment();
        homeFragment2 = new HomeFragment();
        homeFragment3 = new HomeFragment();
        homeFragment4 = new HomeFragment();
        homeFragment5 = new HomeFragment();
        list.add(homeFragment);
        list.add(homeFragment2);
        list.add(homeFragment3);
        list.add(homeFragment4);
        list.add(homeFragment5);

        list_title.add("推荐");
        list_title.add("设备");
        list_title.add("工具");
        list_title.add("耗材");
        list_title.add("组建");
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        mAdapter = new TabFragmentAdapter(getChildFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
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
