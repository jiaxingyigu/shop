package com.yigu.shop.adapter.shops;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiShopResult> mList;
    private Context mContext;

    public ShopAdapter(Context context, List<MapiShopResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bestLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(DPUtil.dip2px(5), 0, DPUtil.dip2px(5), DPUtil.dip2px(5));
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
            view.setTag(i);
            holder.bestLayout.addView(view, layoutParams);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainToast.showShortToast("第" + v.getTag() + "张");
                }
            });
        }
        holder.shop.setTag(position);
        holder.shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainToast.showShortToast("第" + view.getTag() + "家店");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.shop_rl)
        RelativeLayout shopRl;
        @Bind(R.id.best_layout)
        LinearLayout bestLayout;
        @Bind(R.id.shop)
        TextView shop;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}