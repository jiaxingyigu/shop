package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiArticleResult;
import com.yigu.shop.commom.result.MapiHelpResult;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.view.HelpItemLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/16.
 */
public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_ONE = 0;
    private final static int ITEM_TWO = 1;

    LayoutInflater inflater;

    private List<IndexData> mList;

    private RecyOnItemClickListener onItemClickListener;

    private Context mContext;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HelpAdapter(Context context, List<IndexData> list) {
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
                return new ItemOneViewHolder(inflater.inflate(R.layout.layout_help_item_one, parent, false));
            case ITEM_TWO:
                return new ItemTwoViewHolder(inflater.inflate(R.layout.lay_help, parent, false));
            default:
                return new ItemOneViewHolder(inflater.inflate(R.layout.layout_help_item_one, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemOneViewHolder) {
            setItemOne((ItemOneViewHolder) holder, position);
        } else if (holder instanceof ItemTwoViewHolder) {
            ((ItemTwoViewHolder) holder).helpItemLayout.load((List<MapiArticleResult>) mList.get(position).getData());
        }
    }

    class ItemOneViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView name;
        public ItemOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.helpItemLayout)
        HelpItemLayout helpItemLayout;

        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemOne(ItemOneViewHolder holder, int position) {
        MapiHelpResult mapiHelpResult = (MapiHelpResult) mList.get(position).getData();
        holder.name.setText(mapiHelpResult.getName());
    }

}
