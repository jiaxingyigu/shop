package com.yigu.shop.adapter.search;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/20.
 */
public class ForumSearchAdapter extends RecyclerView.Adapter<ForumSearchAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiMunityResult> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ForumSearchAdapter(Context context, List<MapiMunityResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_munity_image_single, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiMunityResult mapiMunityResult = mList.get(position);
        holder.title.setText(mapiMunityResult.getTitle());
        holder.subject.setText(mapiMunityResult.getSubject());
        holder.date.setText(DateUtil.getInstance().string2YMD_H(mapiMunityResult.getLast_reply_date()));
        holder.hits.setText(TextUtils.isEmpty(mapiMunityResult.getHits()) ? "0" : mapiMunityResult.getHits());
        holder.nick.setText(mapiMunityResult.getUser_nick_name());

        if(TextUtils.isEmpty(mapiMunityResult.getPic_path())){
            holder.image.setVisibility(View.GONE);
        }else{
            holder.image.setVisibility(View.VISIBLE);
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(mapiMunityResult.getPic_path());
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                    .setResizeOptions(new ResizeOptions(DPUtil.dip2px(90), DPUtil.dip2px(70)))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(holder.image.getController())
                    .setControllerListener(new BaseControllerListener<ImageInfo>())
                    .build();
            holder.image.setController(controller);
        }


        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if(null!=onItemClickListener)
                    onItemClickListener.onItemClick(view,position);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_host)
        ImageView ivHost;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subject)
        TextView subject;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.hits)
        TextView hits;
        @Bind(R.id.root_view)
        LinearLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
