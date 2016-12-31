package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.ItemTwoAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    private String search = "";

    ItemTwoAdapter mAdapter;
    List<MapiItemResult> mList = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        if (null != getIntent())
            search = getIntent().getStringExtra("search");
        if (!TextUtils.isEmpty(search)) {
            initView();
            initListener();
            load();
        }
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText(search);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText("上架时间"));
        tablayout.addTab(tablayout.newTab().setText("销量"));
        tablayout.addTab(tablayout.newTab().setText("价格"));
        tablayout.addTab(tablayout.newTab().setText("评论数"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemTwoAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ProductDetail(mList.get(position));
            }
        });

        swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainToast.showShortToast(tab.getPosition()+"===pos");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void load() {

    }

    private void loadNext() {
        if (counts == null || counts == pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        showLoading();
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
