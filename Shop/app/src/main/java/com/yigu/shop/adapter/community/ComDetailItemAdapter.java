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
import com.yigu.shop.commom.result.MapiReplyResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/17.
 */
public class ComDetailItemAdapter extends RecyclerView.Adapter<ComDetailItemAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiReplyResult> mList;

    public ComDetailItemAdapter(Context context, List<MapiReplyResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_com_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiReplyResult item = mList.get(position);

        Uri imageUri = Uri.parse(item.getIcon());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(40), DPUtil.dip2px(40)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.replyName.setText(TextUtils.isEmpty(item.getReply_name())?"":item.getReply_name());
        holder.userTitle.setText(TextUtils.isEmpty(item.getUserTitle())?"":item.getUserTitle());

        String dateStr = TextUtils.isEmpty(item.getPosts_date())?"0":item.getPosts_date();
        Date targetDate = new Date();
        targetDate.setTime(Long.parseLong(dateStr));
        holder.date.setText(DateUtil.getInstance().date2PublishTime(targetDate));

        holder.position.setText((TextUtils.isEmpty(item.getPosition())?"1":item.getPosition())+"楼");

        if(null!=item.getReply_content()&&!item.getReply_content().isEmpty()){
            holder.infor.setText(TextUtils.isEmpty(item.getReply_content().get(0).getInfor())?"":item.getReply_content().get(0).getInfor());
        }



    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.reply_name)
        TextView replyName;
        @Bind(R.id.userTitle)
        TextView userTitle;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.position)
        TextView position;
        @Bind(R.id.infor)
        TextView infor;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
