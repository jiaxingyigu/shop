package com.yigu.shop.view;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.product.ShopContentAdapter;
import com.yigu.shop.adapter.shops.ShopAdapter;
import com.yigu.shop.adapter.sort.SortBrandAdapter;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.widget.DividerGridItemDecoration;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/28.
 */
public class ShopContentLayout extends RelativeLayout {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.sticky_header_view)
    LinearLayout stickyHeaderView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> list_title = new ArrayList<>();
    private Context mContext;
    private View view;
    private List<MapiItemResult> mList;
    ShopContentAdapter mAdapter;

    public ShopContentLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ShopContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ShopContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_shop_content, this);
        ButterKnife.bind(this, view);
        stickyHeaderView.setVisibility(View.VISIBLE);
        list_title.add("全部");
        list_title.add("最新");
        list_title.add("最热");
        list_title.add("精品");
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        mList = new ArrayList<>();
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());
        mList.add(new MapiItemResult());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new ShopContentAdapter(mContext,mList);
        recyclerView.setAdapter(mAdapter);
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

    public void load(MapiShopResult item) {
        tablayout.getTabAt(item.getType()).select();
    }

    private TabSelListener tabSelListener;

    public void setTabSelListener(TabSelListener tabSelListener) {
        this.tabSelListener = tabSelListener;
    }

}
