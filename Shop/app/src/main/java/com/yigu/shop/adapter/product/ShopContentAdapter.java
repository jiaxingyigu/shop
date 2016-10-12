package com.yigu.shop.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiItemResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/29.
 */
public class ShopContentAdapter extends RecyclerView.Adapter<ShopContentAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiItemResult> mList;

    public ShopContentAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_content, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MapiItemResult item = mList.get(position);
        holder.goodsName.setText(item.getGoods_name());
        holder.shopPrice.setText(TextUtils.isEmpty(item.getShop_price())?"":"￥"+item.getShop_price());

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.goods_name)
        TextView goodsName;
        @Bind(R.id.shop_price)
        TextView shopPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
