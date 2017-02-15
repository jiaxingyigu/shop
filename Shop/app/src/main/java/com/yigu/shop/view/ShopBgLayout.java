package com.yigu.shop.view;

import android.content.Context;
import android.net.Uri;
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
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/9/28.
 */
public class ShopBgLayout extends RelativeLayout {
    @Bind(R.id.head)
    SimpleDraweeView head;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.shop)
    TextView shop;
    private Context mContext;
    private View view;

    MapiShopResult mapiShopResult;

    public ShopBgLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ShopBgLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ShopBgLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_shop_bg, this);
        ButterKnife.bind(this, view);
    }

    public void load(MapiShopResult item) {
        mapiShopResult = item;
        name.setText(item.getName());

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(item.getLogo());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(50), DPUtil.dip2px(50)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(head.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        head.setController(controller);

    }

    @OnClick(R.id.shop)
    public void onClick() {
    }
}
