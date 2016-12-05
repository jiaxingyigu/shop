package com.yigu.shop.fragment.index;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yigu.shop.R;
import com.yigu.shop.adapter.TabFragmentAdapter;
import com.yigu.shop.adapter.TabObjectFragmentAdapter;
import com.yigu.shop.adapter.purcase.PurcaseAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    private IndexDeviceFragment indexDeviceFragment;
    private IndexToolFragment indexToolFragment;
    private IndexMaterialFragment indexMaterialFragment;
    private IndexSubFragment indexSubFragment;
    private List<Fragment> list = new ArrayList<>();
    private List<MapiSortResult> list_title = new ArrayList<>();
    TabObjectFragmentAdapter mAdapter;

    private int scrollTime = 270;

    private Integer pageIndex = 1;
    private Integer pageSize = 6;
    private Integer counts;

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
        /*homeFragment = new HomeFragment();
        indexDeviceFragment = new IndexDeviceFragment();
        indexToolFragment = new IndexToolFragment();
        indexMaterialFragment = new IndexMaterialFragment();
        indexSubFragment = new IndexSubFragment();
        homeFragment.setCat_id("");
        indexDeviceFragment.setCat_id("");
        indexToolFragment.setCat_id("");
        indexMaterialFragment.setCat_id("");
        indexSubFragment.setCat_id("");
        list.add(homeFragment);
        list.add(indexDeviceFragment);
        list.add(indexToolFragment);
        list.add(indexMaterialFragment);
        list.add(indexSubFragment);*/


        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

       /* mAdapter = new TabObjectFragmentAdapter(getChildFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);*/

        viewpager.setOffscreenPageLimit(1);

        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(getContext(),new AccelerateDecelerateInterpolator());
        //调整速率
        viewPagerScroller.setScrollDuration(scrollTime);
        viewPagerScroller.initViewPagerScroll(viewpager);           //初始化ViewPager时,反射修改滑动速度
    }

    public void load() {

        ItemApi.indexUrl(getActivity(), pageIndex + "", pageSize + "", new RequestPageCallback<JSONObject>() {
            @Override
            public void success(Integer count, JSONObject success) {
                counts = count;
                if(success.isEmpty())
                    return;
                try{
                    List<MapiSortResult> sortList =  JSONArray.parseArray(success.getJSONObject("data").getJSONArray("cate").toJSONString(),MapiSortResult.class);
                    List<MapiResourceResult> resourceResults = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("slider").toJSONString(),MapiResourceResult.class);
                    if(null!=sortList){
                        list_title.clear();
                        list_title.addAll(sortList);
                        for(int i=0;i<list_title.size();i++){
                            tablayout.addTab(tablayout.newTab().setText(list_title.get(i).getCat_name()));
                            if(i==0){
                                homeFragment = new HomeFragment();
                                homeFragment.setCat_id(list_title.get(i).getCat_id(),resourceResults);
                                list.add(homeFragment);
                            }else{
                                indexDeviceFragment = new IndexDeviceFragment();
                                indexDeviceFragment.setCat_id(list_title.get(i).getCat_id());
                                list.add(indexDeviceFragment);
                            }

                        }

                        mAdapter = new TabObjectFragmentAdapter(getChildFragmentManager(), list, list_title);
                        viewpager.setAdapter(mAdapter);
                        tablayout.setupWithViewPager(viewpager);
                        tablayout.setTabsFromPagerAdapter(mAdapter);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                MainToast.showShortToast(message);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
