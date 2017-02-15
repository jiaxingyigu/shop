package com.yigu.shop.adapter.shops;

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
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.view.ShopBgLayout;
import com.yigu.shop.view.ShopContentLayout;
import com.yigu.shop.view.ShopTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/28.
 */
public class ShopDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();
    private final static int SHOP_BG = 0;
    private final static int SHOP_TAB = 1;
    private final static int CONTENT = 2;
    private final static int ITEM = 3;
    private final static int DIVIDER = 4;

    public static final int HAS_STICKY_VIEW = 1;
    // RecyclerView 的不展示StickyLayout的item.
    public static final int NONE_STICKY_VIEW = 2;

    private TabSelListener tabSelListener;

    public void setTabSelListener(TabSelListener tabSelListener) {
        this.tabSelListener = tabSelListener;
    }

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public ShopDetailAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SHOP_BG:
                return new BGViewHolder(inflater.inflate(R.layout.lay_shop_bg, parent, false));
            case SHOP_TAB:
                return new TABViewHolder(inflater.inflate(R.layout.lay_shop_tab, parent, false));
            case CONTENT:
                return new ContentViewHolder(inflater.inflate(R.layout.lay_shop_content, parent, false));
            case ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.item_item, parent, false));
            case DIVIDER:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider, parent, false));
            default:
                return new BGViewHolder(inflater.inflate(R.layout.lay_shop_bg, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BGViewHolder) {
            ((BGViewHolder) holder).shopBgLayout.load((MapiShopResult) mList.get(position).getData());
            ((BGViewHolder) holder).shopBgLayout.setTag(NONE_STICKY_VIEW);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder, position);
        } else if (holder instanceof TABViewHolder) {
            ((TABViewHolder) holder).shopTabLayout.setTag(HAS_STICKY_VIEW);
            ((TABViewHolder) holder).shopTabLayout.setTabSelListener(new TabSelListener() {
                @Override
                public void tabSel(int position) {
                    if (null != tabSelListener)
                        tabSelListener.tabSel(position);
                }
            });
        } else if (holder instanceof ContentViewHolder) {
           /* ((ContentViewHolder) holder).shopContentLayout.load((MapiShopResult)mList.get(position).getData());
            ((ContentViewHolder) holder).shopContentLayout.setTag(HAS_STICKY_VIEW);
            ((ContentViewHolder) holder).shopContentLayout.setTabSelListener(new TabSelListener() {
                @Override
                public void tabSel(int position) {
                    if(null!=tabSelListener)
                        tabSelListener.tabSel(position);
                }
            });*/
        }else if(holder instanceof DividerViewHolder){
            ((DividerViewHolder) holder).dividerView.setTag(HAS_STICKY_VIEW);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "SHOP_BG":
                return SHOP_BG;
            case "SHOP_TAB":
                return SHOP_TAB;
            case "CONTENT":
                return CONTENT;
            case "ITEM":
                return ITEM;
            case "DIVIDER":
                return DIVIDER;
            default:
                return SHOP_BG;
        }
    }

    class BGViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.shopBgLayout)
        ShopBgLayout shopBgLayout;

        public BGViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TABViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.shopTabLayout)
        ShopTabLayout shopTabLayout;

        public TABViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

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

    class ContentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.shopContentLayout)
        ShopContentLayout shopContentLayout;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.divider_view)
        LinearLayout dividerView;
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setItem(ItemViewHolder holder, int position) {
//        holder.itemRoot.setTag(HAS_STICKY_VIEW);
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
        holder.price.setText(TextUtils.isEmpty(itemResult.getShop_price()) ? "￥0" : itemResult.getShop_price());
        if (null != itemResult.getSeller_info()) {
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


}
