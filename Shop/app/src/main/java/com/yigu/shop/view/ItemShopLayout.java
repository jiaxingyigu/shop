package com.yigu.shop.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.MapiShopResult;
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
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.all_num)
    TextView allNum;
    @Bind(R.id.new_num)
    TextView newNum;
    @Bind(R.id.follow_num)
    TextView followNum;
    @Bind(R.id.shop)
    TextView shop;
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
        allNum.setText(TextUtils.isEmpty(shopResult.getShop_goods_total())?"0":shopResult.getShop_goods_total());
        newNum.setText(TextUtils.isEmpty(shopResult.getShop_goods_new())?"0":shopResult.getShop_goods_new());
    }

    @OnClick(R.id.shop)
    public void onClick() {
        if(null!=shopResult)
            ControllerUtil.go2ShopDetail(shopResult);
    }
}
