package com.yigu.shop.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.util.DPUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/10.
 */
public class ItemDiscussAdapter extends RecyclerView.Adapter<ItemDiscussAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    List<MapiOrderResult> mList;
    public ItemDiscussAdapter(Context context, List<MapiOrderResult> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_item_discuss, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       /* holder.itemLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, DPUtil.dip2px(10), 0);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_image, null);
            view.setTag(i);
            holder.itemLayout.addView(view, layoutParams);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }*/

        MapiOrderResult mapiOrderResult = mList.get(position);
        holder.author.setText(TextUtils.isEmpty(mapiOrderResult.getAuthor())?"":mapiOrderResult.getAuthor());
        holder.content.setText(TextUtils.isEmpty(mapiOrderResult.getContent())?"":mapiOrderResult.getContent());
        holder.create.setText(TextUtils.isEmpty(mapiOrderResult.getCreate())?"评论日期：":"评论日期："+mapiOrderResult.getCreate());
        if(TextUtils.isEmpty(mapiOrderResult.getRe_content())){
            holder.ll_re_content.setVisibility(View.GONE);
        }else{
            holder.ll_re_content.setVisibility(View.VISIBLE);
            holder.reContent.setText(mapiOrderResult.getRe_content());
        }



    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.create)
        TextView create;
        @Bind(R.id.re_content)
        TextView reContent;
        @Bind(R.id.ll_re_content)
        LinearLayout ll_re_content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
