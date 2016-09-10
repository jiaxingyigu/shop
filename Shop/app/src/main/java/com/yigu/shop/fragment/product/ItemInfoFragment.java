package com.yigu.shop.fragment.product;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.adapter.ShopPagerAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.view.ItemShopLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemInfoFragment extends BaseFrag {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.guide_dot)
    LinearLayout guideDot;
    @Bind(R.id.newPrice)
    TextView newPrice;
    @Bind(R.id.oldPrice)
    TextView oldPrice;
    @Bind(R.id.info_tv)
    TextView infoTv;
    @Bind(R.id.addr_tv)
    TextView addrTv;
    @Bind(R.id.itemShopLayout)
    ItemShopLayout itemShopLayout;
    List<View> sliderViewList;
    public ItemInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        return view;
    }

    private void initView() {

    }

    public void load() {
        sliderViewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SimpleDraweeView view = new SimpleDraweeView(getActivity());
            view.setImageResource(R.drawable.carousel_default);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainToast.showShortToast("点击");
                }
            });
            sliderViewList.add(view);
        }
        ShopPagerAdapter sliderAdapter = new ShopPagerAdapter(sliderViewList);
        viewpager.setAdapter(sliderAdapter);
        guideDot.removeAllViews();
        for (int i = 0; i < sliderViewList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DPUtil.dip2px(7), DPUtil.dip2px(7));
            params.setMargins(DPUtil.dip2px(4), 0, DPUtil.dip2px(4), DPUtil.dip2px(6));
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.drawable.selector_item_dot);
            guideDot.addView(imageView);
        }
        guideDot.getChildAt(0).setSelected(true);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < sliderViewList.size(); i++) {
                    if (position == i)
                        guideDot.getChildAt(i).setSelected(true);
                    else
                        guideDot.getChildAt(i).setSelected(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
