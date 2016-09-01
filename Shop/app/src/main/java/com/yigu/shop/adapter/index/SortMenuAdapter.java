package com.yigu.shop.adapter.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/1.
 */
public class SortMenuAdapter extends RecyclerView.Adapter<SortMenuAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<MapiResourceResult> mList;
    private int selPosition;
    private RecyOnItemClickListener onItemClickListener;
    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public SortMenuAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    public void setSelPosition(int position){
        selPosition = position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sort_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.menu.setTag(position);
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selPosition != (Integer) view.getTag()) {
                    mList.get(selPosition).setChecked(false);
                    mList.get((Integer) view.getTag()).setChecked(true);
                    selPosition = (Integer) view.getTag();
                    if(null!=onItemClickListener)
                        onItemClickListener.onItemClick(view,(Integer)view.getTag());
                }
                notifyDataSetChanged();
            }
        });
        if (mList.get(position).isChecked())
            holder.menu.setChecked(true);
        else
            holder.menu.setChecked(false);
        holder.menu.setText(mList.get(position).getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.menu)
        CheckBox menu;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
