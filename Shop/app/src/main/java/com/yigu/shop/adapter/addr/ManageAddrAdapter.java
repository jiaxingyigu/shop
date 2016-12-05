package com.yigu.shop.adapter.addr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiAddrResult;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/10/12.
 */
public class ManageAddrAdapter extends RecyclerView.Adapter<ManageAddrAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiAddrResult> mList;

    public ManageAddrAdapter(Context context, List<MapiAddrResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_manage_addr, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.consignee.setText("收货人："+mList.get(position).getConsignee());
        holder.tel.setText(mList.get(position).getTel());
        holder.address.setText(mList.get(position).getAddress());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.consignee)
        TextView consignee;
        @Bind(R.id.tel)
        TextView tel;
        @Bind(R.id.address)
        TextView address;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
