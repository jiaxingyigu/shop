package com.yigu.shop.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/29.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private LayoutInflater inflater;

    public ProductAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_product_list, parent, false));
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
