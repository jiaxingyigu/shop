package com.yigu.shop.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yigu.shop.R;
import com.yigu.shop.activity.products.ProductListActivity;
import com.yigu.shop.commom.result.MapiResourceResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.util.ControllerUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeHostLayout extends RelativeLayout {
    @Bind(R.id.host_layout)
    LinearLayout hostLayout;
    @Bind(R.id.tv_more)
    TextView tvMore;
    private Context mContext;
    private View view;

    public HomeHostLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeHostLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HomeHostLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        if (isInEditMode())
            return;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_host, this);
        ButterKnife.bind(this, view);
    }

    public void load(List<MapiResourceResult> list) {
        hostLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutParams.setMargins(DPUtil.dip2px(5), 0, DPUtil.dip2px(5), DPUtil.dip2px(4));
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, null);
            view.setTag(i);
            hostLayout.addView(view, layoutParams);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ControllerUtil.go2ProductDetail();
                }
            });
        }
    }

    @OnClick(R.id.tv_more)
    public void onClick() {
        Intent i = new Intent();
        i.setClass(mContext, ProductListActivity.class);
        mContext.startActivity(i);
    }
}
