package com.yigu.shop.fragment.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.search.PortalSearchAdapter;
import com.yigu.shop.adapter.search.UserSearchAdapter;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.result.MapiMunityUserResult;
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
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSearchFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipRefreshLayout)
    BestSwipeRefreshLayout swipRefreshLayout;
    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.tv_right_two)
    TextView tvRightTwo;

    List<MapiMunityUserResult> mList = new ArrayList<>();

    private Integer pageIndex = 1;
    private Integer pageSize = 11;
    private Integer counts;

    UserSearchAdapter mAdapter;

    public UserSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
//        load();
        return view;
    }

    private void initView() {
        tvRightTwo.setText("搜索");
        tvRightTwo.setVisibility(View.VISIBLE);

        mList = new ArrayList<>();
        pageIndex = 1;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new UserSearchAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        contentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    if (!TextUtils.isEmpty(contentEt.getText().toString())) {
                        refreshData();
                    } else {
                        MainToast.showShortToast("请输入您要搜索的内容");
                    }

                }
                return true;
            }
        });

        swipRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {

                if (!TextUtils.isEmpty(contentEt.getText().toString())) {
                    refreshData();
                } else {
                    swipRefreshLayout.setRefreshing(false);
                    MainToast.showShortToast("请输入您要搜索的内容");
                }
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

        CommunityApi.searchuser(getActivity(), contentEt.getText().toString(), pageIndex + "", pageSize + "", new RequestPageCallback<List<MapiMunityUserResult>>() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_right_two)
    public void onClick() {
        if (!TextUtils.isEmpty(contentEt.getText().toString())) {
            refreshData();
        } else {
            MainToast.showShortToast("请输入您要搜索的内容");
        }
    }

}
