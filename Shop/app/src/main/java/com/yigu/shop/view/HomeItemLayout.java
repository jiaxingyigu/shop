package com.yigu.shop.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.adapter.ItemTwoAdapter;
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

/**
 * Created by brain on 2016/9/23.
 */
public class HomeItemLayout extends RelativeLayout {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
//    @Bind(R.id.swipRefreshLayout)
//    BestSwipeRefreshLayout swipRefreshLayout;

    private Context mContext;
    private View view;
    ItemTwoAdapter mAdapter;
    List<MapiItemResult> mList = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer counts;
    MapiResourceResult mapiResourceResult;
    public HomeItemLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_item, this);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext,OrientationHelper.HORIZONTAL, DPUtil.dip2px(10),getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ItemTwoAdapter(mContext,mList);
        recyclerView.setAdapter(mAdapter);
        initListener();
    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ProductDetail(mList.get(position));
            }
        });

//        swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
//            @Override
//            public void onBestRefresh() {
//                refreshData();
//            }
//        });
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

    public void load(MapiResourceResult mapiResourceResult){
       /* if(!list.isEmpty()){
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }*/
        this.mapiResourceResult = mapiResourceResult;
        refreshData();
    }

    private void initData(){
//        ((BaseActivity)mContext).showLoading();
        DebugLog.i("HomeItemLayout=>"+mapiResourceResult.getCat_id());
        ItemApi.getGoods((BaseActivity)mContext,mapiResourceResult.getCat_id() ,"","",mapiResourceResult.getType(), pageIndex + "", new RequestPageCallback< List<MapiItemResult>>() {
            @Override
            public void success(Integer count,  List<MapiItemResult> success) {
//                swipRefreshLayout.setRefreshing(false);
//                ((BaseActivity)mContext).hideLoading();
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
//                ((BaseActivity)mContext).hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    private void loadNext() {
        if (counts == null || counts==pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        ((BaseActivity)mContext).showLoading();
        pageIndex++;
        initData();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 0;
            mAdapter.notifyDataSetChanged();
            initData();
        }
    }


}
