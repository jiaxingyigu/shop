package com.yigu.shop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.adapter.sort.SortAdapter;
import com.yigu.shop.commom.result.MapiAttrResult;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.StringUtil;
import com.yigu.shop.view.SelSizeViewLayout;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/11/25.
 */
public class SelSizeAdapter extends RecyclerView.Adapter<SelSizeAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private Context mContext;
    List<MapiAttrResult> mList = new ArrayList<>();

    String[] attrValueStrs ;
    String[] attrPriceStrs;
    String[] attrIdStrs;

    public SelSizeAdapter(Context context, List<MapiAttrResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;

    }

    @Override
    public int getItemCount() {
        if(attrValueStrs==null){
            attrValueStrs = new String[mList.size()];
            attrPriceStrs = new String[mList.size()];
            attrIdStrs =  new String[mList.size()];
        }

        return mList == null ? 0 : mList.size();
    }

    @Override
    public SelSizeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sel_size, parent, false));
    }

    @Override
    public void onBindViewHolder(SelSizeAdapter.ViewHolder holder, int position) {
//        holder.title.setText(mList.get(position).getAttr_name());
        holder.selSizeViewLayout.setTag(position);
        if(null!=mList.get(position).getGoods_attr()){

            holder.selSizeViewLayout.setTitleTV(mList.get(position).getAttr_name());
            holder.selSizeViewLayout.load(mList.get(position).getAttr_name(),mList.get(position).getGoods_attr());

           /* GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
            SelSizeItemAdapter mAdapter = new SelSizeItemAdapter(mContext,mList.get(position).getGoods_attr());
            holder.recyclerView.setAdapter(mAdapter);*/
        }

        holder.selSizeViewLayout.setSelSizeListener(new SelSizeViewLayout.SelSizeInterface() {
            @Override
            public void sizeDetail(View view,String goods_attr, String price,String attr_id) {

                int pos = (int) view.getTag();
                DebugLog.i("selSizeViewLayout-pos:"+pos);
                attrValueStrs[pos] = TextUtils.isEmpty(goods_attr)?"":goods_attr;
                attrPriceStrs[pos] = TextUtils.isEmpty(price)?"0":price;
                attrIdStrs[pos] = TextUtils.isEmpty(attr_id)?"":attr_id;
                if(null!=selSizeInterface)
                    selSizeInterface.sizeDetail(attrValueStrs,attrPriceStrs,attrIdStrs);
            }
        });

       /* holder.flowLayout.removeAllViews();

        for (final MapiResourceResult resourceResult : mList.get(position).getGoods_attr()) {
            final TextView textView = new TextView(mContext);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT, DPUtil.dip2px(26));
            params.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(params);
            textView.setPadding(DPUtil.dip2px(15), DPUtil.dip2px(4), DPUtil.dip2px(15), DPUtil.dip2px(4));
            textView.setText(resourceResult.getAttr_value());
            textView.setGravity(Gravity.CENTER);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            if (resourceResult.isSel()) {
                textView.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
                textView.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);
            } else {
                textView.setTextColor(mContext.getResources().getColor(R.color.shop_black));
                textView.setBackgroundResource(R.drawable.rect_stroke_gray__width_1_round_4);
            }
            textView.setTag(position);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   *//* textView.setTextColor(mContext.getResources().getColor(R.color.light_yellow));
                    textView.setBackgroundResource(R.drawable.rect_stroke_yellow__width_1_round_4);*//*
                    resourceResult.setSel(!resourceResult.isSel());
                    notifyItemChanged((Integer) view.getTag());
                }
            });
            holder.flowLayout.addView(textView);
        }*/


    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        @Bind(R.id.flowLayout)
//        FlowLayout flowLayout;
//        @Bind(R.id.recyclerView)
//        RecyclerView recyclerView;
        @Bind(R.id.selSizeView)
        SelSizeViewLayout selSizeViewLayout;
//        @Bind(R.id.title)
//        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private SelSizeInterface selSizeInterface;

    public interface SelSizeInterface{
        void sizeDetail(String[] goodsAttrs,String[] prices,String[] attrIdStrs);
    }

    public void setSelSizeListener(SelSizeInterface selSizeInterface){
        this.selSizeInterface = selSizeInterface;
    }

}
