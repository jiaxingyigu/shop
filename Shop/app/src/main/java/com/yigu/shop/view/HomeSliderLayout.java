package com.yigu.shop.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.adapter.ShopPagerAdapter;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/30.
 */
public class HomeSliderLayout extends RelativeLayout {
    @Bind(R.id.index_viewpager)
    ViewPager indexViewpager;
    @Bind(R.id.guide_dot)
    LinearLayout guideDot;
    private Context mContext;
    private View view;
    List<View> sliderViewList;
    public HomeSliderLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeSliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_slider, this);
        ButterKnife.bind(this, view);
    }

    public void load(List<MapiResourceResult> list){
        sliderViewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SimpleDraweeView view = new SimpleDraweeView(mContext);
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
        indexViewpager.setAdapter(sliderAdapter);
        guideDot.removeAllViews();
        for (int i = 0; i < sliderViewList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DPUtil.dip2px(7), DPUtil.dip2px(7));
            params.setMargins(DPUtil.dip2px(4), 0, DPUtil.dip2px(4), DPUtil.dip2px(6));
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.drawable.selector_item_dot);
            guideDot.addView(imageView);
        }
        guideDot.getChildAt(0).setSelected(true);
        indexViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

}
