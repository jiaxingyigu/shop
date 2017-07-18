package com.yigu.shop.adapter.community;

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
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/27.
 */
public class JobListHisAdapter extends RecyclerView.Adapter<JobListHisAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiJobResult> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public JobListHisAdapter(Context context, List<MapiJobResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_job_his_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiJobResult mapiJobResult = mList.get(position);
        holder.title.setText(TextUtils.isEmpty(mapiJobResult.getPosition_name()) ? "" : mapiJobResult.getPosition_name());
        holder.company.setText("公司："+(TextUtils.isEmpty(mapiJobResult.getGongsi()) ? "" : mapiJobResult.getGongsi()));
        holder.date.setText(TextUtils.isEmpty(mapiJobResult.getTimestamp()) ? "" : mapiJobResult.getTimestamp());
        holder.status.setText(TextUtils.isEmpty(mapiJobResult.getStatus()) ? "" : mapiJobResult.getStatus());
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, pos);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.company)
        TextView company;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.root_view)
        LinearLayout rootView;
        @Bind(R.id.status)
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
