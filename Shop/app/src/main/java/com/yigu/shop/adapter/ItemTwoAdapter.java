package com.yigu.shop.adapter;

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
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/1.
 */
public class ItemTwoAdapter extends RecyclerView.Adapter<ItemTwoAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<MapiItemResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ItemTwoAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });

        MapiItemResult itemResult = mList.get(position);
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(itemResult.getGoods_img());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(400), DPUtil.dip2px(160)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.goodsName.setText(mList.get(position).getGoods_name());
        holder.price.setText(TextUtils.isEmpty(itemResult.getShop_price())?"￥0":itemResult.getShop_price());
        if(null!=itemResult.getSeller_info()){
            holder.shopName.setText(TextUtils.isEmpty(itemResult.getSeller_info().getShop_name())?"":itemResult.getSeller_info().getShop_name());
            //创建将要下载的图片的URI
            Uri imageUri2 = Uri.parse(itemResult.getSeller_info().getLogo());
            ImageRequest request2 = ImageRequestBuilder.newBuilderWithSource(imageUri2)
                    .setResizeOptions(new ResizeOptions(DPUtil.dip2px(20), DPUtil.dip2px(20)))
                    .build();
            DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request2)
                    .setOldController(holder.head.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .build();
            holder.head.setController(controller2);
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_root)
        LinearLayout itemRoot;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.goods_name)
        TextView goodsName;
        @Bind(R.id.shop_name)
        TextView shopName;
        @Bind(R.id.price)
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
