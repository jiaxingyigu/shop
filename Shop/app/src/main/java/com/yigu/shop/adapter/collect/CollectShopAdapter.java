package com.yigu.shop.adapter.collect;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;

public class CollectShopAdapter extends  RecyclerView.Adapter <CollectShopAdapter.ViewHolder> {
    private LayoutInflater inflater;

    public CollectShopAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_col_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }



    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
