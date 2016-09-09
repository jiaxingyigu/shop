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
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.view.PurcaseSheetLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/8.
 */
public class PurcaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiCartResult> mList;
    private Context mContext;
    private Map<Integer, Object> CART_ROW_TYPE = new HashMap<Integer, Object>();

    public PurcaseAdapter(Context context, List<MapiCartResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (MapiCartResult ware : mList) {
            CART_ROW_TYPE.put(count++, ware);
            for (int i=0;i<ware.getItems().size();i++) {
                if(i == ware.getItems().size()-1){
                    ware.getItems().get(i).setLast(true);
                }else
                    ware.getItems().get(i).setLast(false);
                CART_ROW_TYPE.put(count++, ware.getItems().get(i));

            }
            CART_ROW_TYPE.put(count++, new Object());
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = CART_ROW_TYPE.get(position);
        if (obj instanceof MapiItemResult) {
            return 2;
        } else if (obj instanceof MapiCartResult) {
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
            ((HeadViewHolder) holder).load((MapiCartResult) CART_ROW_TYPE.get(position),position);
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).load((MapiItemResult) CART_ROW_TYPE.get(position),position);
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

        public void load(MapiCartResult result,int position){
            shopRl.setTag(position);
            shopRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_sel)
        ImageView rootSel;
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

        public void load(MapiItemResult result,int position){
            if(result.isLast())
                divider.setVisibility(View.GONE);
            else
                divider.setVisibility(View.VISIBLE);
        }

    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
