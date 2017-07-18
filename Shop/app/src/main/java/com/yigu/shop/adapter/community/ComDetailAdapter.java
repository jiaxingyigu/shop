package com.yigu.shop.adapter.community;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.result.MapiReplyResult;
import com.yigu.shop.commom.result.MapiTopicResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.view.ComDetailItemLayout;
import com.yigu.shop.view.ComDetailPortalLayout;
import com.yigu.shop.view.ComDetailTopicLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/16.
 */
public class ComDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;

    private List<IndexData> mList;

    public ComDetailAdapter(Context context, List<IndexData> list) {
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
            case 0:
                return new HeadViewHolder(inflater.inflate(R.layout.layout_com_detail_head, parent, false));
            case 1:
                return new TopicViewHolder(inflater.inflate(R.layout.lay_com_detail_topic, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.lay_com_detail_item, parent, false));
            case 3:
                return new PortalViewHolder(inflater.inflate(R.layout.lay_com_detail_portal, parent, false));
            case 4:
                return new ServiceHeadViewHolder(inflater.inflate(R.layout.layout_service_detail_head, parent, false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.lay_home_slider, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHeadView((HeadViewHolder) holder, position);
        } else if (holder instanceof TopicViewHolder) {
            ((TopicViewHolder) holder).comDetailTopic.load((MapiTopicResult) mList.get(position).getData());
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).comDetailItem.load((List<MapiReplyResult>) mList.get(position).getData());
        } else if (holder instanceof PortalViewHolder) {
            ((PortalViewHolder) holder).comDetailPortal.load((MapiTopicResult) mList.get(position).getData());
        }else if (holder instanceof ServiceHeadViewHolder) {
            setServiceHeadView((ServiceHeadViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "HEAD":
                return 0;
            case "TOPIC":
                return 1;
            case "ITEM":
                return 2;
            case "PORTAL":
                return 3;
            case "SERVICE_HEAD":
                return 4;
            default:
                return 0;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.hits)
        TextView hits;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ServiceHeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.info)
        TextView info;

        public ServiceHeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comDetailTopic)
        ComDetailTopicLayout comDetailTopic;

        public TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PortalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comDetailPortal)
        ComDetailPortalLayout comDetailPortal;

        public PortalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comDetailItem)
        ComDetailItemLayout comDetailItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHeadView(HeadViewHolder holder, int position) {
        MapiTopicResult mapiTopicResult = (MapiTopicResult) mList.get(position).getData();
        holder.title.setText(TextUtils.isEmpty(mapiTopicResult.getTitle()) ? "" : mapiTopicResult.getTitle());
        if (null == mapiTopicResult.getHits())
            holder.hits.setText(TextUtils.isEmpty(mapiTopicResult.getViewNum()) ? "" : mapiTopicResult.getViewNum());
        else
            holder.hits.setText(TextUtils.isEmpty(mapiTopicResult.getHits()) ? "" : mapiTopicResult.getHits());
    }

    private void setServiceHeadView(ServiceHeadViewHolder holder,int position){
        MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
        //创建将要下载的图片的URI
        String[] images = mapiMunityResult.getImageList();
        if(null!=images&&images.length>0){
            Uri imageUri = Uri.parse(images[0]);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                    .setResizeOptions(new ResizeOptions(DPUtil.dip2px(95), DPUtil.dip2px(95)))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(holder.image.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .build();
            holder.image.setController(controller);
        }

        holder.name.setText(TextUtils.isEmpty(mapiMunityResult.getTitle())?"":mapiMunityResult.getTitle());
        holder.info.setText(TextUtils.isEmpty(mapiMunityResult.getSubject())?"":mapiMunityResult.getSubject());
    }

}
