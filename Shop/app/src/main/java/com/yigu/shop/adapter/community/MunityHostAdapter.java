package com.yigu.shop.adapter.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.view.HomeSliderLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/11/21.
 */
public class MunityHostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    LayoutInflater inflater;

    private List<IndexData> mList;

    public MunityHostAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new HaveImageViewHolder(inflater.inflate(R.layout.item_munity_host_image, parent, false));
            case 1:
                return new HaveNoImageViewHolder(inflater.inflate(R.layout.item_munity_host_no_image, parent, false));
            default:
                return new HaveImageViewHolder(inflater.inflate(R.layout.item_munity_host_image, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HaveImageViewHolder) {
            setItemImage((HaveImageViewHolder) holder,position);
        }else if(holder instanceof HaveNoImageViewHolder){
            setItemNoImage((HaveNoImageViewHolder) holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "image":
                return 0;
            case "noimage":
                return 1;
            default:
                return 0;
        }
    }

    class HaveImageViewHolder extends RecyclerView.ViewHolder {
        public HaveImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HaveNoImageViewHolder extends RecyclerView.ViewHolder {
        public HaveNoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setItemImage(HaveImageViewHolder holdre,int position){

    }

    private void setItemNoImage(HaveNoImageViewHolder holder,int position){

    }

}
