package com.yigu.shop.fragment.sort;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.index.SortMenuAdapter;
import com.yigu.shop.adapter.sort.SortItemMenuAdapter;
import com.yigu.shop.adapter.sort.SortItemsAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiSortChildResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortItemsTwoFragment extends BaseFrag {

    @Bind(R.id.menu_recycler)
    RecyclerView menuRecycler;
    @Bind(R.id.item_recycler)
    RecyclerView itemRecycler;

    List<MapiSortResult> menuList = new ArrayList<>();
    List<MapiSortChildResult> itemList = new ArrayList<>();

    SortItemMenuAdapter menuAdapter;
    SortItemsAdapter itemsAdapter;

    List<IndexData> mList;

    public SortItemsTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sort_items_two, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void initView() {

        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        menuRecycler.setHasFixedSize(true);
        menuRecycler.setLayoutManager(linearLayoutManager);
        menuAdapter = new SortItemMenuAdapter(getActivity(),menuList);
        menuRecycler.setAdapter(menuAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        itemRecycler.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerListItemDecoration(getActivity(), OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.divider_line)));
        itemRecycler.setLayoutManager(linearLayoutManager2);
        itemsAdapter = new SortItemsAdapter(getActivity(), mList);
        itemRecycler.setAdapter(itemsAdapter);

    }

    private void initListener() {
        menuAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                notifyItemData(position);
//                MainToast.showShortToast(menuList.get(position).getName());
            }
        });



    }

    @Override
    public void load() {

        showLoading();
        ItemApi.categorylist(getActivity(),new RequestCallback< List<MapiSortResult>>() {
            @Override
            public void success(List<MapiSortResult> success) {
                hideLoading();
                if(success.isEmpty())
                    return;
                menuList.addAll(success);
                menuList.get(0).setChecked(true);
                menuAdapter.setSelPosition(0);
                menuAdapter.notifyDataSetChanged();
                notifyItemData(0);
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    public void refreshData() {
        if (null != menuList) {
            menuList.clear();
            itemList.clear();
            mList.clear();
            menuAdapter.notifyDataSetChanged();
            itemsAdapter.notifyDataSetChanged();
            load();
        }
    }


    private void notifyItemData(int position){
        itemList.clear();
        String c_id = menuList.get(position).getId();
        showLoading();
        ItemApi.categorylistbyid(getActivity(),c_id,new RequestCallback<List<MapiSortChildResult>>() {
            @Override
            public void success(List<MapiSortChildResult> success) {
                hideLoading();
                if(success.isEmpty())
                    return;
                itemList.addAll(success);
                notifySortData(itemList);

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    private void notifySortData(List<MapiSortChildResult> data){
        mList.clear();
        int num=0;
        if(data==null||data.size()==0){
            num = 0;
        }else{
            for (MapiSortChildResult ware : data) {
                mList.add(new IndexData(num++,"ITEM_TWO", ware));
            }
        }
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
