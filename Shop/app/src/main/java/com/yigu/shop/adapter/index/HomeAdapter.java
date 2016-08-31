package com.yigu.shop.adapter.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.view.HomeBestLayout;
import com.yigu.shop.view.HomeHostLayout;
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
    private final static int HOST = 1;
    private final static int TOOL = 2;
    private final static int BSET_SHOP = 3;

    LayoutInflater inflater;

    private List<IndexData> mList;

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
            case HOST:
                return new HostViewHolder(inflater.inflate(R.layout.lay_home_host, parent, false));
            case TOOL:
                return new ToolViewHolder(inflater.inflate(R.layout.lay_home_tool, parent, false));
            case BSET_SHOP:
                return new BestViewHolder(inflater.inflate(R.layout.lay_home_best, parent, false));
            default:
                return new SliderViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SliderViewHolder) {
            ((SliderViewHolder)holder).homeSliderLayout.load((List<MapiResourceResult>) mList.get(position).getData());
        }else if(holder instanceof HostViewHolder){
            ((HostViewHolder)holder).homeHostLayout.load((List<MapiResourceResult>) mList.get(position).getData());
        }else if(holder instanceof BestViewHolder){
            ((BestViewHolder)holder).homeBestLayout.load((List<MapiShopResult>) mList.get(position).getData());
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "SCROLL":
                return SLIDER_IMAGE;
            case "HOST":
                return HOST;
            case "TOOL":
                return TOOL;
            case "BSET_SHOP":
                return BSET_SHOP;
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

    class HostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeHostLayout)
        HomeHostLayout homeHostLayout;
        public HostViewHolder(View itemView) {
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

    class BestViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.homeBestLayout)
        HomeBestLayout homeBestLayout;
        public BestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
