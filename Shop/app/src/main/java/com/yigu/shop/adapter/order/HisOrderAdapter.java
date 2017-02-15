package com.yigu.shop.adapter.order;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiOrderItem;
import com.yigu.shop.commom.result.MapiOrderResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/17.
 */
public class HisOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    List<IndexData> mList = new ArrayList<>();

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HisOrderAdapter(Context context, List<IndexData> list) {
        this.inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals("item")) {
            return 2;
        } else if (type.equals("head")) {
            return 1;
        } else if (type.equals("bottom")) {
            return 3;
        }
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeadViewHolder(inflater.inflate(R.layout.item_order_head, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.item_order_item, parent, false));
            case 3:
                return new BottomViewHolder(inflater.inflate(R.layout.item_order_bottom, parent, false));
            case 4:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider, parent, false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_divider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHead((HeadViewHolder) holder, position);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder, position);
        } else if (holder instanceof BottomViewHolder) {
            setBottom((BottomViewHolder) holder, position);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.item_root)
        RelativeLayout itemRoot;
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.goods_attr)
        TextView goodsAttr;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.price_tip)
        TextView price_tip;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_deel)
        LinearLayout ll_deel;
        @Bind(R.id.allNum)
        TextView allNum;
        @Bind(R.id.allAcount)
        TextView allAcount;
        @Bind(R.id.ship_tv)
        TextView ship_tv;
        @Bind(R.id.cancel_deel)
        TextView cancel_deel;
        @Bind(R.id.pay_deel)
        TextView pay_deel;

        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(HeadViewHolder holder, int position) {
        MapiOrderResult itemResult = (MapiOrderResult) mList.get(position).getData();
        holder.name.setText(TextUtils.isEmpty(itemResult.getOrder_sn())?"":"订单号："+itemResult.getOrder_sn());
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,(Integer) view.getTag());
            }
        });
    }

    private void setItem(ItemViewHolder holder, int position) {

        MapiOrderItem itemResult = (MapiOrderItem) mList.get(position).getData();

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(itemResult.getImg_info().getUrl());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(120), DPUtil.dip2px(120)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.content.setText(TextUtils.isEmpty(itemResult.getName()) ? "" : itemResult.getName());

        String attrs = itemResult.getGoods_attr();
        if (TextUtils.isEmpty(attrs))
            holder.goodsAttr.setVisibility(View.GONE);
        else {
            holder.goodsAttr.setVisibility(View.VISIBLE);
            holder.goodsAttr.setText(attrs);
        }

        holder.price_tip.setVisibility(View.GONE);
        holder.price.setText(TextUtils.isEmpty(itemResult.getFormated_shop_price()) ? "￥0" : itemResult.getFormated_shop_price());

        holder.num.setText("x " + (TextUtils.isEmpty(itemResult.getGoods_number()) ? "0" : itemResult.getGoods_number()));

    }

    private void setBottom(BottomViewHolder holder, int position) {
        MapiOrderResult itemResult = (MapiOrderResult) mList.get(position).getData();
        holder.allNum.setText("共"+itemResult.getGoods_list().size()+"种商品  合计");
        holder.allAcount.setText(itemResult.getTotal_fee());
        holder.ship_tv.setText(String.format("(含运费%s)",TextUtils.isEmpty(itemResult.getFormated_shipping_fee())?"0":itemResult.getFormated_shipping_fee()));

        if(itemResult.getType().equals("await_ship")){
            holder.ll_deel.setVisibility(View.GONE);
        }else if(itemResult.getType().equals("shipped")){
//            holder.ll_deel.setVisibility(View.GONE);
            holder.cancel_deel.setVisibility(View.GONE);
            holder.pay_deel.setText("确认收货");
        }else if(itemResult.getType().equals("finished")){
            holder.ll_deel.setVisibility(View.GONE);
        }else{
            holder.ll_deel.setVisibility(View.VISIBLE);
        }

        holder.cancel_deel.setTag(position);
        holder.cancel_deel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(null!=deelOnListener)
                    deelOnListener.cancel_deel(pos);
            }
        });

        holder.pay_deel.setTag(position);
        holder.pay_deel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(null!=deelOnListener)
                    deelOnListener.pay_deel(pos);
            }
        });

    }

    public interface DeelOnListener{
        void cancel_deel(int position);
        void pay_deel(int position);
    }

    private DeelOnListener deelOnListener;

    public void setDeelOnListener(DeelOnListener deelOnListener){
        this.deelOnListener = deelOnListener;
    }


}
