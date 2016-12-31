package com.yigu.shop.adapter.order;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.yigu.shop.commom.util.DPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/10/14.
 */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    List<IndexData> mList = new ArrayList<>();

    public OrderAdapter(Context context, List<IndexData> list) {
        this.inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        /*int count = 0;//1
//        list.add(new IndexData(count++,"divider", new Object()));
        for (MapiCartResult ware : mList) {
            list.add(new IndexData(count++,"head",ware));
            for (int i=0;i<ware.getCart_goods().size();i++) {
                if(i == ware.getCart_goods().size()-1){
                    ware.getCart_goods().get(i).setLast(true);
                }else
                    ware.getCart_goods().get(i).setLast(false);
                list.add(new IndexData(count++,"item", ware.getCart_goods().get(i)));

            }
            list.add(new IndexData(count++,"bottom", new Object()));
            list.add(new IndexData(count++,"divider", new Object()));
        }
        return count;*/
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
                return new HeadViewHolder(inflater.inflate(R.layout.item_order_head, parent, false));
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
        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(HeadViewHolder holder, int position) {
        MapiItemResult itemResult = (MapiItemResult) mList.get(position).getData();
        holder.name.setText(itemResult.getShop_name());
    }

    private void setItem(ItemViewHolder holder, int position) {

        MapiItemResult itemResult = (MapiItemResult) mList.get(position).getData();

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(itemResult.getGoods_img()) ? "" : itemResult.getGoods_img());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(120), DPUtil.dip2px(120)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.content.setText(TextUtils.isEmpty(itemResult.getGoods_name()) ? "" : itemResult.getGoods_name());
        if (TextUtils.isEmpty(itemResult.getGoods_attr()))
            holder.goodsAttr.setVisibility(View.GONE);
        else {
            holder.goodsAttr.setVisibility(View.VISIBLE);
            holder.goodsAttr.setText(itemResult.getGoods_attr());
        }

        holder.price.setText(TextUtils.isEmpty(itemResult.getGoods_price()) ? "" : itemResult.getGoods_price());

        holder.num.setText("x" + (TextUtils.isEmpty(itemResult.getGoods_number()) ? "0" : itemResult.getGoods_number()));

    }

    private void setBottom(BottomViewHolder holder, int position) {
        MapiItemResult itemResult = (MapiItemResult) mList.get(position).getData();
        holder.ll_deel.setVisibility(View.GONE);
        holder.allNum.setText("共"+itemResult.getAllNum()+"件商品  合计  ￥");
        holder.allAcount.setText(itemResult.getAllAcount());
    }

}
