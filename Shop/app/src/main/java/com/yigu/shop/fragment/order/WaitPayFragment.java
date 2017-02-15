package com.yigu.shop.fragment.order;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.adapter.order.HisOrderAdapter;
import com.yigu.shop.adapter.order.OrderAdapter;
import com.yigu.shop.adapter.purcase.PurcaseAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiOrderItem;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.util.RequestPageCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link BaseFrag} subclass.
 */
public class WaitPayFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    private Integer pageIndex = 1;
    private Integer counts;

    HisOrderAdapter mAdapter;

    List<IndexData> mList = new ArrayList<>();
    List<MapiOrderResult> list = new ArrayList<>();

    public WaitPayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), Color.parseColor("#eeeeee")));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HisOrderAdapter(getActivity(),mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {

        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2HisOrderDetail((MapiOrderResult) mList.get(position).getData());
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

        mAdapter.setDeelOnListener(new HisOrderAdapter.DeelOnListener() {
            @Override
            public void cancel_deel(int position) {
                final MapiOrderResult delapiOrderResult = (MapiOrderResult) mList.get(position).getData();
                showLoading();
                ItemApi.ordercancel(getActivity(), delapiOrderResult.getOrder_id(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        hideLoading();

                        Iterator<MapiOrderResult> it =list.iterator();
                        while(it.hasNext()){
                            MapiOrderResult mapiOrderResult1 = it.next();
                            if(mapiOrderResult1.getOrder_id()==delapiOrderResult.getOrder_id()){
                                it.remove();
                            }
                        }

                        refreshItemList(list);

                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        hideLoading();
                        MainToast.showShortToast(message);
                    }
                });

            }

            @Override
            public void pay_deel(int position) {
                MapiOrderResult delapiOrderResult = (MapiOrderResult) mList.get(position).getData();
                ControllerUtil.go2SelPay(delapiOrderResult);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void load() {
        showLoading();
        ItemApi.getorderlist(getActivity(), "await_pay", pageIndex+"", new RequestPageCallback<List<MapiOrderResult>>() {
            @Override
            public void success(Integer count, List<MapiOrderResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                counts = count;
                if(null==success||success.isEmpty())
                    return;
                list.addAll(success);
                refreshItemList(list);

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                MainToast.showShortToast(message);
            }
        });
    }

    private void refreshItemList(List<MapiOrderResult> data){

        mList.clear();
        int num = 0;
        if (data == null || data.size() == 0) {
            num = 0;
        } else {
            for (MapiOrderResult ware : data) {
                ware.setType("await_pay");
                mList.add(new IndexData(num++, "head", ware));
                for(MapiOrderItem mapiOrderItem : ware.getGoods_list()){
                    mList.add(new IndexData(num++, "item", mapiOrderItem));
                }
                mList.add(new IndexData(num++, "bottom", ware));
                mList.add(new IndexData(num++, "divider", new Object()));
            }
        }
        mAdapter.notifyDataSetChanged();

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
            list.clear();
            pageIndex = 1;
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
