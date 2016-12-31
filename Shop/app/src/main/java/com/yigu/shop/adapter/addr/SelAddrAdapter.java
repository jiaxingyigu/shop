package com.yigu.shop.adapter.addr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/11/23.
 */
public class SelAddrAdapter extends RecyclerView.Adapter<SelAddrAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiAddrResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public SelAddrAdapter(Context context, List<MapiAddrResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sel_addr, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiAddrResult addrResult = mList.get(position);
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });
        holder.consignee.setText("收货人："+addrResult.getConsignee());
        holder.tel.setText(addrResult.getTel());
        holder.address.setText(addrResult.getProvince()+addrResult.getCity()+addrResult.getDistrict()+addrResult.getAddress());

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_root)
        LinearLayout itemRoot;
        @Bind(R.id.sel_iv)
        ImageView selIv;
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
