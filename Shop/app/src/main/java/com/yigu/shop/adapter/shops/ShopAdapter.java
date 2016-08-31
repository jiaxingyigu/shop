package com.yigu.shop.adapter.shops;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;

import butterknife.ButterKnife;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private LayoutInflater inflater;

    public ShopAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
