package com.yigu.shop.adapter.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.util.ShopDataSource;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/10/15.
 */
public class ComToolAdapter extends RecyclerView.Adapter<ComToolAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiResourceResult> mList;


    public ComToolAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_com_tool, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiResourceResult item = mList.get(position);
        switch (item.getId()){
            case ShopDataSource.TYPE_chitai:
                holder.image.setImageResource(R.mipmap.chitai);
                break;
            case ShopDataSource.TYPE_paopian:
                holder.image.setImageResource(R.mipmap.paopian);
                break;
            case ShopDataSource.TYPE_doudon:
                holder.image.setImageResource(R.mipmap.doudon);
                break;
            case ShopDataSource.TYPE_qita:
                holder.image.setImageResource(R.mipmap.qita);
                break;
            case ShopDataSource.TYPE_wanxue:
                holder.image.setImageResource(R.mipmap.wanxue);
                break;
            case ShopDataSource.TYPE_zhuanye:
                holder.image.setImageResource(R.mipmap.zhuanye);
                break;
            case ShopDataSource.TYPE_dipan:
                holder.image.setImageResource(R.mipmap.dipan);
                break;
            case ShopDataSource.TYPE_shebei:
                holder.image.setImageResource(R.mipmap.shebei);
                break;
        }

        holder.title.setText(item.getTitle());

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.title)
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
