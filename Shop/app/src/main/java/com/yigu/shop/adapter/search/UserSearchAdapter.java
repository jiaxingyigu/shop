package com.yigu.shop.adapter.search;

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
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.api.CommunityApi;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.commom.util.RequestCallback;
import com.yigu.shop.commom.util.RequestExceptionCallback2;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/20.
 */
public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MapiMunityUserResult> mList;

    BaseActivity activity;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UserSearchAdapter(Context context, List<MapiMunityUserResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_munity_user_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapiMunityUserResult mapiMunityResult = mList.get(position);

        holder.image.setVisibility(View.VISIBLE);
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(mapiMunityResult.getIcon());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(75), DPUtil.dip2px(75)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.name.setText(mapiMunityResult.getName());
        holder.userTitle.setText(mapiMunityResult.getUserTitle());
        holder.date.setText(DateUtil.getInstance().string2YMD_H(mapiMunityResult.getDateline()));
        if(mapiMunityResult.getIsFollow().equals("0")) {
            holder.guanzhu.setBackgroundResource(R.drawable.selector_pressed_round_4_color_red);
            holder.guanzhu.setText("关注");
        }
        else if(mapiMunityResult.getIsFollow().equals("1")) {
            holder.guanzhu.setBackgroundResource(R.drawable.rect_solid_gray_round_4);
            holder.guanzhu.setText("取消");
        }

        holder.guanzhu.setTag(position);
        holder.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = (int) view.getTag();
                MapiMunityUserResult mapiMunityResult = mList.get(position);
                String type= "";
                if(mapiMunityResult.getIsFollow().equals("0"))
                    type = "follow";
                else if(mapiMunityResult.getIsFollow().equals("1"))
                    type = "unfollow";
                activity.showLoading();
                CommunityApi.useradmin(activity, mapiMunityResult.getUid(),type, new RequestCallback() {
                    @Override
                    public void success(Object success) {
                        activity.hideLoading();
                        if(mList.get(position).getIsFollow().equals("0")) {
                            MainToast.showShortToast("关注成功");
                            mList.get(position).setIsFollow("1");
                        }else if(mList.get(position).getIsFollow().equals("1")) {
                            MainToast.showShortToast("已取消关注");
                            mList.get(position).setIsFollow("0");
                        }
                        DebugLog.i("adapter=useradmin");
                        if(null!=notifyListener){
                            DebugLog.i("adapter=notifyListener");
                            notifyListener.notifyMethod(position);
                        }
                    }
                }, new RequestExceptionCallback2() {
                    @Override
                    public void error(String code, String message) {
                        activity.hideLoading();
                        MainToast.showShortToast(message);
                    }
                });

            }
        });

        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, position);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_view)
        LinearLayout rootView;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.userTitle)
        TextView userTitle;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.guanzhu)
        TextView guanzhu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private NotifyListener notifyListener;

    public interface NotifyListener{
        void notifyMethod(int position);
    }

    public void setNotifyListener(NotifyListener notifyListener){
        this.notifyListener = notifyListener;
    }

}
