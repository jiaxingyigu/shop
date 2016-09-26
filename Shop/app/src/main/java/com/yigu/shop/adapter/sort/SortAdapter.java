package com.yigu.shop.adapter.sort;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/26.
 */
public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SortAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sort, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.item_root)
        RelativeLayout itemRoot;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
