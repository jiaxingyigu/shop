package com.yigu.shop.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.util.DebugLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/10/14.
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    List<MapiCartResult> mList = new ArrayList<>();
    List<IndexData> list = new ArrayList<>();
    public OrderDetailAdapter(Context context, List<MapiCartResult> list) {
        this.inflater =  LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        int count = 0;//1
//        list.add(new IndexData(count++,"divider", new Object()));
        for (MapiCartResult ware : mList) {
            list.add(new IndexData(count++,"head",ware));
            for (int i=0;i<ware.getCart_goods().size();i++) {
                if(i == ware.getCart_goods().size()-1){
                    ware.getCart_goods().get(i).setLast(true);
                }else
                    ware.getCart_goods().get(i).setLast(false);
                list.add(new IndexData(count++,"item", ware.getCart_goods().get(i)));

            }
            list.add(new IndexData(count++,"bottom", new Object()));
            list.add(new IndexData(count++,"divider", new Object()));
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        String type = list.get(position).getType();
        if (type.equals("item")) {
            return 2;
        } else if (type.equals("head")) {
            return 1;
        }else if(type.equals("bottom")){
            return 3;
        }
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeadViewHolder(inflater.inflate(R.layout.item_order_head, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.item_order_item, parent, false));
            case 3:
                return new BottomViewHolder(inflater.inflate(R.layout.item_order_bottom,parent,false));
            case 4:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider,parent,false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.item_order_head, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHead((HeadViewHolder) holder,position);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder,position);
        }else if(holder instanceof BottomViewHolder){
            setBottom((BottomViewHolder) holder,position);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.ll_deel)
        LinearLayout ll_deel;
        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void setHead(HeadViewHolder holder,int position) {
        DebugLog.i("HeadViewHolder=load");
    }

    private  void setItem(ItemViewHolder holder,int position) {
        DebugLog.i("ItemViewHolder=load");
    }

    private  void setBottom(BottomViewHolder holder,int position) {
        DebugLog.i("BottomViewHolder=load");
        holder.ll_deel.setVisibility(View.GONE);
    }

}
