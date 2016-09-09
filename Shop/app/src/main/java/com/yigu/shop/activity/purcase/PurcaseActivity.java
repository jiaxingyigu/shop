package com.yigu.shop.activity.purcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.purcase.PurcaseAdapter;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PurcaseActivity extends AppCompatActivity {

    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.account)
    TextView account;
    @Bind(R.id.statement)
    TextView statement;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.count_ll)
    LinearLayout countLl;
    private List<MapiCartResult> mList = new ArrayList<>();
    PurcaseAdapter mAdapter;
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
    private Integer ISNEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purcase);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        center.setText("购物车");
        tvRight.setText("编辑");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PurcaseAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
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
        mList.clear();
        List<MapiItemResult> itemList = new ArrayList<>();
        itemList.add(new MapiItemResult());
        mList.add(new MapiCartResult(itemList));
        itemList = new ArrayList<>();
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        itemList.add(new MapiItemResult());
        mList.add(new MapiCartResult(itemList));
        mAdapter.notifyDataSetChanged();
    }

    private void loadNext() {
        if (ISNEXT != null && ISNEXT == 0) {
            return;
        }
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

    @OnClick({R.id.back, R.id.tv_right, R.id.all, R.id.statement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tv_right:
                if ("编辑".equals(tvRight.getText().toString())) {
                    tvRight.setText("完成");
                    countLl.setVisibility(View.INVISIBLE);
                    statement.setText("删除");
                } else if ("完成".equals(tvRight.getText().toString())) {
                    tvRight.setText("编辑");
                    countLl.setVisibility(View.VISIBLE);
                    statement.setText("结算（1）");
                }
                break;
            case R.id.all:
                break;
            case R.id.statement:
                break;
        }
    }
}
