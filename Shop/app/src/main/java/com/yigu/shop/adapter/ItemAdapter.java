package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiItemResult;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/1.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private LayoutInflater inflater;
    List<MapiItemResult> mList;
    public ItemAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
