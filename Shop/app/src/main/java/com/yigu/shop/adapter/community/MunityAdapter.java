package com.yigu.shop.adapter.community;

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
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/11/21.
 */
public class MunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;

    private List<IndexData> mList;

    private Context mContext;

    public MunityAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new HaveNoImageViewHolder(inflater.inflate(R.layout.item_munity_text, parent, false));
            case 1:
                return new HaveImageViewHolder(inflater.inflate(R.layout.item_munity_image_single, parent, false));
            case 2:
                return new HaveListViewHolder(inflater.inflate(R.layout.item_munity_image_list, parent, false));
            default:
                return new HaveNoImageViewHolder(inflater.inflate(R.layout.item_munity_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HaveImageViewHolder) {
            setItemImage((HaveImageViewHolder) holder, position);
        } else if (holder instanceof HaveNoImageViewHolder) {
            setItemNoImage((HaveNoImageViewHolder) holder, position);
        } else if (holder instanceof HaveListViewHolder) {
            setItemListImage((HaveListViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "TEXT":
                return 0;
            case "IMAGE":
                return 1;
            case "LIST":
                return 2;
            default:
                return 0;
        }
    }

    class HaveImageViewHolder extends RecyclerView.ViewHolder {
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

        public HaveImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HaveNoImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_host)
        ImageView ivHost;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subject)
        TextView subject;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.hits)
        TextView hits;

        public HaveNoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HaveListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_host)
        ImageView ivHost;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subject)
        TextView subject;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.hits)
        TextView hits;
        @Bind(R.id.ll_image)
        LinearLayout ll_image;

        public HaveListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setItemNoImage(HaveNoImageViewHolder holder, int position) {
        holder.ivHost.setVisibility(View.VISIBLE);
        MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
        holder.title.setText(mapiMunityResult.getTitle());
        holder.subject.setText(mapiMunityResult.getSubject());
        holder.date.setText(DateUtil.getInstance().string2YMD_H(mapiMunityResult.getLast_reply_date()));
        holder.hits.setText(TextUtils.isEmpty(mapiMunityResult.getHits()) ? "0" : mapiMunityResult.getHits());
        holder.nick.setText(mapiMunityResult.getUser_nick_name());

    }

    private void setItemImage(HaveImageViewHolder holder, int position) {
        holder.ivHost.setVisibility(View.VISIBLE);
        MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
        holder.title.setText(mapiMunityResult.getTitle());
        holder.subject.setText(mapiMunityResult.getSubject());
        holder.date.setText(DateUtil.getInstance().string2YMD_H(mapiMunityResult.getLast_reply_date()));
        holder.hits.setText(TextUtils.isEmpty(mapiMunityResult.getHits()) ? "0" : mapiMunityResult.getHits());
        holder.nick.setText(mapiMunityResult.getUser_nick_name());
        //创建将要下载的图片的URI
        String[] images = mapiMunityResult.getImageList();
        Uri imageUri = Uri.parse(images[0]);
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

    private void setItemListImage(HaveListViewHolder holder, int position) {

        holder.ivHost.setVisibility(View.VISIBLE);
        MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
        holder.title.setText(mapiMunityResult.getTitle());
        holder.subject.setText(mapiMunityResult.getSubject());
        holder.date.setText(DateUtil.getInstance().string2YMD_H(mapiMunityResult.getLast_reply_date()));
        holder.hits.setText(TextUtils.isEmpty(mapiMunityResult.getHits()) ? "0" : mapiMunityResult.getHits());
        holder.nick.setText(mapiMunityResult.getUser_nick_name());

        holder.ll_image.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, 1, 0);
        String[] images = mapiMunityResult.getImageList();
        if (null != images &&images.length>0) {
            int length = 0;
            if(images.length>3)
                length = 3;
            else
                length = images.length;
            for (int i = 0; i < length; i++) {
                final String path = images[i];
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
                view.setTag(i);
                holder.ll_image.addView(view, layoutParams);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag();
//                        ControllerUtil.go2ProductDetail(itemResult);
//                        MainToast.showShortToast("第" + v.getTag() + "张");
                    }
                });

                SimpleDraweeView iamge = (SimpleDraweeView) view.findViewById(R.id.image);
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(path);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                        .setResizeOptions(new ResizeOptions(DPUtil.dip2px(130), DPUtil.dip2px(90)))
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(iamge.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>())
                        .build();
                iamge.setController(controller);
            }
        }


    }


}
