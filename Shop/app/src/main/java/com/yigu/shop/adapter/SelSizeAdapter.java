package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/11/25.
 */
public class SelSizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private Context mContext;
    List<MapiResourceResult> mList = new ArrayList<>();

    public SelSizeAdapter(Context context,List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
