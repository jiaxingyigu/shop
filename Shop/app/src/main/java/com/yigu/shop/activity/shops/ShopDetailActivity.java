package com.yigu.shop.activity.shops;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.shops.ShopDetailAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;

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
    @Bind(R.id.lay_header)
    RelativeLayout layHeader;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;
    private List<String> list_title = new ArrayList<>();
    List<IndexData> mList;
    private List<MapiItemResult> data;
    ShopDetailAdapter mAdapter;
    private final static String SHOP_BG = "SHOP_BG";
    private final static String SHOP_TAB = "SHOP_TAB";
    private final static String CONTENT = "CONTENT";
    private final static String ITEM = "ITEM";
    private final static String DIVIDER = "DIVIDER";
    MapiShopResult item;
    private boolean isSel = false;

    private Integer pageIndex = 1;
    private Integer pageSize = 8;
    private Integer counts;

    String type = "";

    int maxDist;

    int totalChange = 0;
    int layHeaderHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        if (null != getIntent())
            item = (MapiShopResult) getIntent().getSerializableExtra("item");
        if (null != item) {
            initView();
            load();
            initListener();
        }

    }

    private void initView() {

        //200dp是普通header的高度
        maxDist = DPUtil.dip2px(155);//上面图片的高度

        data = new ArrayList<>();
        mList = new ArrayList<>();

        back.setImageResource(R.mipmap.back);
        center.setText("店铺详情");
        /*ivRight.setImageResource(R.mipmap.search);
        ivRightTwo.setVisibility(View.VISIBLE);
        ivRightTwo.setImageResource(R.mipmap.purcase);*/

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

        mList.clear();
        data.clear();
        mList.add(new IndexData(0, SHOP_BG, item));
        mList.add(new IndexData(1, SHOP_TAB, item));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();

        stickyHeaderView.post(new Runnable() {
            @Override
            public void run() {
                layHeaderHeight = layHeader.getMeasuredHeight();
                stickyHeaderView.setVisibility(View.VISIBLE);
                stickyHeaderView.setTranslationY(maxDist);
            }
        });


    }

    private void load() {

        showLoading();
        ItemApi.getGoods(this, "", item.getId(), "", type, pageIndex + "", new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer count, List<MapiItemResult> success) {
                swipeRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if (success.isEmpty())
                    return;
                data.addAll(success);
                int num = mList.size();
                DebugLog.i(data.size() + "==>data.size()");
                if (data == null || data.size() == 0) {
                    num = 0;
                } else {
                    for (MapiItemResult ware : data) {
                        mList.add(new IndexData(num++, ITEM, ware));
                        mList.add(new IndexData(num++, DIVIDER, new Object()));
                    }
                }
                DebugLog.i(mList.size() + "==>mList.size()");
                mAdapter.notifyDataSetChanged();

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                swipeRefreshLayout.setRefreshing(false);
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    public void loadNext() {
        if (counts == null || counts<=pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }

        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            data.clear();
            mList.add(new IndexData(0, SHOP_BG, item));
            mList.add(new IndexData(1, SHOP_TAB, item));
            pageIndex = 1;
            totalChange = 0;
            mAdapter.notifyDataSetChanged();
           /* stickyHeaderView.post(new Runnable() {
                @Override
                public void run() {
                    layHeaderHeight = layHeader.getMeasuredHeight();
                    stickyHeaderView.setVisibility(View.VISIBLE);
                    stickyHeaderView.setTranslationY(maxDist);
                }
            });*/
            load();
        }
    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*((MapiShopResult) mList.get(1).getData()).setType(tab.getPosition());
                ((MapiShopResult) mList.get(1).getData()).setSel(isSel);
                mAdapter.notifyDataSetChanged();
                isSel = true;
                */
                DebugLog.i("tablayout=>"+tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        type = "";
                        break;
                    case 1:
                        type = "new";
                        break;
                    case 2:
                        type = "hot";
                        break;
                    case 3:
                        type = "best";
                        break;
                }

                refreshData();

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
//                isSel = false;
                tablayout.getTabAt(position).select();
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ProductDetail((MapiItemResult) mList.get(position).getData());
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

                // 找到固定在屏幕上方那个FakeStickyLayout下面一个像素位置的RecyclerView的item，
                // 我们根据这个item来更新假的StickyLayout要translate多少距离.
                // 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
                // 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
              /* View transInfoView = recyclerView.findChildViewUnder(
                        stickyHeaderView.getMeasuredWidth() / 2, stickyHeaderView.getMeasuredHeight() + 1);

                DebugLog.i("transInfoView.getTag()==>"+transInfoView.getTag());
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
                }*/

                totalChange += dy;
                int tranY = Math.max(0, maxDist - totalChange);
                //移动距离超过maxDist，就定在0处  ,barHeight是appbar的高度，必须加上
                stickyHeaderView.setVisibility(View.VISIBLE);
                stickyHeaderView.setTranslationY(tranY);


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
