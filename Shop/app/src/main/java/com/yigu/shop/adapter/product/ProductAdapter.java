package com.yigu.shop.adapter.product;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.shop.R;

import butterknife.Bind;
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
        holder.tvOriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_ori_price)
        TextView tvOriPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
