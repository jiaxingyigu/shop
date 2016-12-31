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
import com.yigu.shop.fragment.find.CollectionFragment;
import com.yigu.shop.fragment.find.UnCollectionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class FindShopFragment extends BaseFrag {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    UnCollectionFragment unCollectionFragment;
    CollectionFragment collectionFragment;
    private List<Fragment> list = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_findshop, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {
        unCollectionFragment = new UnCollectionFragment();
        collectionFragment = new CollectionFragment();
        list.add(unCollectionFragment);
        list.add(collectionFragment);
        list_title.add("身边的好店");
        list_title.add("收藏的店铺");
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
