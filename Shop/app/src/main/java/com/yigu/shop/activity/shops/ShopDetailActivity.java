package com.yigu.shop.activity.shops;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.shops.ShopDetailAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.shopinterface.TabSelListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDetailActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.sticky_header_view)
    LinearLayout stickyHeaderView;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.iv_right_two)
    ImageView ivRightTwo;
    private List<String> list_title = new ArrayList<>();
    List<IndexData> mList = new ArrayList<>();
    ShopDetailAdapter mAdapter;
    private final static String SHOP_BG = "SHOP_BG";
    private final static String CONTENT = "CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initView();
        load();
        initListener();
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText("店铺详情");
        ivRight.setImageResource(R.mipmap.search);
        ivRightTwo.setVisibility(View.VISIBLE);
        ivRightTwo.setImageResource(R.mipmap.purcase);

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this,OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopDetailAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void load() {
        mList.clear();
        mList.add(new IndexData(0, SHOP_BG, new MapiShopResult()));
        mList.add(new IndexData(1, CONTENT, new MapiShopResult()));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((MapiShopResult) mList.get(1).getData()).setType(tab.getPosition());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAdapter.setTabSelListener(new TabSelListener() {
            @Override
            public void tabSel(int position) {
                tablayout.getTabAt(position).select();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               /* LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() > 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }*/

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 找到固定在屏幕上方那个FakeStickyLayout下面一个像素位置的RecyclerView的item，
                // 我们根据这个item来更新假的StickyLayout要translate多少距离.
                // 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
                // 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
                View transInfoView = recyclerView.findChildViewUnder(
                        stickyHeaderView.getMeasuredWidth() / 2, stickyHeaderView.getMeasuredHeight() + 1);
                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - stickyHeaderView.getMeasuredHeight();

                    // 如果当前item需要展示StickyLayout，
                    // 那么根据这个item的getTop和FakeStickyLayout的高度相差的距离来滚动FakeStickyLayout.
                    // 这里有一处需要注意，如果这个item的getTop已经小于0，也就是滚动出了屏幕，
                    // 那么我们就要把假的StickyLayout恢复原位，来覆盖住这个item对应的吸顶信息.
                    if (transViewStatus == mAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            stickyHeaderView.setVisibility(View.INVISIBLE);
                            stickyHeaderView.setTranslationY(dealtY);
                        } else {
                            stickyHeaderView.setVisibility(View.VISIBLE);
                            stickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == mAdapter.NONE_STICKY_VIEW) {
                        // 如果当前item不需要展示StickyLayout，那么就不会引起FakeStickyLayout的滚动.
                        stickyHeaderView.setVisibility(View.INVISIBLE);
                        stickyHeaderView.setTranslationY(0);
                    }
                }

            }
        });
    }

    @OnClick({R.id.back, R.id.iv_right, R.id.iv_right_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right:
                break;
            case R.id.iv_right_two:
                break;
        }
    }
}
