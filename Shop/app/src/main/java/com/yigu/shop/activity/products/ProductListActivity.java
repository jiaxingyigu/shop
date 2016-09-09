package com.yigu.shop.activity.products;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.product.ProductAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipeLayout;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.purcase_iv)
    ImageView purcaseIv;
    @Bind(R.id.lay_header)
    RelativeLayout layHeader;
    @Bind(R.id.tv_multiple)
    TextView tvMultiple;
    @Bind(R.id.tv_new)
    TextView tvNew;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.iv_price)
    ImageView ivPrice;

    ProductAdapter mAdapter;
    private TextView tvSelect;
    private int imageIndex = 0;
    private int[] imageIds = {R.drawable.icon_price_h, R.drawable.icon_price_l};
    private Integer pageIndex = 0;
    private Integer pageSize = 6;
    private Integer ISNEXT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), Color.parseColor("#696969")));
        recyclerView.setLayoutManager(manager);
        mAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(mAdapter);
        tvSelect = tvMultiple;
    }

    private void initListener() {
        swipeLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
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
    }

    private void changeText(View tv) {
        if (tvSelect == tv) return;
        tvSelect.setTextColor(Color.parseColor("#eeeeee"));
        tvSelect = (TextView) tv;
        tvSelect.setTextColor(Color.parseColor("#d98d2b"));
    }

    private void load() {

    }

    private void loadNext() {
        if (ISNEXT != null && ISNEXT == 0) {
            return;
        }
        pageIndex++;
        load();
    }

    public void refreshData() {
//        if (null != mList) {
//            mList.clear();
//            pageIndex = 0;
//            mAdapter.notifyDataSetChanged();
//            load();
//        }
    }


    @OnClick({R.id.tv_multiple, R.id.tv_new, R.id.tv_hot, R.id.tv_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_multiple:
                changeText(view);
                break;
            case R.id.tv_new:
                if (tvSelect == view) return;
                changeText(view);
                break;
            case R.id.tv_hot:
                changeText(view);
                break;
            case R.id.tv_price:
                changeText(view);
                imageIndex++;
                ivPrice.setImageResource(imageIds[imageIndex%2]);
                break;
        }
    }
}
