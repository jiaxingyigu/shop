package com.yigu.shop.view;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
import com.yigu.shop.adapter.community.ComContentAdapter;
import com.yigu.shop.commom.result.MapiContentResult;
import com.yigu.shop.commom.result.MapiTopicResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DateUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/8/31.
 */
public class ComDetailPortalLayout extends RelativeLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.care)
    TextView care;
    @Bind(R.id.nick)
    TextView nick;
    @Bind(R.id.userTitle)
    TextView userTitle;
    @Bind(R.id.date)
    TextView date;
    private Context mContext;
    private View view;

    ComContentAdapter mAdapter;
    List<MapiContentResult> mList;

    public ComDetailPortalLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ComDetailPortalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ComDetailPortalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_com_detail_topic, this);
        ButterKnife.bind(this, view);
        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.addItemDecoration(new DividerListItemDecoration(mContext, OrientationHelper.HORIZONTAL, DPUtil.dip2px(8), getResources().getColor(android.R.color.white)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ComContentAdapter(mContext, mList);
        recyclerView.setAdapter(mAdapter);

    }

    public void load(MapiTopicResult mapiTopicResult) {

        Uri imageUri = Uri.parse(mapiTopicResult.getIcon());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(40), DPUtil.dip2px(40)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        image.setController(controller);

        nick.setText(TextUtils.isEmpty(mapiTopicResult.getUser_nick_name())?"":mapiTopicResult.getUser_nick_name());
        userTitle.setText(TextUtils.isEmpty(mapiTopicResult.getUserTitle())?"":mapiTopicResult.getUserTitle());
        date.setText(DateUtil.getInstance().string2YMD_H(mapiTopicResult.getCreate_date()));

        List<MapiContentResult> list = mapiTopicResult.getContent();
        if(null==list||list.isEmpty())
            return;
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.care)
    public void onClick() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        DebugLog.i("onWindowFocusChanged");
        if (!hasWindowFocus) {
            mAdapter.setFouces(true);
            mAdapter.notifyDataSetChanged();
        }
    }
}
