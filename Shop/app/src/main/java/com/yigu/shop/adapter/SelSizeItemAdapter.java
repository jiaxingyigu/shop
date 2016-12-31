package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/12/8.
 */
public class SelSizeItemAdapter extends RecyclerView.Adapter<SelSizeItemAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private Context mContext;
    List<MapiResourceResult> mList = new ArrayList<>();
    int selPos = -1;
    public SelSizeItemAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 :mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_item_sel_size, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(mList.get(position).getAttr_value());
        if (mList.get(position).isSel()) {
            holder.content.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
            holder.ll_tv.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);
        } else {
            holder.content.setTextColor(mContext.getResources().getColor(R.color.shop_black));
            holder.ll_tv.setBackgroundResource(R.drawable.rect_stroke_gray__width_1_round_4);
        }

        holder.ll_tv.setTag(position);
        holder.ll_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selPos>=0){
                    if(selPos==(Integer) view.getTag()){
                        mList.get(selPos).setSel(false);
                        selPos = -1;
                    }else{
                        mList.get(selPos).setSel(false);
                        mList.get((Integer) view.getTag()).setSel(true);
                        selPos = (Integer) view.getTag();
                    }
                }else{
                    mList.get((Integer) view.getTag()).setSel(true);
                    selPos = (Integer) view.getTag();
                }

                notifyDataSetChanged();

            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.ll_tv)
        LinearLayout ll_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
