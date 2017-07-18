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
import com.yigu.shop.commom.processors.BlurPostprocessor;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiContentResult;
import com.yigu.shop.commom.result.MapiMunityResult;
import com.yigu.shop.commom.result.MapiMunityUserResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;
import com.yigu.shop.shopinterface.RecyOnItemClickListener;
import com.yigu.shop.shopinterface.TabSelListener;
import com.yigu.shop.view.ComPersonTabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/3/27.
 */
public class ComPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();
    private final static int PERSON_BG = 0;
    private final static int PERSON_TAB = 1;
    private final static int ITEM_PUBLISH = 2;
    private final static int ITEM_INFO = 3;
    private final static int DIVIDER = 4;

    private TabSelListener tabSelListener;

    private boolean isEdit = true;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setTabSelListener(TabSelListener tabSelListener) {
        this.tabSelListener = tabSelListener;
    }

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ComPersonAdapter(Context context, List<IndexData> list) {
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
            case PERSON_BG:
                return new BGViewHolder(inflater.inflate(R.layout.layout_person_bg, parent, false));
            case PERSON_TAB:
                return new TABViewHolder(inflater.inflate(R.layout.lay_person_tab, parent, false));
            case ITEM_PUBLISH:
                return new ItemPublishViewHolder(inflater.inflate(R.layout.item_com_persion_publish, parent, false));
            case ITEM_INFO:
                return new ItemInfoViewHolder(inflater.inflate(R.layout.item_com_persion_info, parent, false));
            case DIVIDER:
                return new DividerViewHolder(inflater.inflate(R.layout.item_person_divider, parent, false));
            default:
                return new BGViewHolder(inflater.inflate(R.layout.lay_shop_bg, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BGViewHolder) {
            setBGLayout((BGViewHolder) holder, position);
        } else if (holder instanceof ItemPublishViewHolder) {
            setItemPublish((ItemPublishViewHolder) holder, position);
        } else if (holder instanceof ItemInfoViewHolder) {
            setItemInfo((ItemInfoViewHolder) holder, position);
        } else if (holder instanceof TABViewHolder) {
            ((TABViewHolder) holder).personTabLayout.setTabSelListener(new TabSelListener() {
                @Override
                public void tabSel(int position) {
                    if (null != tabSelListener)
                        tabSelListener.tabSel(position);
                }
            });
        } else if (holder instanceof DividerViewHolder) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "PERSON_BG":
                return PERSON_BG;
            case "PERSON_TAB":
                return PERSON_TAB;
            case "ITEM_PUBLISH":
                return ITEM_PUBLISH;
            case "ITEM_INFO":
                return ITEM_INFO;
            case "DIVIDER":
                return DIVIDER;
            default:
                return PERSON_BG;
        }
    }

    class BGViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageBg)
        SimpleDraweeView imageBg;
        @Bind(R.id.back)
        ImageView back;
        @Bind(R.id.bianji)
        TextView bianji;
        @Bind(R.id.lay_header)
        RelativeLayout layHeader;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.userTitle)
        TextView userTitle;
        @Bind(R.id.reply_posts_num)
        TextView replyPostsNum;
        @Bind(R.id.friend_num)
        TextView friendNum;
        @Bind(R.id.follow_num)
        TextView followNum;
        @Bind(R.id.sign)
        TextView sign;

        public BGViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TABViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.personTabLayout)
        ComPersonTabLayout personTabLayout;

        public TABViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemPublishViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.ll_image)
        LinearLayout llImage;
        @Bind(R.id.hits)
        TextView hits;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.title)
        TextView title;

        public ItemPublishViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemInfoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.data)
        TextView data;

        public ItemInfoViewHolder(View itemView) {
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

    private void setBGLayout(BGViewHolder holder, int position) {
        MapiMunityUserResult mapiMunityUserResult = (MapiMunityUserResult) mList.get(position).getData();

        holder.name.setText(TextUtils.isEmpty(mapiMunityUserResult.getName()) ? "" : mapiMunityUserResult.getName());
        holder.replyPostsNum.setText("参与 " + (TextUtils.isEmpty(mapiMunityUserResult.getReply_posts_num()) ? "0" : mapiMunityUserResult.getReply_posts_num()));
        holder.friendNum.setText("关注 " + (TextUtils.isEmpty(mapiMunityUserResult.getFriend_num()) ? "0" : mapiMunityUserResult.getFriend_num()));
        holder.followNum.setText("粉丝 " + (TextUtils.isEmpty(mapiMunityUserResult.getFollow_num()) ? "0" : mapiMunityUserResult.getFollow_num()));
        holder.sign.setText(TextUtils.isEmpty(mapiMunityUserResult.getSign()) ? "暂无签名" : mapiMunityUserResult.getSign());

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(mapiMunityUserResult.getIcon());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(75), DPUtil.dip2px(75)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        ImageRequest request2 = ImageRequestBuilder.newBuilderWithResourceId(R.mipmap.com_person_bg)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(200), DPUtil.dip2px(200)))
                .setPostprocessor(new BlurPostprocessor(mContext, 60))
                .build();
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request2)
                .setOldController(holder.imageBg.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.imageBg.setController(controller2);

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=bgDeelListener)
                    bgDeelListener.backListener();
            }
        });

        if(isEdit)
            holder.bianji.setVisibility(View.VISIBLE);
        else
            holder.bianji.setVisibility(View.INVISIBLE);

        holder.bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=bgDeelListener)
                    bgDeelListener.deelListener();
            }
        });


    }


    private void setItemInfo(ItemInfoViewHolder holder, int position) {
        MapiContentResult mapiContentResult = (MapiContentResult) mList.get(position).getData();
        holder.title.setText(mapiContentResult.getTitle());
        holder.data.setText(TextUtils.isEmpty(mapiContentResult.getData()) ? "" : mapiContentResult.getData());
    }

    private void setItemPublish(ItemPublishViewHolder holder, int position) {
        MapiMunityResult mapiMunityResult = (MapiMunityResult) mList.get(position).getData();
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(mapiMunityResult.getUserAvatar());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(40), DPUtil.dip2px(40)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.name.setText(TextUtils.isEmpty(mapiMunityResult.getUser_nick_name()) ? "" : mapiMunityResult.getUser_nick_name());

        String dateStr = TextUtils.isEmpty(mapiMunityResult.getLast_reply_date()) ? "0" : mapiMunityResult.getLast_reply_date();
        Date targetDate = new Date();
        targetDate.setTime(Long.parseLong(dateStr));
        holder.date.setText(DateUtil.getInstance().date2PublishTime(targetDate));
        holder.title.setText(TextUtils.isEmpty(mapiMunityResult.getTitle()) ? "" : mapiMunityResult.getTitle());
        holder.hits.setText(TextUtils.isEmpty(mapiMunityResult.getHits()) ? "0" : mapiMunityResult.getHits());

        holder.llImage.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(0, 0, 1, 0);
        String[] images = mapiMunityResult.getImageList();
        if (null != images && images.length > 0) {
            int length = 0;
            if (images.length > 3)
                length = 3;
            else
                length = images.length;
            for (int i = 0; i < length; i++) {
                final String path = images[i];
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setVisibility(View.GONE);
                view.setTag(i);
                holder.llImage.addView(view, layoutParams);
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
                Uri imageUri2 = Uri.parse(path);
                ImageRequest request2 = ImageRequestBuilder.newBuilderWithSource(imageUri2)
                        .setResizeOptions(new ResizeOptions(DPUtil.dip2px(130), DPUtil.dip2px(90)))
                        .build();
                DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request2)
                        .setOldController(iamge.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>())
                        .build();
                iamge.setController(controller2);
            }
        }


    }

    private BGDeelListener bgDeelListener;

    public void setBgDeelListener(BGDeelListener bgDeelListener){
        this.bgDeelListener = bgDeelListener;
    }

    public interface BGDeelListener{
        void deelListener();
        void backListener();
    }

}
