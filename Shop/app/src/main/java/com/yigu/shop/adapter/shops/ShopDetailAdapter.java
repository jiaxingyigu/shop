package com.yigu.shop.adapter.shops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.activity.shops.ShopDetailActivity;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.view.ShopBgLayout;
import com.yigu.shop.view.ShopContentLayout;

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
    private final static int CONTENT = 1;

    public static final int HAS_STICKY_VIEW = 1;
    // RecyclerView 的不展示StickyLayout的item.
    public static final int NONE_STICKY_VIEW = 2;

    private TabSelListener tabSelListener;
    public void setTabSelListener(TabSelListener tabSelListener){
        this.tabSelListener = tabSelListener;
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
            case CONTENT:
                return new ContentViewHolder(inflater.inflate(R.layout.lay_shop_content, parent, false));
            default:
                return new BGViewHolder(inflater.inflate(R.layout.lay_shop_bg, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BGViewHolder) {
            ((BGViewHolder) holder).shopBgLayout.load();
            ((BGViewHolder) holder).shopBgLayout.setTag(NONE_STICKY_VIEW);
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).shopContentLayout.load((MapiShopResult)mList.get(position).getData());
            ((ContentViewHolder) holder).shopContentLayout.setTag(HAS_STICKY_VIEW);
            ((ContentViewHolder) holder).shopContentLayout.setTabSelListener(new TabSelListener() {
                @Override
                public void tabSel(int position) {
                    if(null!=tabSelListener)
                        tabSelListener.tabSel(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "SHOP_BG":
                return SHOP_BG;
            case "CONTENT":
                return CONTENT;
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

    class ContentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.shopContentLayout)
        ShopContentLayout shopContentLayout;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
