package com.yigu.shop.adapter.purcase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.shopinterface.AdapterSelListener;
import com.yigu.shop.view.PurcaseSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/8.
 */
public class PurcaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    List<MapiCartResult> mList = new ArrayList<>();
    List<IndexData> list = new ArrayList<>();
    AdapterSelListener listener;

    public List<MapiCartResult> getmList() {
        return mList;
    }

    public void setOnAdapterSelListener(AdapterSelListener listener){
        this.listener = listener;
    }
    public PurcaseAdapter(Context context,List<MapiCartResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (MapiCartResult ware : mList) {
            list.add(new IndexData(count++,"head",ware));
            for (int i=0;i<ware.getItems().size();i++) {
                if(i == ware.getItems().size()-1){
                    ware.getItems().get(i).setLast(true);
                }else
                    ware.getItems().get(i).setLast(false);
                list.add(new IndexData(count++,"item", ware.getItems().get(i)));

            }
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
        }
        return 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.item_purcase_item, parent, false));
            case 3:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider,parent,false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHead((HeadViewHolder) holder,position);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder,position);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_sel)
        ImageView rootSel;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.shop_rl)
        RelativeLayout shopRl;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_sel)
        ImageView itemSel;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.purcaseSheetLayout)
        PurcaseSheetLayout purcaseSheetLayout;
        @Bind(R.id.divider)
        View divider;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(HeadViewHolder holder,int position){
        DebugLog.i("HeadViewHolder=load");
        holder.shopRl.setTag(position);
        holder.shopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        MapiCartResult ware = (MapiCartResult) list.get(position).getData();
        boolean isAll = true;
        for (MapiItemResult item : ware.getItems()) {
            if (!item.isSel()) {
                isAll = false;
                break;
            }
        }
        ware.setSel(isAll);
        if(null!=listener){
            listener.isAll();
        }
        if(ware.isSel()){
            holder.rootSel.setImageResource(R.mipmap.circle_yellow_sel);
        }else{
            holder.rootSel.setImageResource(R.mipmap.circle_white);
        }
        holder.rootSel.setTag(position);
        holder.rootSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                MapiCartResult ware = (MapiCartResult) list.get(position).getData();
//                    if (!ware.isSel()) {
//                        holder.rootSel.setImageResource(R.mipmap.circle_yellow_sel);
//                    } else {
//                        holder.rootSel.setImageResource(R.mipmap.circle_white);
//                    }
                ware.setSel(!ware.isSel());
                if(null!=listener){
                    listener.isAll();
                }
                for (MapiItemResult item : ware.getItems()) {
                    item.setSel(ware.isSel());
                }
                notifyDataSetChanged();
            }
        });
    }

    private  void setItem(ItemViewHolder holder,int position){
        DebugLog.i("ItemViewHolder=load");
        MapiItemResult result = (MapiItemResult) list.get(position).getData();
        if(result.isSel())
            holder.itemSel.setImageResource(R.mipmap.circle_yellow_sel);
        else
            holder.itemSel.setImageResource(R.mipmap.circle_white);
        if(result.isLast())
            holder.divider.setVisibility(View.GONE);
        else
            holder.divider.setVisibility(View.VISIBLE);
        holder.itemSel.setTag(position);
        holder.itemSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

                MapiItemResult item = (MapiItemResult) list.get(position).getData();
//                    if (!item.isSel()) {
//                        holder.itemSel.setImageResource(R.mipmap.circle_white);
//                    } else {
//                        holder.itemSel.setImageResource(R.mipmap.circle_yellow_sel);
//                    }
                item.setSel(!item.isSel());
                if(null!=listener){
                    listener.isAll();
                }
                notifyDataSetChanged();
            }
        });

    }


}
