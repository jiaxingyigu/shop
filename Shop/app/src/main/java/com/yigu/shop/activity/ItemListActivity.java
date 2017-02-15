package com.yigu.shop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
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

public class ItemListActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    HomeAdapter mAdapter;
    List<IndexData> mList = new ArrayList<>();
    private final static String TOOL = "TOOL";
    private final static String ITEM = "ITEM";
    String cat_id = "";
    String typeStr = "";
    String name = "";

    List<MapiItemResult> data = new ArrayList<>();
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;

    private Integer pageIndex = 1;
    private Integer counts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            cat_id = getIntent().getStringExtra("c_id");
            name = getIntent().getStringExtra("name");
        }
        if (!TextUtils.isEmpty(cat_id)) {
            initView();
            initListener();
            load();
        }
    }

    private void initView() {

        back.setImageResource(R.mipmap.back);
        center.setText(name);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        mList.clear();
        data.clear();
        mList.add(new IndexData(0, TOOL, new ArrayList<MapiResourceResult>()));
        pageIndex = 1;
        mAdapter.notifyDataSetChanged();

    }

    private void initListener() {
        mAdapter.setTypeListener(new HomeAdapter.TypeListener() {
            @Override
            public void getType(String type) {
                typeStr = type;
                refreshData();
            }
        });

        swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
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
            }
        });

    }

    public void load() {
        DebugLog.i("load===>" + cat_id);
        showLoading();
        ItemApi.getGoods(this, cat_id, "", "", typeStr, pageIndex + "", new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer count, List<MapiItemResult> success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if (success.isEmpty())
                    return;
                data.addAll(success);
                int num = mList.size();
                if (data == null || data.size() == 0) {
                    num = 0;
                } else {
                    for (MapiItemResult ware : data) {
                        mList.add(new IndexData(num++, ITEM, ware));
//                        mList.add(new IndexData(num++,"divider", new Object()));
                    }
                }

                mAdapter.notifyDataSetChanged();

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    public void loadNext() {
        if (counts == null || counts <= pageIndex) {
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
            mList.add(new IndexData(0, TOOL, new ArrayList<MapiResourceResult>()));
            pageIndex = 1;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
