package com.yigu.shop.adapter.sort;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiSortChildResult;
import com.yigu.shop.commom.result.MapiSortResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.view.SortItemTwoLayout;
import com.yigu.shop.widget.DividerGridItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/16.
 */
public class SortItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_ONE = 0;
    private final static int ITEM_TWO = 1;

    LayoutInflater inflater;

    private List<IndexData> mList;

    private RecyOnItemClickListener onItemClickListener;

    private Context mContext;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SortItemsAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "ITEM_ONE":
                return ITEM_ONE;
            case "ITEM_TWO":
                return ITEM_TWO;
            default:
                return ITEM_ONE;
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_ONE:
                return new ItemOneViewHolder(inflater.inflate(R.layout.layout_sort_item_one, parent, false));
            case ITEM_TWO:
                return new ItemTwoViewHolder(inflater.inflate(R.layout.lay_sort_item_two, parent, false));
            default:
                return new ItemOneViewHolder(inflater.inflate(R.layout.layout_sort_item_one, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemOneViewHolder) {
            setItemOne((ItemOneViewHolder) holder, position);
        } else if (holder instanceof ItemTwoViewHolder) {
            ((ItemTwoViewHolder) holder).sortItemTwoLayout.load((MapiSortChildResult) mList.get(position).getData());
        }
    }

    class ItemOneViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_one)
        LinearLayout item_one;
        @Bind(R.id.sort_tv)
        TextView sortTv;

        public ItemOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sortItemTwoLayout)
        SortItemTwoLayout sortItemTwoLayout;
        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemOne(ItemOneViewHolder holder, int position) {
        MapiSortResult mapiSortResult = (MapiSortResult) mList.get(position).getData();
        holder.sortTv.setText(mapiSortResult.getName());
        holder.item_one.setTag(position);
        holder.item_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });
    }

}
