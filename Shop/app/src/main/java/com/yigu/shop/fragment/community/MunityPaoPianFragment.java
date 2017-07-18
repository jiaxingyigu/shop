package com.yigu.shop.fragment.community;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.community.MunityAdapter;
import com.yigu.shop.adapter.community.MunityHostAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
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

/**
 * A simple {@link BaseFrag} subclass.
 */
public class MunityPaoPianFragment extends BaseFrag {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;

    List<IndexData> mList = new ArrayList<>();
    List<MapiMunityResult> data = new ArrayList<>();

    private final static String TEXT = "TEXT";
    private final static String IMAGE = "IMAGE";
    private final static String LIST = "LIST";
    MunityAdapter mAdapter;

    private Integer pageIndex = 1;
    private Integer pageSize = 11;
    private Integer counts;

    public MunityPaoPianFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_munity_host, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView() {

        mList = new ArrayList<>();
        data = new ArrayList<>();
        pageIndex = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MunityAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    public void load() {

        showLoading();
        CommunityApi.topiclist(getActivity(), "0", "47", "all", "1", pageIndex + "", pageSize + "", new RequestPageCallback<List<MapiMunityResult>>() {
            @Override
            public void success(Integer count, List<MapiMunityResult> success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if(success.isEmpty())
                    return;
                data.clear();
                data.addAll(success);

                int num = mList.size();
                if(data==null||data.size()==0){
                    num = 0;
                }else{
                    for (MapiMunityResult ware : data) {
                        String[] images = ware.getImageList();
                        DebugLog.i("images=>"+images.length);
                        if(null==images||images.length==0){
                            mList.add(new IndexData(num++,TEXT, ware));
                        }else if(images.length>=3){
                            mList.add(new IndexData(num++,LIST, ware));
                        }else{
                            mList.add(new IndexData(num++,IMAGE, ware));
                        }
                    }
                }

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
                MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
                ControllerUtil.go2ComDetail(mapiMunityResult.getTopic_id(),mapiMunityResult.getBoard_id());
            }
        });

    }

    public void loadNext() {
        if (counts == null || counts<=mList.size()) {
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
