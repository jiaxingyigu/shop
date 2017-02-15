package com.yigu.shop.fragment.index;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.adapter.index.HomeAdapter;
import com.yigu.shop.base.BaseFrag;
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
import com.yigu.shop.util.ControllerUtil;
import com.yigu.shop.widget.BestSwipeRefreshLayout;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class IndexDeviceFragment extends BaseFrag{
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

    List<MapiItemResult> data = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer counts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_index_device, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(),OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(getActivity(),mList);
        recyclerView.setAdapter(mAdapter);



    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;

    }

    private void initListener(){
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
        DebugLog.i("load===>"+cat_id);
       /* mList.clear();
        mList.add(new IndexData(0,TOOL,new ArrayList<MapiResourceResult>()));
        MapiResourceResult resourceResult = new MapiResourceResult();
        resourceResult.setType(typeStr);
        resourceResult.setCat_id(cat_id);
        mList.add(new IndexData(1,ITEM,resourceResult));
        Collections.sort(mList);
        mAdapter.notifyDataSetChanged();*/

        showLoading();
        ItemApi.getGoods(getActivity(),cat_id,"","",typeStr, pageIndex + "", new RequestPageCallback< List<MapiItemResult>>() {
            @Override
            public void success(Integer count,  List<MapiItemResult> success) {
                swipRefreshLayout.setRefreshing(false);
                hideLoading();
                counts = count;
                if(success.isEmpty())
                    return;
                data.addAll(success);
                int num = mList.size();
                if(data==null||data.size()==0){
                    num = 0;
                }else{
                    for (MapiItemResult ware : data) {
                        mList.add(new IndexData(num++,ITEM, ware));
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
            mList.add(new IndexData(0,TOOL,new ArrayList<MapiResourceResult>()));
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
