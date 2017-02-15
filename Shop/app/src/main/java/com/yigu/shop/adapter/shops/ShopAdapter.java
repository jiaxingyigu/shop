package com.yigu.shop.adapter.shops;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<MapiShopResult> mList;
    private Context mContext;

    public ShopAdapter(Context context, List<MapiShopResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiShopResult shopResult = mList.get(position);
        holder.bestLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, 0, 0);
        List<MapiItemResult> items = mList.get(position).getGoods_list();
        if (null != items && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                final MapiItemResult itemResult = items.get(i);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
                view.setTag(i);
                holder.bestLayout.addView(view, layoutParams);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
                        ControllerUtil.go2ProductDetail(itemResult);
//                        MainToast.showShortToast("第" + v.getTag() + "张");
                    }
                });

                SimpleDraweeView iamge = (SimpleDraweeView) view.findViewById(R.id.image);
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(itemResult.getGoods_img());
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                        .setResizeOptions(new ResizeOptions(DPUtil.dip2px(120), DPUtil.dip2px(120)))
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(iamge.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>())
                        .build();
                iamge.setController(controller);

                TextView title = (TextView) view.findViewById(R.id.title);

                title.setText(itemResult.getGoods_name());

            }
        }

       /* holder.shop.setTag(position);
        holder.shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainToast.showShortToast("收藏");
            }
        });*/
        holder.shopRl.setTag(position);
        holder.shopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControllerUtil.go2ShopDetail(mList.get((Integer) view.getTag()));
            }
        });

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(shopResult.getLogo());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(36), DPUtil.dip2px(36)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.head.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.head.setController(controller);

        holder.shop_name.setText(shopResult.getName());


    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.head)
        SimpleDraweeView head;
        @Bind(R.id.shop_rl)
        RelativeLayout shopRl;
        @Bind(R.id.best_layout)
        LinearLayout bestLayout;
        @Bind(R.id.shop_name)
        TextView shop_name;

        //        @Bind(R.id.shop)
//        TextView shop;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
