package com.yigu.shop.activity.community;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.search.UserSearchAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
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

public class FollowListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    List<MapiMunityUserResult> mList = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer pageSize = 11;
    private Integer counts;

    UserSearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back);
        center.setText("我的关注");

        mList = new ArrayList<>();
        pageIndex = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new UserSearchAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
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

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() >= 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiMunityUserResult mapiMunityUserResult = mList.get(position);
                boolean isEdit;
                if(comUserSP.getUserBean().getUid().equals(mapiMunityUserResult.getUid()))
                    isEdit = true;
                else
                    isEdit = false;
                ControllerUtil.go2ComPersonInfo(mapiMunityUserResult.getUid(),isEdit);
            }
        });

        mAdapter.setNotifyListener(new UserSearchAdapter.NotifyListener() {
            @Override
            public void notifyMethod(int position) {
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    public void load() {
        showLoading();
        CommunityApi.userlist(this, pageIndex + "", pageSize + "", new RequestPageCallback<List<MapiMunityUserResult>>() {
            @Override
            public void success(Integer count, List<MapiMunityUserResult> success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if (success.isEmpty())
                    return;
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback2() {
            @Override
            public void error(String code, String message) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    public void loadNext() {
        if (counts == null || counts <= mList.size()) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }

        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
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
