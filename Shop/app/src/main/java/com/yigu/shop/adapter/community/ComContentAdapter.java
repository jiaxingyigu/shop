package com.yigu.shop.adapter.community;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.shop.R;
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.MapiContentResult;
import com.yigu.shop.commom.result.MapiTopicResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.util.webview.WebViewUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/16.
 */
public class ComContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;

    private List<MapiContentResult> mList;

    private Context mContext;

    private boolean isFouces = false;

    public ComContentAdapter(Context context, List<MapiContentResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    public void setFouces(boolean isFouces){
        this.isFouces = isFouces;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new TextViewHolder(inflater.inflate(R.layout.layout_com_content_text, parent, false));
            case 1:
                return new ImageViewHolder(inflater.inflate(R.layout.layout_com_content_image, parent, false));
            case 2:
                return new VideoViewHolder(inflater.inflate(R.layout.layout_com_content_video, parent, false));
            default:
                return new TextViewHolder(inflater.inflate(R.layout.layout_com_content_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            setText((TextViewHolder) holder, position);
        } else if (holder instanceof ImageViewHolder) {
            setImage((ImageViewHolder) holder, position);
        } else if (holder instanceof VideoViewHolder) {
            setVideo((VideoViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "text":
            case "0":
                return 0;
            case "image":
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text)
        TextView text;
        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.webview)
        WebView webview;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setText(TextViewHolder holder, int position){
        MapiContentResult mapiContentResult = mList.get(position);
        if(null==mapiContentResult.getInfor()){
            holder.text.setText(mapiContentResult.getContent());
        }else
            holder.text.setText(mapiContentResult.getInfor());
    }

    private void setImage(ImageViewHolder holder, int position){
        MapiContentResult mapiContentResult = mList.get(position);
        Uri imageUri;
        if(null==mapiContentResult.getInfor()){
            imageUri = Uri.parse(mapiContentResult.getContent());
        }else
            imageUri = Uri.parse(mapiContentResult.getInfor());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(360), DPUtil.dip2px(220)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);
    }

    private void setVideo(VideoViewHolder holder, int position){
        MapiContentResult mapiContentResult = mList.get(position);
        WebSettings ws = holder.webview.getSettings();

        ws.setAllowFileAccess(true);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAppCacheEnabled(true);
        ws.setBuiltInZoomControls(false);
        ws.setUseWideViewPort(false);// 可任意比例缩放
        ws.setPluginState(WebSettings.PluginState.ON);//设置webview支持插件
        ws.setSupportMultipleWindows(true);// 新加
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        holder.webview.addJavascriptInterface(new WebViewUtil((BaseActivity) mContext,holder.webview), "app");


        //我就是没有这一行，死活不出来。MD，硬是没有人写这一句的
        holder.webview.setWebChromeClient(new WebChromeClient());
        holder.webview.setWebViewClient(new WebViewClient());
        if(null==mapiContentResult.getInfor()){
            holder.webview.loadUrl(mapiContentResult.getContent());
        }else
            holder.webview.loadUrl(mapiContentResult.getInfor());


        if(isFouces){
            holder.webview.destroy();
        }

    }

}
