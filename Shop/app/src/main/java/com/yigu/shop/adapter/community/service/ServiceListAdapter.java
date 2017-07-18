package com.yigu.shop.adapter.community.service;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/28.
 */
public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiMunityResult> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ServiceListAdapter(Context context, List<MapiMunityResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_service_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiMunityResult mapiMunityResult = mList.get(position);
        //创建将要下载的图片的URI
        String[] images = mapiMunityResult.getImageList();
        if(null!=images&&images.length>0){
            Uri imageUri = Uri.parse(images[0]);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                    .setResizeOptions(new ResizeOptions(DPUtil.dip2px(55), DPUtil.dip2px(55)))
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
        holder.send.setTag(position);
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,pos);
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.info)
        TextView info;
        @Bind(R.id.send)
        TextView send;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
