package com.yigu.shop.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yigu.shop.R;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.adapter.ItemAdapter;
import com.yigu.shop.adapter.index.SortMenuAdapter;
import com.yigu.shop.base.BaseFrag;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/1.
 */
public class SortFragment extends BaseFrag {

    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.menu_recycler)
    RecyclerView menuRecycler;
    @Bind(R.id.item_recycler)
    RecyclerView itemRecycler;
    List<MapiResourceResult> menuList = new ArrayList<>();
    List<MapiItemResult> itemList = new ArrayList<>();
    SortMenuAdapter menuAdapter;
    ItemAdapter itemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        ButterKnife.bind(this, view);
        initView();
        load();
        initListener();
        return view;
    }



    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        menuRecycler.setHasFixedSize(true);
        menuRecycler.setLayoutManager(linearLayoutManager);
        menuAdapter = new SortMenuAdapter(getActivity(),menuList);
        menuRecycler.setAdapter(menuAdapter);

        itemRecycler.addItemDecoration(new DividerGridItemDecoration(getActivity(), DPUtil.dip2px(10),getResources().getColor(R.color.dark_black)));//分割线
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        itemRecycler.setLayoutManager(gridLayoutManager);
        itemAdapter = new ItemAdapter(getActivity(),itemList);
        itemRecycler.setAdapter(itemAdapter);

    }

    private void initListener(){
        menuAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                notifyItemData(position);
                MainToast.showShortToast(menuList.get(position).getTitle());
            }
        });

        itemAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                notifyItemData(position);
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void load() {
        menuList.clear();
        menuList.add(new MapiResourceResult("设    备"));
        menuList.add(new MapiResourceResult("工    具"));
        menuList.add(new MapiResourceResult("调整主件"));
        menuList.add(new MapiResourceResult("修补耗材"));
        menuList.get(0).setChecked(true);
        menuAdapter.setSelPosition(0);
        menuAdapter.notifyDataSetChanged();
        notifyItemData(0);
    }

    private void notifyItemData(int position){
        itemList.clear();
        for(int i=0;i<position+1;i++){
            itemList.add(new MapiItemResult());
        }
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
