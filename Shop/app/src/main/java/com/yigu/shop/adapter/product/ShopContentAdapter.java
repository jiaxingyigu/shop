package com.yigu.shop.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/29.
 */
public class ShopContentAdapter extends RecyclerView.Adapter<ShopContentAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<MapiItemResult> mList;
    public ShopContentAdapter(Context context,List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_content, parent, false));
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
