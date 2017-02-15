package com.yigu.shop.adapter.index;

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
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.view.HomeBestLayout;
import com.yigu.shop.view.HomeItemLayout;
import com.yigu.shop.view.HomeSliderLayout;
import com.yigu.shop.view.HomeToolLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/30.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int SLIDER_IMAGE = 0;
    private final static int TOOL = 1;
    private final static int ITEM = 2;

    LayoutInflater inflater;

    private List<IndexData> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SLIDER_IMAGE:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
            case TOOL:
                return new ToolViewHolder(inflater.inflate(R.layout.lay_home_tool, parent, false));
            case ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.item_item, parent, false));
            default:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SliderViewHolder) {
            ((SliderViewHolder)holder).homeSliderLayout.load((List<MapiResourceResult>) mList.get(position).getData());
        }else if(holder instanceof ItemViewHolder){
//            MapiResourceResult mapiResourceResult = (MapiResourceResult) mList.get(position).getData();
//            ((ItemViewHolder)holder).homeItemLayout.load(mapiResourceResult);
            setItem((ItemViewHolder) holder,position);
        }else if(holder instanceof ToolViewHolder){
            ((ToolViewHolder)holder).homeToolLayout.setTypeListener(new HomeToolLayout.TypeListener() {
                @Override
                public void getType(String type) {
                    if(null!=typeListener)
                        typeListener.getType(type);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "SCROLL":
                return SLIDER_IMAGE;
            case "TOOL":
                return TOOL;
            case "ITEM":
                return ITEM;
            default:
                return SLIDER_IMAGE;
        }
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeSliderLayout)
        HomeSliderLayout homeSliderLayout;
        public SliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ToolViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeToolLayout)
        HomeToolLayout homeToolLayout;
        public ToolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
//        @Bind(R.id.homeItemLayout)
//        HomeItemLayout homeItemLayout;

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

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setItem(ItemViewHolder holder,int position){
        holder.itemRoot.setTag(position);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });

        MapiItemResult itemResult = (MapiItemResult) mList.get(position).getData();
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

        holder.goodsName.setText(itemResult.getGoods_name());
        holder.price.setText(TextUtils.isEmpty(itemResult.getShop_price())?"￥0":itemResult.getShop_price());
        if(null!=itemResult.getSeller_info()) {
            holder.shopName.setText(TextUtils.isEmpty(itemResult.getSeller_info().getShop_name()) ? "" : itemResult.getSeller_info().getShop_name());
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

    public interface TypeListener{
        void getType(String type);
    }

    private TypeListener typeListener;

    public void setTypeListener(TypeListener typeListener){
        this.typeListener = typeListener;
    }

}
