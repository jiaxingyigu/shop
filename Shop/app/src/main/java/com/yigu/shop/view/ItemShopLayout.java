package com.yigu.shop.view;

import android.content.Context;
import android.net.Uri;
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
import com.yigu.shop.commom.result.MapiShopResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.util.ControllerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/9/10.
 */
public class ItemShopLayout extends RelativeLayout {
    @Bind(R.id.image)
    SimpleDraweeView image;
    @Bind(R.id.name)
    TextView name;
    /*@Bind(R.id.content)
    TextView content;*/
    @Bind(R.id.all_num)
    TextView allNum;
    @Bind(R.id.new_num)
    TextView newNum;
    @Bind(R.id.shop)
    TextView shop;
    @Bind(R.id.concer_num)
    TextView concer_num;


    private Context mContext;
    private View view;

    MapiShopResult shopResult = null;

    public ItemShopLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ItemShopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ItemShopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_shop, this);
        ButterKnife.bind(this, view);
    }

    public void load(MapiShopResult shopResult) {
        this.shopResult = shopResult;
        allNum.setText(TextUtils.isEmpty(shopResult.getGoods_count())?"0":shopResult.getGoods_count());
        newNum.setText(TextUtils.isEmpty(shopResult.getNew_goods_count())?"0":shopResult.getNew_goods_count());
        concer_num.setText(TextUtils.isEmpty(shopResult.getSeller_foller_count())?"0":shopResult.getSeller_foller_count());

        name.setText(TextUtils.isEmpty(shopResult.getName())?"":shopResult.getName());

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(shopResult.getLogo());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(50), DPUtil.dip2px(50)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        image.setController(controller);

    }

    @OnClick(R.id.shop)
    public void onClick() {
        if(null!=shopResult)
            ControllerUtil.go2ShopDetail(shopResult);
    }
}
