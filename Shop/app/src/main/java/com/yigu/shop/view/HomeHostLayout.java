package com.yigu.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yigu.shop.R;

import butterknife.ButterKnife;

/**
 * Created by brain on 2016/8/31.
 */
public class HomeHostLayout extends RelativeLayout {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_slider, this);
        ButterKnife.bind(this, view);
    }
}
