package com.yigu.shop.adapter.addr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.activity.addr.AddAddrActivity;
import com.yigu.shop.activity.addr.ModifyAddrActivity;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.base.RequestCode;
import com.yigu.shop.commom.api.ItemApi;
import com.yigu.shop.commom.result.MapiAddrResult;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/10/12.
 */
public class ManageAddrAdapter extends RecyclerView.Adapter<ManageAddrAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MapiAddrResult> mList;
    private Context mContext;
    public ManageAddrAdapter(Context context, List<MapiAddrResult> list) {
        this.mContext = context;
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
        final MapiAddrResult addrResult = mList.get(position);
        holder.consignee.setText("收货人：" + addrResult.getName());
        holder.tel.setText(addrResult.getTel());
        holder.address.setText(addrResult.getProvince_name() + addrResult.getCity_name() + addrResult.getDistrict_name() + mList.get(position).getAddress());
        holder.edit.setTag(position);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
               /* Intent intent = new Intent(mContext,ModifyAddrActivity.class);
                intent.putExtra("item",mList.get((Integer) view.getTag()));
                ((BaseActivity)mContext).startActivityForResult(intent, RequestCode.add_addr);*/

                if(null!=addrListener)
                    addrListener.edit(position);

            }
        });
        holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                if(null!=addrListener)
                    addrListener.del(position);
                /*ItemApi.delAddresses((BaseActivity) mContext, mList.get(position).getAddress_id(), new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        mList.remove(position);
                        notifyItemRangeRemoved(position,mList.size());
                    }
                }, new RequestExceptionCallback() {
                    @Override
                    public void error(Integer code, String message) {
                        MainToast.showShortToast(message);
                    }
                });*/
            }
        });

        if(addrResult.getDefault_address()==1)
            holder.img_addr_default.setImageResource(R.mipmap.sel_rect_red);
        else
            holder.img_addr_default.setImageResource(R.mipmap.sel_rect_black);

        holder.addr_default_ll.setTag(position);
        holder.addr_default_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(mList.get(pos).getDefault_address()==0){
                    if(null!=addrListener)
                        addrListener.defaultAddr(pos);
                }
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.consignee)
        TextView consignee;
        @Bind(R.id.tel)
        TextView tel;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.edit)
        TextView edit;
        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.img_addr_default)
        ImageView img_addr_default;
        @Bind(R.id.addr_default_ll)
        LinearLayout addr_default_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface AddrListener{
        void del(int postion);
        void edit(int postion);
        void defaultAddr(int postion);
    }

    private  AddrListener addrListener;

    public void setAddrListener(AddrListener addrListener){
        this.addrListener = addrListener;
    }

}
