package com.yigu.shop.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.product.ShopContentAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.widget.BestSwipeRefreshLayout;

import java.lang.reflect.Method;
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
//    @Bind(R.id.swipRefreshLayout)
//    BestSwipeRefreshLayout swipRefreshLayout;
    private List<String> list_title = new ArrayList<>();
    private Context mContext;
    private View view;
    private List<MapiItemResult> mList;
    ShopContentAdapter mAdapter;
    String type = "all";
    String seller_id = "";
    private Integer pageIndex = 1;
    private Integer pageSize = 8;
    private Integer counts;
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

        mList = new ArrayList<>();

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

       /* swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });*/
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


    }

    public void load(MapiShopResult item) {
        if(null!=item){


            seller_id = item.getSeller_id();

           /* Class clz = tablayout.getClass();
            try {
                Method animateToTab = clz.getDeclaredMethod("animateToTab", new Class[]{int.class});
                animateToTab.setAccessible(true);
                animateToTab.invoke(tablayout, new Object[]{4});
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            if(item.isSel())
                tablayout.getTabAt(item.getType()).select();//

            switch (item.getType()){
                case 0:
                    type = "all";
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
            DebugLog.i(seller_id);
            DebugLog.i(type);
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            loadData();
        }
    }

    private void loadNext() {
        if (counts == null || counts==mList.size()) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        ((BaseActivity)mContext).showLoading();
        pageIndex++;
        loadData();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            loadData();
        }
    }

    private void loadData(){
        ItemApi.shopDetail((BaseActivity)mContext, pageIndex + "", pageSize + "",seller_id,type, new RequestPageCallback< List<MapiItemResult>>() {
            @Override
            public void success(Integer count, List<MapiItemResult> success) {
//                swipRefreshLayout.setRefreshing(false);
                ((BaseActivity)mContext).hideLoading();
                counts = count;
                if(success.isEmpty())
                    return;
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
//                swipRefreshLayout.setRefreshing(false);
                ((BaseActivity)mContext).hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }


    private TabSelListener tabSelListener;

    public void setTabSelListener(TabSelListener tabSelListener) {
        this.tabSelListener = tabSelListener;
    }

}
