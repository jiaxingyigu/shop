package com.yigu.shop.adapter.community.job;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiJobResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/27.
 */
public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiJobResult> mList;


    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public JobListAdapter(Context context, List<MapiJobResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_job_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        MapiJobResult mapiJobResult = mList.get(position);
        holder.title.setText(TextUtils.isEmpty(mapiJobResult.getTitle())?"":mapiJobResult.getTitle());
        holder.company.setText(TextUtils.isEmpty(mapiJobResult.getCompany())?"":mapiJobResult.getCompany());
        holder.regions.setText(TextUtils.isEmpty(mapiJobResult.getRegions())?"":mapiJobResult.getRegions());
        holder.xueli.setText(TextUtils.isEmpty(mapiJobResult.getXueli())?"":mapiJobResult.getXueli());
        holder.date.setText(TextUtils.isEmpty(mapiJobResult.getDate())?"":mapiJobResult.getDate());
        holder.pay.setText(TextUtils.isEmpty(mapiJobResult.getPay())?"":mapiJobResult.getPay());
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,pos);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.company)
        TextView company;
        @Bind(R.id.regions)
        TextView regions;
        @Bind(R.id.xueli)
        TextView xueli;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.pay)
        TextView pay;
        @Bind(R.id.root_view)
        LinearLayout rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
