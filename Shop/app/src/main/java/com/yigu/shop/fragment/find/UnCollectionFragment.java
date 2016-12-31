package com.yigu.shop.fragment.find;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.shops.ShopAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class UnCollectionFragment extends BaseFrag {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    ShopAdapter mAdapter;
    private List<MapiShopResult> mList;
    private Integer pageIndex = 1;
    private Integer pageSize = 8;
    private Integer counts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        initListener();
        return view;
    }

    private void initView() {
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(),OrientationHelper.HORIZONTAL, DPUtil.dip2px(8),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopAdapter(getActivity(),mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load() {
        ItemApi.getshops(getActivity(), pageIndex + "", pageSize + "", "",new RequestPageCallback< List<MapiShopResult>>() {
            @Override
            public void success(Integer count, List<MapiShopResult> success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if(success.isEmpty())
                    return;
                mList.addAll(success);
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

    private void loadNext() {
        if (counts == null || counts==mList.size()) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
