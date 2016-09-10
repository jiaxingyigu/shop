package com.yigu.shop.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/10.
 */
public class ItemDiscussAdapter extends RecyclerView.Adapter<ItemDiscussAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    public ItemDiscussAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_item_discuss, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, DPUtil.dip2px(10),0);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_image, null);
            view.setTag(i);
            holder.itemLayout.addView(view, layoutParams);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.info)
        TextView info;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
