package com.yigu.shop.fragment.index;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;

import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.fragment.sort.SortBrandFragment;
import com.yigu.shop.fragment.sort.SortPartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class SortTwoFragment extends BaseFrag {
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    SortPartFragment sortPartFragment;
    SortBrandFragment sortBrandFragment;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sort_two, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        sortPartFragment = new SortPartFragment();
        sortBrandFragment = new SortBrandFragment();
        list.add(sortPartFragment);
        list.add(sortBrandFragment);
        list_title.add("分类");
        list_title.add("品牌");
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
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
