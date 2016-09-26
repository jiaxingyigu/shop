package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/1.
 */
public class ItemTwoAdapter extends RecyclerView.Adapter<ItemTwoAdapter.ViewHolder> {
    private LayoutInflater inflater;
    List<MapiItemResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ItemTwoAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,(Integer)view.getTag());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_root)
        LinearLayout itemRoot;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
