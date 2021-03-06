package com.yigu.shop.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yigu.shop.R;
import com.yigu.shop.shopinterface.TabSelListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/11.
 */
public class ComPersonTabLayout extends LinearLayout {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.sticky_header_view)
    LinearLayout stickyHeaderView;

    private Context mContext;
    private View view;

    private List<String> list_title = new ArrayList<>();

    public ComPersonTabLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ComPersonTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ComPersonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_shop_tab, this);
        ButterKnife.bind(this, view);

        stickyHeaderView.setVisibility(View.VISIBLE);
        list_title.add("资料");
        list_title.add("发表");

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        initListener();
    }

    private void initListener() {
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (null != tabSelListener)
                    tabSelListener.tabSel(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private TabSelListener tabSelListener;

    public void setTabSelListener(TabSelListener tabSelListener) {
        this.tabSelListener = tabSelListener;
    }

}
