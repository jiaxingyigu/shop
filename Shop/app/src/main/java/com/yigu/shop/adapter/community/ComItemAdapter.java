package com.yigu.shop.adapter.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
public class ComItemAdapter extends RecyclerView.Adapter<ComItemAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiResourceResult> mList;



    public ComItemAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_com_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiResourceResult item = mList.get(position);
        switch (item.getId()) {
            case ShopDataSource.TYPE_qiuzhi:
                holder.image.setImageResource(R.mipmap.qiuzhi);
                break;
            case ShopDataSource.TYPE_mendian:
                holder.image.setImageResource(R.mipmap.mendian);
                break;
            case ShopDataSource.TYPE_anli:
                holder.image.setImageResource(R.mipmap.anli);
                break;
            case ShopDataSource.TYPE_wenti:
                holder.image.setImageResource(R.mipmap.wenti);
                break;
            case ShopDataSource.TYPE_geshujijian:
                holder.image.setImageResource(R.mipmap.geshujijian);
                break;
            case ShopDataSource.TYPE_kuaisu:
                holder.image.setImageResource(R.mipmap.kuaisu);
                break;
        }

        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.root_view)
        RelativeLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
